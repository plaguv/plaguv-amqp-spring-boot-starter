package io.github.plaguv.contract.envelope.payload.pos;

import io.github.plaguv.contract.envelope.payload.EventDomain;
import io.github.plaguv.contract.envelope.payload.EventInstance;
import jakarta.annotation.Nonnull;

public record StoreOpenedEvent(long storeId) implements EventInstance {

    public StoreOpenedEvent {
        if (storeId < 0) {
            throw new IllegalArgumentException("StoreOpenedEvents storeId cannot be < 0");
        }
    }

    @Override
    public @Nonnull EventDomain getEventDomain() {
        return EventDomain.STORE;
    }
}