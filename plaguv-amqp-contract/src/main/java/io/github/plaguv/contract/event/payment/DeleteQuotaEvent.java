package io.github.plaguv.contract.event.payment;

import io.github.plaguv.contract.event.Event;
import io.github.plaguv.contract.event.EventDomain;

@Event(domain = EventDomain.PAYMENT)
public record DeleteQuotaEvent(
        long articleId
) {
    public DeleteQuotaEvent {
        if (articleId < 0) {
            throw new IllegalArgumentException("DeleteQuotaEvent parameter 'articleId' cannot be negative");
        }
    }
}