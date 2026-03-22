package io.github.plaguv.amqp.core.listener.registrar;

import io.github.plaguv.amqp.api.envelope.EventEnvelope;
import io.github.plaguv.amqp.api.envelope.EventEnvelopeBuilder;
import io.github.plaguv.amqp.api.envelope.payload.EventPayload;
import io.github.plaguv.amqp.core.listener.discoverer.EventListenerDiscoverer;
import io.github.plaguv.amqp.core.utlity.router.EventRouter;
import io.github.plaguv.amqp.core.utlity.helper.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.MethodRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class AmqpEventListenerRegistrar implements EventListenerRegistrar, RabbitListenerConfigurer {

    private static final Logger log = LoggerFactory.getLogger(AmqpEventListenerRegistrar.class);

    private final SimpleRabbitListenerContainerFactory containerFactory;
    private final MessageHandlerMethodFactory methodFactory;
    private final EventListenerDiscoverer discoverer;
    private final EventRouter router;

    public AmqpEventListenerRegistrar(
            SimpleRabbitListenerContainerFactory containerFactory,
            MessageHandlerMethodFactory methodFactory,
            EventListenerDiscoverer discoverer,
            EventRouter router
    ) {
        this.containerFactory = containerFactory;
        this.methodFactory = methodFactory;
        this.discoverer = discoverer;
        this.router = router;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(methodFactory);
        registrar.setContainerFactory(containerFactory);

        for (Listener listener : discoverer.getListeners()) {
            registerListener(listener, registrar);
        }
    }

    private void registerListener(Listener listener, RabbitListenerEndpointRegistrar registrar) {
        Object bean = listener.bean();
        Method method = listener.method();
        Parameter parameter = listener.parameter();

        EventEnvelope envelope = EventEnvelopeBuilder.defaults()
                .ofEventPayload(EventPayload.empty(parameter.getType()))
                .build();
        String queueName = router.resolveQueue(envelope);

        MethodRabbitListenerEndpoint endpoint = new MethodRabbitListenerEndpoint();
        endpoint.setBean(bean);
        endpoint.setMethod(method);
        endpoint.setQueueNames(queueName);
        endpoint.setMessageHandlerMethodFactory(methodFactory);

        endpoint.setId("%s#%s".formatted(bean.getClass().getName(), method.getName()));

        registrar.registerEndpoint(endpoint);
        log.atDebug().log("Registered listener '{}' with endpoint '{}'", listener, endpoint);
    }
}