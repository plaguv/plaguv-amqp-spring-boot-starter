package io.github.plaguv.messaging.listener;

import io.github.plaguv.contracts.DomainEvent;
import io.github.plaguv.contracts.common.EventEnvelope;

public interface EventListener<T extends DomainEvent> {
    void handleMessage(EventEnvelope<T> eventEnvelope);
}