package io.github.plaguv.contract.envelope;

import io.github.plaguv.contract.envelope.payload.EventInstance;
import io.github.plaguv.contract.envelope.metadata.EventMetadata;
import io.github.plaguv.contract.envelope.metadata.EventVersion;
import io.github.plaguv.contract.envelope.routing.EventDispatchType;
import io.github.plaguv.contract.envelope.routing.EventRouting;
import jakarta.annotation.Nonnull;

import java.time.Instant;
import java.util.UUID;

public record EventEnvelope(
        EventMetadata metadata,
        EventRouting routing,
        EventInstance payload
) {
    public EventEnvelope {
        if (metadata == null) {
            throw new IllegalArgumentException("EventEnvelope attribute 'metadata' cannot be null");
        }
        if (routing == null) {
            throw new IllegalArgumentException("EventEnvelope attribute 'routing' cannot be null");
        }
        if (payload == null) {
            throw new IllegalArgumentException("EventEnvelope attribute 'payload' cannot be null");
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        // Default values
        private UUID eventId = UUID.randomUUID();
        private EventVersion eventVersion = EventVersion.valueOf(1);
        private Instant occurredAt = Instant.now();
        private Class<?> producer;

        private EventDispatchType eventDispatchType = EventDispatchType.FANOUT;
        private String eventWildcard = "";

        private EventInstance payload;

        private Builder() {}

        public Builder ofMetadata(EventMetadata metadata) {
            if (metadata == null) {
                throw new IllegalArgumentException("EventMetadata attribute 'metadata' cannot be null");
            }
            this.eventId = metadata.eventId();
            this.eventVersion = metadata.eventVersion();
            this.occurredAt = metadata.occurredAt();
            this.producer = metadata.producer();
            return this;
        }

        public Builder withEventId(UUID eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder withEventVersion(EventVersion eventVersion) {
            this.eventVersion = eventVersion;
            return this;
        }

        public Builder withOccurredAt(Instant occurredAt) {
            this.occurredAt = occurredAt;
            return this;
        }

        public Builder withProducer(Class<?> producer) {
            this.producer = producer;
            return this;
        }

        public Builder ofRouting(EventRouting eventRouting) {
            if (eventRouting == null) {
                throw new IllegalArgumentException("EventRouting attribute 'routing' cannot be null");
            }
            this.eventDispatchType = eventRouting.eventDispatchType();
            this.eventWildcard = eventRouting.eventWildcard();
            return this;
        }

        public Builder withEventDispatchType(EventDispatchType eventDispatchType) {
            this.eventDispatchType = eventDispatchType;
            return this;
        }

        public Builder withWildcard(String eventWildcard) {
            this.eventWildcard = eventWildcard;
            return this;
        }

        public Builder ofPayload(EventInstance payload) {
            this.payload = payload;
            return this;
        }

        public EventEnvelope build() {
            EventMetadata metadata = new EventMetadata(
                    eventId,
                    eventVersion,
                    occurredAt,
                    producer
            );

            EventRouting routing = new EventRouting(
                    eventDispatchType,
                    eventWildcard
            );

            return new EventEnvelope(
                    metadata,
                    routing,
                    payload
            );
        }
    }

    @Override
    public @Nonnull String toString() {
        return "EventEnvelope{" +
                "metadata=" + metadata +
                ", routing=" + routing +
                ", payload=" + payload +
                '}';
    }
}