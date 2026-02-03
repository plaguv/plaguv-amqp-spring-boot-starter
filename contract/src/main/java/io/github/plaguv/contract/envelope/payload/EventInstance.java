package io.github.plaguv.contract.envelope.payload;

import jakarta.annotation.Nonnull;

public interface EventInstance {
    @Nonnull EventDomain getEventDomain();
}