package io.github.plaguv.amqp.starter.autoconfigure;

import io.github.plaguv.amqp.core.publisher.AmqpEventPublisher;
import io.github.plaguv.amqp.core.publisher.EventPublisher;
import io.github.plaguv.amqp.core.utlity.EventRouter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import tools.jackson.databind.ObjectMapper;

@AutoConfiguration(after = AmqpAutoConfiguration.class)
public class AmqpEventPublisherAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(EventPublisher.class)
    public EventPublisher eventPublisher(RabbitTemplate rabbitTemplate, EventRouter eventRouter, ObjectMapper objectMapper) {
        return new AmqpEventPublisher(rabbitTemplate, eventRouter, objectMapper);
    }
}