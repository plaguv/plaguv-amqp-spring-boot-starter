package io.github.plaguv.messaging.listener;

import io.github.plaguv.contracts.common.EventEnvelope;

public interface EventListener {
    void handleMessage(EventEnvelope eventEnvelope);
}