package io.github.plaguv.amqp.starter.autoconfigure;

import io.github.plaguv.amqp.core.listener.MessageRejectedException;
import io.github.plaguv.amqp.core.utlity.converter.EventPayloadArgumentResolver;
import io.github.plaguv.amqp.core.utlity.converter.EventPayloadByteConverter;
import io.github.plaguv.amqp.starter.properties.AmqpDeclarationPropertiesConfiguration;
import io.github.plaguv.amqp.starter.properties.AmqpPropertiesConfiguration;
import io.github.plaguv.amqp.starter.properties.AmqpStartupPropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.amqp.autoconfigure.RabbitAutoConfiguration;
import org.springframework.boot.amqp.autoconfigure.RabbitTemplateCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.retry.RetryPolicy;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;

import java.time.Duration;
import java.util.List;

@AutoConfiguration(after = RabbitAutoConfiguration.class)
@EnableConfigurationProperties({
        AmqpPropertiesConfiguration.class,
        AmqpDeclarationPropertiesConfiguration.class,
        AmqpStartupPropertiesConfiguration.class
})
public class AmqpAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AmqpAutoConfiguration.class);

    @Bean
    public ObjectMapper amqpObjectMapper() {
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType("io.github.plaguv")
                .build();

        return JsonMapper.builder()
                .polymorphicTypeValidator(ptv)
                .build();
    }

    @Bean
    public RabbitTemplateCustomizer amqpEventTemplateCustomizer() {
        return rabbitTemplate -> {
            rabbitTemplate.setMandatory(true);
            rabbitTemplate.setMessageConverter(new EventPayloadByteConverter(amqpObjectMapper()));
            rabbitTemplate.setReturnsCallback(returned ->
                    log.atError().log(
                            "Unroutable message. exchange='{}', routingKey='{}'",
                            returned.getExchange(),
                            returned.getRoutingKey()
                    )
            );
        };
    }

    @Bean
    public SimpleRabbitListenerContainerFactory amqpEventListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();

        factory.setMessageConverter(new EventPayloadByteConverter(amqpObjectMapper()));
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        factory.setChannelTransacted(false);
        factory.setDefaultRequeueRejected(false);

        factory.setAdviceChain(
                RetryInterceptorBuilder.stateless()
                        .retryPolicy(
                                RetryPolicy.builder()
                                        .excludes(MessageRejectedException.class)
                                        .maxRetries(5)
                                        .delay(Duration.ofMillis(1000))
                                        .maxDelay(Duration.ofMillis(100_000))
                                        .build()
                        )
                        .recoverer((message, cause) -> {
                            if (cause.getCause() instanceof MessageRejectedException) {
                                // Substring at 62 removes first part of not relevant ListenerException message
                                log.atWarn().log("Message has been manually rejected. {}", cause.getMessage().substring(62));
                            } else {
                                log.atError().log(cause.getMessage());
                            }
                        })
                        .build()
        );

        return factory;
    }

    @Bean
    public MessageHandlerMethodFactory amqpMessageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();

        factory.setCustomArgumentResolvers(List.of(
                new EventPayloadArgumentResolver()
        ));
        factory.afterPropertiesSet();

        return factory;
    }
}