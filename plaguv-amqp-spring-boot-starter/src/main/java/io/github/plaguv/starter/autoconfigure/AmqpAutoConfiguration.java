package io.github.plaguv.starter.autoconfigure;

import io.github.plaguv.core.utlity.properties.AmqpDeclarationProperties;
import io.github.plaguv.core.utlity.properties.AmqpProperties;
import io.github.plaguv.core.utlity.properties.AmqpStartupProperties;
import io.github.plaguv.core.utlity.AmqpEventRouter;
import io.github.plaguv.core.utlity.EventRouter;
import io.github.plaguv.core.utlity.converter.EventPayloadArgumentResolver;
import io.github.plaguv.core.utlity.converter.EventPayloadByteConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.amqp.autoconfigure.RabbitAutoConfiguration;
import org.springframework.boot.amqp.autoconfigure.RabbitTemplateCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;

import java.util.List;

@AutoConfiguration(after = RabbitAutoConfiguration.class)
@EnableConfigurationProperties({
        AmqpProperties.class,
        AmqpDeclarationProperties.class,
        AmqpStartupProperties.class
})
public class AmqpAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AmqpAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper() {
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType("io.github.plaguv")
                .build();

        return JsonMapper.builder()
                .polymorphicTypeValidator(ptv)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(EventRouter.class)
    public EventRouter eventRouter(AmqpProperties amqpProperties) {
        return new AmqpEventRouter(amqpProperties);
    }

    @Bean
    @ConditionalOnMissingBean(RabbitTemplateCustomizer.class)
    public RabbitTemplateCustomizer amqpEventTemplateCustomizer() {
        return rabbitTemplate -> {
            rabbitTemplate.setMandatory(true);
            rabbitTemplate.setReturnsCallback(returned ->
                    log.atError().log(
                            "Unroutable message. exchange={}, routingKey={}",
                            returned.getExchange(),
                            returned.getRoutingKey()
                    )
            );
        };
    }

    @Bean
//    @ConditionalOnMissingBean(SimpleRabbitListenerContainerFactory.class)
    public SimpleRabbitListenerContainerFactory amqpEventListenerContainerFactory(ConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();

        factory.setMessageConverter(new EventPayloadByteConverter(objectMapper));
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        factory.setChannelTransacted(false);
        factory.setAdviceChain(RetryInterceptorBuilder.stateless()
                .maxRetries(5)
                .backOffOptions(1000, 5.0, 100_000)
                .build());

        return factory;
    }

    @Bean
    @ConditionalOnMissingBean(MessageHandlerMethodFactory.class)
    public MessageHandlerMethodFactory amqpMessageHandlerMethodFactory(ObjectMapper objectMapper) {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();

        factory.setCustomArgumentResolvers(List.of(
                new EventPayloadArgumentResolver(objectMapper)
        ));
        factory.afterPropertiesSet();

        return factory;
    }
}