package io.github.plaguv.messaging.listener;

import org.springframework.amqp.core.*;
import org.springframework.stereotype.Component;

@Component
public class AmqpEventListenerRegistrar {

    private final AmqpAdmin amqpAdmin;

    public AmqpEventListenerRegistrar(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }
}