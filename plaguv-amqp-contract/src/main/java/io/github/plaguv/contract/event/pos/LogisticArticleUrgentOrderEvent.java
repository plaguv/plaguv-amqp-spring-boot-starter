package io.github.plaguv.contract.event.pos;

import io.github.plaguv.contract.event.Event;
import io.github.plaguv.contract.event.EventDomain;

@Event(domain = EventDomain.STORE)
public record LogisticArticleUrgentOrderEvent(
        long storeId,
        long articleId,
        long quantity
) {
    public LogisticArticleUrgentOrderEvent {
        if (storeId < 0) {
            throw new IllegalArgumentException("LogisticArticleOrderEvent parameter 'storeId' cannot be negative");
        }
        if (articleId < 0) {
            throw new IllegalArgumentException("LogisticArticleOrderEvent parameter 'articleId' cannot be negative");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("LogisticArticleOrderEvent parameter 'quantity' cannot be negative");
        }
    }
}