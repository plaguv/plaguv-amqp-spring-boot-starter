package io.github.plaguv.messaging.publisher;

import io.github.plaguv.contracts.common.EventEnvelope;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AmqpTopologyDeclarer implements TopologyDeclarer {

    private static final Logger log = LoggerFactory.getLogger(AmqpTopologyDeclarer.class);

    private final RabbitAdmin rabbitAdmin;
    private final EventRouter eventRouter;

    // can probably be simplified to one collective key-set
    private final Set<String> declaredExchanges = ConcurrentHashMap.newKeySet();
    private final Set<String> declaredQueues = ConcurrentHashMap.newKeySet();
    private final Set<String> declaredBindings = ConcurrentHashMap.newKeySet();

    public AmqpTopologyDeclarer(RabbitAdmin rabbitAdmin, EventRouter eventRouter) {
        this.rabbitAdmin = rabbitAdmin;
        this.eventRouter = eventRouter;
    }

    @Override
    public void declareExchangeIfAbsent(String exchangeName, EventEnvelope eventEnvelope) {
        if (eventEnvelope == null) {
            throw new IllegalArgumentException("Parameter 'eventEnvelope' cannot be null");
        }
        if (declaredExchanges.contains(exchangeName)) {
            return;
        }

        Exchange exchange = switch (eventEnvelope.routing().eventDispatchType()) {
            case TOPIC     -> new TopicExchange (exchangeName, true, false);
            case DIRECT    -> new DirectExchange(exchangeName, true, false);
            case BROADCAST -> new FanoutExchange(exchangeName, true, false);
        };

        rabbitAdmin.declareExchange(exchange);
        declaredExchanges.add(exchangeName);

        log.atInfo().log("Declared exchange '{}'", exchangeName);
    }

    @Override
    public void declareQueueIfAbsent(String queueName, EventEnvelope eventEnvelope) {
        if (eventEnvelope == null) {
            throw new IllegalArgumentException("Parameter 'eventEnvelope' cannot be null");
        }
        if (declaredQueues.contains(queueName)) {
            return;
        }
    }

    @Override
    public void declareBindingIfAbsent(String bindingName, EventEnvelope eventEnvelope) {
        if (eventEnvelope == null) {
            throw new IllegalArgumentException("Parameter 'eventEnvelope' cannot be null");
        }
        if (declaredBindings.contains(bindingName)) {
            return;
        }

    }

    @PostConstruct
    public void constructAll() {

    }
}