package de.fhdw.messaging.config.autoconfiguration;

import de.fhdw.messaging.config.properties.AmqpProperties;
import de.fhdw.messaging.publisher.AmqpEventPublisher;
import de.fhdw.messaging.publisher.EventPublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(after = AmqpAutoConfiguration.class)
@EnableConfigurationProperties(AmqpProperties.class)
public class AmqpPublisherAutoConfiguration {

    public AmqpPublisherAutoConfiguration() {}

    @Bean
    @ConditionalOnMissingBean(EventPublisher.class)
    public EventPublisher eventPublisher(RabbitTemplate rabbitTemplate, AmqpProperties amqpProperties) {
        return new AmqpEventPublisher(rabbitTemplate, amqpProperties);
    }
}