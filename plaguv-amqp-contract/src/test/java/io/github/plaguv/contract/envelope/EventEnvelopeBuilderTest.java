package io.github.plaguv.contract.envelope;

import io.github.plaguv.contract.envelope.metadata.EventMetadata;
import io.github.plaguv.contract.envelope.payload.EventPayload;
import io.github.plaguv.contract.envelope.routing.EventRouting;
import io.github.plaguv.contract.envelope.routing.EventScope;
import io.github.plaguv.contract.event.pos.StoreOpenedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EventEnvelopeBuilderTest {

    private EventMetadata eventMetadata;
    private EventRouting eventRouting;
    private EventPayload eventPayload;

    @BeforeEach
    void beforeEach() {
        eventMetadata = EventMetadata.now();
        eventRouting = new EventRouting(
                EventScope.BROADCAST,
                null
        );
        eventPayload = EventPayload.valueOf(new StoreOpenedEvent(5L));
    }

    @Test
    @DisplayName("Builder instances should differ")
    void builderInstanceShouldDiffer() {
        EventEnvelopeBuilder empty = EventEnvelopeBuilder.defaults();
        EventEnvelopeBuilder withDefaults = EventEnvelopeBuilder.empty();

        Assertions.assertNotEquals(empty, withDefaults);
        Assertions.assertNotSame(empty, withDefaults);
    }

    @Test
    @DisplayName("Builder should throw if null parameter gets parsed to constructor")
    void throwsOnNullWithBuilder() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> EventEnvelopeBuilder.empty()
                        .build());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> EventEnvelopeBuilder.defaults()
                        .build());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> EventEnvelopeBuilder.empty()
                        .ofEventMetadata(null)
                        .ofEventRouting(null)
                        .ofEventPayload(null)
                        .build());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> EventEnvelopeBuilder.empty()
                        .ofEventMetadata(eventMetadata)
                        .ofEventRouting(eventRouting)
                        .ofEventPayload(null)
                        .build());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> EventEnvelopeBuilder.empty()
                        .ofEventMetadata(eventMetadata)
                        .ofEventRouting(null)
                        .ofEventPayload(eventPayload)
                        .build());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> EventEnvelopeBuilder.empty()
                        .ofEventMetadata(null)
                        .ofEventRouting(eventRouting)
                        .ofEventPayload(eventPayload)
                        .build());
    }

    @Test
    @DisplayName("Builder should keep values it gets handed in built EventEnvelope")
    void builderKeepsValues() {
        EventEnvelope eventEnvelope = EventEnvelopeBuilder.empty()
                .ofEventMetadata(eventMetadata)
                .ofEventRouting(eventRouting)
                .ofEventPayload(eventPayload)
                .build();

        Assertions.assertEquals(eventMetadata, eventEnvelope.metadata());
        Assertions.assertEquals(eventRouting, eventEnvelope.routing());
        Assertions.assertEquals(eventPayload, eventEnvelope.payload());

        eventEnvelope = EventEnvelopeBuilder.empty()
                .withEventId(eventMetadata.eventId())
                .withOccurredAt(eventMetadata.occurredAt())
                .withProducer(eventMetadata.producer())
                .withScope(eventRouting.scope())
                .withWildcard(eventRouting.wildcard())
                .withContentType(eventPayload.contentType())
                .withContent(eventPayload.content())
                .build();

        Assertions.assertEquals(eventMetadata, eventEnvelope.metadata());
        Assertions.assertEquals(eventRouting, eventEnvelope.routing());
        Assertions.assertEquals(eventPayload, eventEnvelope.payload());
    }
}