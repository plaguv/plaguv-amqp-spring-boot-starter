package de.fhdw.messaging.utility;

import de.fhdw.messaging.config.AmqpProperties;
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
    public void publish(Object o) {
        rabbitTemplate.convertAndSend(
                amqpProperties.getExchange(),
                "",
                o
        );
    }
}
