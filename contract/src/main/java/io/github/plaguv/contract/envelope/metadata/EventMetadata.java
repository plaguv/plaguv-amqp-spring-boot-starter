package io.github.plaguv.contract.envelope.metadata;

import java.time.Instant;
import java.util.UUID;

public record EventMetadata(
        UUID eventId,
        EventVersion eventVersion,
        Instant occurredAt,
        Class<?> producer
) {
    public EventMetadata {
        if (eventId == null) {
            throw new IllegalArgumentException("EventMetadata attribute 'eventId' cannot be null");
        }
        if (eventVersion == null) {
            throw new IllegalArgumentException("EventMetadata attribute 'eventVersion' cannot be null");
        }
        if (occurredAt == null) {
            throw new IllegalArgumentException("EventMetadata attribute 'occurredAt' cannot be null");
        }
        if (producer == null) {
            throw new IllegalArgumentException("EventMetadata attribute 'producer' cannot be null");
        }
    }

    public EventMetadata(EventVersion eventVersion, Class<?> producer) {
        this(
                UUID.randomUUID(),
                eventVersion,
                Instant.now(),
                producer
        );
    }

    @Override
    public String toString() {
        return "EventMetadata{" +
                "eventId=" + eventId +
                ", eventVersion=" + eventVersion +
                ", occurredAt=" + occurredAt +
                ", producer=" + producer +
                '}';
    }
}