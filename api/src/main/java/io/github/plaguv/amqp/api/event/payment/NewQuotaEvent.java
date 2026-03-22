package io.github.plaguv.amqp.api.event.payment;

import io.github.plaguv.amqp.api.event.Event;
import io.github.plaguv.amqp.api.event.EventDomain;

@Event(domain = EventDomain.PAYMENT)
public record NewQuotaEvent(
        long articleId,
        long amount
) {
    public NewQuotaEvent {
        if (articleId < 0) {
            throw new IllegalArgumentException("NewQuotaEvent parameter 'articleId' cannot be negative");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("NewQuotaEvent parameter 'amount' cannot be negative");
        }
    }
}