package io.github.plaguv.amqp.core.publisher;

import io.github.plaguv.amqp.api.envelope.EventEnvelope;

@FunctionalInterface
public interface EventPublisher {
    void publishMessage(EventEnvelope eventEnvelope);
}