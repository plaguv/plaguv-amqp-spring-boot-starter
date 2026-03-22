package io.github.plaguv.amqp.api.event.pos;

import io.github.plaguv.amqp.api.event.Event;
import io.github.plaguv.amqp.api.event.EventDomain;

@Event(domain = EventDomain.STORE)
public record ArticleUrgentOrderEvent(
        long storeId,
        long articleId,
        long quantity
) {
    public ArticleUrgentOrderEvent {
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