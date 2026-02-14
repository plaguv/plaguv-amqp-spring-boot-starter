package io.github.plaguv.contract.envelope;

import io.github.plaguv.contract.envelope.metadata.EventMetadata;
import io.github.plaguv.contract.envelope.payload.EventPayload;
import io.github.plaguv.contract.envelope.routing.EventRouting;

public record EventEnvelope(
        EventMetadata metadata,
        EventRouting routing,
        EventPayload payload
) {
    public EventEnvelope {
        if (metadata == null) {
            throw new IllegalArgumentException("EventEnvelope attribute 'metadata' cannot be null");
        }
        if (routing == null) {
            throw new IllegalArgumentException("EventEnvelope attribute 'routing' cannot be null");
        }
        if (payload == null) {
            throw new IllegalArgumentException("EventEnvelope attribute 'content' cannot be null");
        }
    }
}