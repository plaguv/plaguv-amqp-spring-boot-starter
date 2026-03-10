package io.github.plaguv.amqp.core.publisher;

import io.github.plaguv.amqp.api.envelope.EventEnvelope;
import jakarta.annotation.Nonnull;

public interface EventPublisher {
    void publishMessage(@Nonnull EventEnvelope eventEnvelope);
}