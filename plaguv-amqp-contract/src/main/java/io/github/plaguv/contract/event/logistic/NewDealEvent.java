package io.github.plaguv.contract.event.logistic;

import io.github.plaguv.contract.event.Event;
import io.github.plaguv.contract.event.EventDomain;

@Event(domain = EventDomain.LOGISTIC)
public record NewDealEvent(
        long articleId
) {
    public NewDealEvent {
        if (articleId < 0) {
            throw new IllegalArgumentException("NewDealEvent parameter 'articleId' cannot be negative");
        }
    }
}