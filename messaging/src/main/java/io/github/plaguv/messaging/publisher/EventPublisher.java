package io.github.plaguv.messaging.publisher;

import io.github.plaguv.contract.envelope.EventEnvelope;
import jakarta.annotation.Nonnull;

public interface EventPublisher {

    /**
     *
     * @param eventEnvelope
     */
    void publishMessage(@Nonnull EventEnvelope eventEnvelope);
}