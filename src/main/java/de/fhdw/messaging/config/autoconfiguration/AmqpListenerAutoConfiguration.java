package de.fhdw.messaging.config.autoconfiguration;

import de.fhdw.messaging.config.properties.AmqpProperties;
import de.fhdw.messaging.listener.AmqpEventListener;
import de.fhdw.messaging.listener.EventListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(after = AmqpAutoConfiguration.class)
@EnableConfigurationProperties(AmqpProperties.class)
public class AmqpListenerAutoConfiguration {

    public AmqpListenerAutoConfiguration() {}

    @Bean
    @ConditionalOnMissingBean(EventListener.class)
    public EventListener eventListener(RabbitTemplate rabbitTemplate, AmqpProperties amqpProperties) {
        return new AmqpEventListener(rabbitTemplate, amqpProperties);
    }
}