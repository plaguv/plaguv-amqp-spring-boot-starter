package io.github.plaguv.contract.envelope;

import io.github.plaguv.contract.envelope.metadata.EventMetadata;
import io.github.plaguv.contract.envelope.payload.EventPayload;
import io.github.plaguv.contract.envelope.routing.EventRouting;
import io.github.plaguv.contract.envelope.routing.EventScope;

import java.time.Instant;
import java.util.UUID;

public final class EventEnvelopeBuilder {

    // EventMetadata fields
    private UUID eventId;
    private Instant occurredAt;
    private Class<?> producer;

    // EventRouting fields
    private EventScope scope;
    private String wildcard;

    // EventPayload fields
    private Class<?> type;
    private Object content;

    private EventEnvelopeBuilder() {}

    public static EventEnvelopeBuilder defaults() {
        EventEnvelopeBuilder builder = new EventEnvelopeBuilder();
        builder.eventId = UUID.randomUUID();
        builder.occurredAt = Instant.now();
        builder.scope = EventScope.BROADCAST;
        return builder;
    }

    public static EventEnvelopeBuilder empty() {
        return new EventEnvelopeBuilder();
    }

    public EventEnvelopeBuilder ofEventMetadata(EventMetadata eventMetadata) {
        if (eventMetadata == null) {
            throw new IllegalArgumentException("Parameter 'eventMetadata' cannot be null");
        }
        this.eventId = eventMetadata.eventId();
        this.occurredAt = eventMetadata.occurredAt();
        this.producer = eventMetadata.producer();
        return this;
    }

    public EventEnvelopeBuilder withEventId(UUID eventId) {
        this.eventId = eventId;
        return this;
    }

    public EventEnvelopeBuilder withOccurredAt(Instant occurredAt) {
        this.occurredAt = occurredAt;
        return this;
    }

    public EventEnvelopeBuilder withProducer(Class<?> producer) {
        this.producer = producer;
        return this;
    }

    public EventEnvelopeBuilder ofEventRouting(EventRouting eventRouting) {
        if (eventRouting == null) {
            throw new IllegalArgumentException("Parameter 'eventRouting' cannot be null");
        }
        this.scope = eventRouting.scope();
        this.wildcard = eventRouting.wildcard();
        return this;
    }

    public EventEnvelopeBuilder withScope(EventScope scope) {
        this.scope = scope;
        return this;
    }

    public EventEnvelopeBuilder withWildcard(String wildcard) {
        this.wildcard = wildcard;
        return this;
    }

    public EventEnvelopeBuilder ofEventPayload(EventPayload eventPayload) {
        if (eventPayload == null) {
            throw new IllegalArgumentException("Parameter 'eventPayload' cannot be null");
        }
        this.type = eventPayload.contentType();
        this.content = eventPayload.content();
        return this;
    }

    public EventEnvelopeBuilder withContent(Object content) {
        this.content = content;
        return this;
    }

    public EventEnvelopeBuilder withContentType(Class<?> type) {
        this.type = type;
        return this;
    }

    public EventEnvelope build() {
        EventMetadata eventMetadata = new EventMetadata(
                eventId,
                occurredAt,
                producer
        );

        EventRouting eventRouting = new EventRouting(
                scope,
                wildcard
        );

        EventPayload eventPayload = new EventPayload(
                type,
                content
        );

        return new EventEnvelope(
                eventMetadata,
                eventRouting,
                eventPayload
        );
    }
}