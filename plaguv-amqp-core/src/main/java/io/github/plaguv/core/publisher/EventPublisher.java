package io.github.plaguv.core.publisher;

import io.github.plaguv.contract.envelope.EventEnvelope;
import jakarta.annotation.Nonnull;

public interface EventPublisher {
    void publishMessage(@Nonnull EventEnvelope eventEnvelope);
}