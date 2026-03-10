package io.github.plaguv.amqp.api.event.logistic;

import io.github.plaguv.amqp.api.event.Event;
import io.github.plaguv.amqp.api.event.EventDomain;

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