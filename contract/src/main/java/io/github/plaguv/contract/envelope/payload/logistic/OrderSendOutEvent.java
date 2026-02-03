package io.github.plaguv.contract.envelope.payload.logistic;

import io.github.plaguv.contract.envelope.payload.EventDomain;
import io.github.plaguv.contract.envelope.payload.EventInstance;
import jakarta.annotation.Nonnull;

public record OrderSendOutEvent(long storeId) implements EventInstance {

    public OrderSendOutEvent {
        if (storeId < 0) {
            throw new IllegalArgumentException("OrderSendOutEvent attribute 'storeId' cannot be < 0");
        }
    }

    @Override
    public @Nonnull EventDomain getEventDomain() {
        return EventDomain.LOGISTIC;
    }
}