package io.github.plaguv.messaging.listener;

import io.github.plaguv.contracts.DomainEvent;
import io.github.plaguv.contracts.common.EventScope;
import io.github.plaguv.contracts.common.EventType;
import io.github.plaguv.messaging.config.properties.AmqpProperties;
import org.jspecify.annotations.NonNull;
import org.springframework.amqp.core.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.GenericTypeResolver;
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
        if (!(bean instanceof EventListener<?> eventListener)) {
            return bean;
        }

        Class<?> eventClass = GenericTypeResolver.resolveTypeArgument(eventListener.getClass(), EventListener.class);
        EventType eventType = EventType.fromEventClass((Class<? extends DomainEvent>) eventClass)
                .orElseThrow(() -> new IllegalStateException("No EventType registered for: " + eventListener));

        String routingKey = eventType.eventRoutingKey();
        String exchange = amqpProperties.getExchange(EventScope.EXTERNAL);

        TopicExchange topicExchange = new TopicExchange(exchange, true, false);
        amqpAdmin.declareExchange(topicExchange);

        Queue queue = new Queue(routingKey, true, false, false);
        amqpAdmin.declareQueue(queue);

        Binding binding = BindingBuilder.bind(queue).to(topicExchange).with(routingKey);
        amqpAdmin.declareBinding(binding);

        return bean;

    }
}