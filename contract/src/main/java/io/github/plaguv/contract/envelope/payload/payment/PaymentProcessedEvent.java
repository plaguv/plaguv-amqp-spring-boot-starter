package io.github.plaguv.contract.envelope.payload.payment;

import io.github.plaguv.contract.envelope.payload.EventDomain;
import io.github.plaguv.contract.envelope.payload.EventInstance;
import jakarta.annotation.Nonnull;

public record PaymentProcessedEvent(float paymentId) implements EventInstance {

    public PaymentProcessedEvent {
        if (paymentId < 0) {
            throw new IllegalArgumentException("PaymentProcessedEvent attribute 'paymentId' cannot be < 0");
        }
    }

    @Override
    public @Nonnull EventDomain getEventDomain() {
        return EventDomain.PAYMENT;
    }
}