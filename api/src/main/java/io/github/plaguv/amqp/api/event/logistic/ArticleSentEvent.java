package io.github.plaguv.amqp.api.event.logistic;

import io.github.plaguv.amqp.api.event.Event;
import io.github.plaguv.amqp.api.event.EventDomain;

@Event(domain = EventDomain.LOGISTIC)
public record ArticleSentEvent(
        long storeId,
        long articleId,
        long articleAmount
) {
    public ArticleSentEvent {
        if (storeId < 1) {
            throw new IllegalArgumentException("storeId must be greater than 0");
        }
        if (articleId < 1) {
            throw new IllegalArgumentException("articleId must be greater than 0");
        }
        if (articleAmount < 1) {
            throw new IllegalArgumentException("articleAmount must be greater than 0");
        }
    }
}