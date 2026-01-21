package io.github.plaguv.messaging.publisher;

import io.github.plaguv.contracts.common.EventEnvelope;

public interface TopologyDeclarer {

    /**
     *
     * @param exchangeName e
     * @param eventEnvelope e
     */
    void declareExchangeIfAbsent(String exchangeName, EventEnvelope eventEnvelope);

    /**
     *
     * @param queueName e
     * @param eventEnvelope e
     */
    void declareQueueIfAbsent(String queueName, EventEnvelope eventEnvelope);

    /**
     *
     * @param bindingName e
     * @param eventEnvelope e
     */
    void declareBindingIfAbsent(String bindingName, EventEnvelope eventEnvelope);
}