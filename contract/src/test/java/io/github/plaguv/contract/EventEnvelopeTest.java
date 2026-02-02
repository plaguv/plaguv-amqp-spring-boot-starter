package io.github.plaguv.contract;

import io.github.plaguv.contract.envelope.EventEnvelope;
import io.github.plaguv.contract.event.EventInstance;
import io.github.plaguv.contract.envelope.metadata.EventMetadata;
import io.github.plaguv.contract.envelope.metadata.EventVersion;
import io.github.plaguv.contract.envelope.routing.EventDispatchType;
import io.github.plaguv.contract.envelope.routing.EventType;
import io.github.plaguv.contract.event.StoreClosedEvent;
import io.github.plaguv.contract.event.StoreOpenedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EventEnvelopeTest {

    private EventMetadata metadata;
    private EventInstance payload;

    @BeforeEach
    void beforeEach() {
        metadata = new EventMetadata(
                new EventVersion(1),
                EventEnvelopeTest.class
        );
        payload = new StoreOpenedEvent(5L);
    }

    @Test
    @DisplayName("Should throw if null parameter in constructor")
    void throwsOnNull() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EventEnvelope(null, null));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EventEnvelope(metadata, null));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EventEnvelope(null, payload));
    }

    @Test
    @DisplayName("Should throw if null parameter in builder")
    void builderThrowsOnNull() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> EventEnvelope.builder()
                        .build());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> EventEnvelope.builder()
                        .ofMetadata(metadata)
                        .ofMetadata(null)
                        .build());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> EventEnvelope.builder()
                        .ofMetadata(metadata)
                        .ofRouting(null)
                        .ofMetadata(metadata)
                        .build());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> EventEnvelope.builder()
                        .ofMetadata(null)
                        .ofMetadata(metadata)
                        .build());
    }

    @Test
    @DisplayName("Builder should keep given values")
    void builderConstructsWithGivenValues() {
        EventEnvelope eventEnvelope = EventEnvelope.builder()
                .ofMetadata(metadata)
                .ofPayload(payload)
                .build();

        Assertions.assertEquals(metadata, eventEnvelope.metadata());
        Assertions.assertEquals(payload, eventEnvelope.payload());
    }

    @Test
    @DisplayName("Builder should construct with only method parameters")
    void builderConstructsWithMethodParametersOnly() {
        Assertions.assertDoesNotThrow(() ->
                EventEnvelope.builder()
                        .withEventVersion(new EventVersion(1))
                        .withProducer(EventEnvelopeTest.class)
                        .withEventType(EventType.STORE_OPENED)
                        .withEventDispatchType(EventDispatchType.DIRECT)
                        .ofPayload(payload)
                        .build());
    }

    @Test
    @DisplayName("Builder should construct with minimal parameters")
    void builderConstructsWithMinimalValues() {
        metadata = new EventMetadata(
                new EventVersion(1),
                EventEnvelopeTest.class
        );
        Assertions.assertDoesNotThrow(() ->
                EventEnvelope.builder()
                        .ofMetadata(metadata)
                        .ofPayload(payload)
                        .build());

        Assertions.assertDoesNotThrow(() ->
                EventEnvelope.builder()
                        .withEventVersion(new EventVersion(1))
                        .withProducer(EventEnvelopeTest.class)
                        .ofPayload(payload)
                        .build());
    }
}