package io.github.plaguv.messaging.listener;

import io.github.plaguv.messaging.config.properties.AmqpProperties;
import org.jspecify.annotations.NonNull;
import org.springframework.amqp.core.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class AmqpEventListenerRegistrar implements BeanPostProcessor {

    private final AmqpAdmin amqpAdmin;
    private final AmqpProperties amqpProperties;

    public AmqpEventListenerRegistrar(AmqpAdmin amqpAdmin, AmqpProperties amqpProperties) {
        this.amqpAdmin = amqpAdmin;
        this.amqpProperties = amqpProperties;
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        EventListener eventListener = (EventListener) bean;
        return bean;
    }
}