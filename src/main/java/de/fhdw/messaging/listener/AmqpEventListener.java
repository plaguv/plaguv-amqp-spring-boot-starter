package de.fhdw.messaging.listener;

import de.fhdw.messaging.config.properties.AmqpProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AmqpEventListener implements EventListener {

    private final RabbitTemplate rabbitTemplate;
    private final AmqpProperties amqpProperties;

    public AmqpEventListener(RabbitTemplate rabbitTemplate, AmqpProperties amqpProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.amqpProperties = amqpProperties;
    }

    @Override
    public void handleMessage(Object message) {}
}