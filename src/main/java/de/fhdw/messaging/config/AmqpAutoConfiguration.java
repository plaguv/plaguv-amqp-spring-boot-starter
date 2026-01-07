package de.fhdw.messaging.config;

import de.fhdw.messaging.utility.AmqpEventPublisher;
import de.fhdw.messaging.utility.EventPublisher;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.boot.amqp.autoconfigure.RabbitTemplateCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(AmqpProperties.class)
public class AmqpAutoConfiguration {

    public AmqpAutoConfiguration() {}

    @Bean
    @ConditionalOnMissingBean(EventPublisher.class)
    public EventPublisher eventPublisher(RabbitTemplate rabbitTemplate, AmqpProperties amqpProperties) {
        return new AmqpEventPublisher(rabbitTemplate, amqpProperties);
    }

    @Bean
    @ConditionalOnMissingBean(RabbitTemplateCustomizer.class)
    public RabbitTemplateCustomizer rabbitTemplateCustomizer(JacksonJsonMessageConverter converter) {
        return template -> template.setMessageConverter(converter);
    }

    @Bean
    @ConditionalOnMissingBean(DirectExchange.class)
    public DirectExchange appExchange(AmqpProperties amqpProperties) {
        return new DirectExchange(
                amqpProperties.getExchange(),
                true,
                false
        );
    }

//    TODO: Will be implemented after de.fhdw:messaging-amqp-contract dependency
//    @Bean
//    @ConditionalOnMissingBean(Declarable.class)
//    public Declarables domainDeclarables(DirectExchange exchange, DomainQueue[] queues) {
//        List<Declarable> declarables = new ArrayList<>();
//
//        for (DomainQueue dq : queues) {
//            Queue queue = QueueBuilder
//                    .durable(dq.getQueue())
//                    .build();
//
//            Binding binding = BindingBuilder
//                    .bind(queue)
//                    .to(exchange)
//                    .with(dq.getQueue());
//
//            declarables.add(queue);
//            declarables.add(binding);
//        }
//
//        return new Declarables(declarables);
//    }
}