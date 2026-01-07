package de.fhdw.messaging.publisher;

import de.fhdw.messaging.config.properties.AmqpProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AmqpEventPublisher implements EventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final AmqpProperties amqpProperties;

    public AmqpEventPublisher(RabbitTemplate rabbitTemplate, AmqpProperties amqpProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.amqpProperties = amqpProperties;
    }

    @Override
    public void publish(Object o) {}
}
