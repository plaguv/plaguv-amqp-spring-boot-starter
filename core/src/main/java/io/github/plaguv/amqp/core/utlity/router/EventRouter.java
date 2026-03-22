package io.github.plaguv.amqp.core.utlity.router;

import io.github.plaguv.amqp.api.envelope.EventEnvelope;

import java.util.Set;

public interface EventRouter {
    String resolveQueue(EventEnvelope eventEnvelope);

    String resolveExchange(EventEnvelope eventEnvelope);

    String resolveRoutingKey(EventEnvelope eventEnvelope);

    Set<String> resolveBindingKey(EventEnvelope eventEnvelope);
}