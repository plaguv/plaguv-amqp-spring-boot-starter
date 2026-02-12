package io.github.plaguv.messaging.listener.registrar;

import io.github.plaguv.contract.envelope.EventEnvelope;
import io.github.plaguv.contract.envelope.payload.EventPayload;
import io.github.plaguv.messaging.listener.discoverer.EventListenerDiscoverer;
import io.github.plaguv.messaging.utlity.EventRouter;
import io.github.plaguv.messaging.utlity.helper.Listener;
import org.jspecify.annotations.NonNull;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.MethodRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class AmqpEventListenerRegistrar implements EventListenerRegistrar, RabbitListenerConfigurer {

    private final ListableBeanFactory beanFactory;
    private final MessageHandlerMethodFactory factory;
    private final EventListenerDiscoverer discoverer;
    private final EventRouter router;


    public AmqpEventListenerRegistrar(ListableBeanFactory beanFactory, MessageHandlerMethodFactory factory, EventListenerDiscoverer discoverer, EventRouter router) {
        this.beanFactory = beanFactory;
        this.factory = factory;
        this.discoverer = discoverer;
        this.router = router;
    }

    @Override
    public void configureRabbitListeners(@NonNull RabbitListenerEndpointRegistrar registrar) {
        for (Listener listener : discoverer.getListeners()) {
            System.out.println("Registering listener: " + listener.getClass().getSimpleName());
            registerListener(listener, registrar);
        }
    }

    private void registerListener(Listener listener, RabbitListenerEndpointRegistrar registrar) {
        Object bean = beanFactory.getBean(listener.bean().toString());
        Method method = listener.method();
        Parameter parameter = listener.parameter();

        EventEnvelope envelope = EventEnvelope.builderWithDefaults()
                .ofEventPayload(EventPayload.empty(parameter.getType()))
                .build();

        String queueName = router.resolveQueue(envelope);

        MethodRabbitListenerEndpoint endpoint = new MethodRabbitListenerEndpoint();
        endpoint.setBean(bean);
        endpoint.setMethod(method);
        endpoint.setQueueNames(queueName);
        endpoint.setMessageHandlerMethodFactory(factory);

        endpoint.setId("%s.%s".formatted(bean, method));

        registrar.registerEndpoint(endpoint);
    }
}