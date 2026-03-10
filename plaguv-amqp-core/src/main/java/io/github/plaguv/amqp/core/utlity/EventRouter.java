package io.github.plaguv.amqp.core.utlity;

import io.github.plaguv.amqp.api.envelope.EventEnvelope;
import jakarta.annotation.Nonnull;

import java.util.Set;

public interface EventRouter {

    @Nonnull
    String resolveQueue(@Nonnull EventEnvelope eventEnvelope);

    @Nonnull
    String resolveExchange(@Nonnull EventEnvelope eventEnvelope);

    @Nonnull
    String resolveRoutingKey(@Nonnull EventEnvelope eventEnvelope);

    @Nonnull
    Set<String> resolveBindingKey(@Nonnull EventEnvelope eventEnvelope);
}