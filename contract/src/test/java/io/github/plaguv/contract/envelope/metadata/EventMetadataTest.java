package io.github.plaguv.contract.envelope.metadata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

class EventMetadataTest {

    private UUID uuid;
    private EventVersion eventVersion;
    private Instant instant;
    private Class<?> producer;


    @BeforeEach
    void beforeEach() {
        uuid = UUID.randomUUID();
        eventVersion = EventVersion.valueOf(1);
        instant = Instant.now();
        producer = EventMetadataTest.class;
    }

    @Test
    @DisplayName("Should throw if null parameter is present in constructor")
    void throwsOnNull() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EventMetadata(null, null, null, null));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EventMetadata(uuid, eventVersion, instant, null));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EventMetadata(uuid, eventVersion, null, producer));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EventMetadata(uuid, null, instant, producer));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EventMetadata(null, eventVersion, instant, producer));
    }

    @Test
    @DisplayName("Should construct with defaults if parameters are omitted in constructor")
    void constructsWithMinimalParameters() {
        Assertions.assertDoesNotThrow(
                () -> new EventMetadata(eventVersion, producer));
        Assertions.assertDoesNotThrow(
                () -> new EventMetadata(producer));
    }

    @Test
    @DisplayName("Constructor should keep field values")
    void constructorKeepsParameters() {
        EventMetadata eventMetadata;

        eventMetadata = new EventMetadata(uuid, eventVersion, instant, producer);
        Assertions.assertEquals(uuid, eventMetadata.eventId());
        Assertions.assertEquals(eventVersion, eventMetadata.eventVersion());
        Assertions.assertEquals(instant, eventMetadata.occurredAt());
        Assertions.assertEquals(producer, eventMetadata.producer());
    }

    @Test
    @DisplayName("Constructor should reevaluate fields, if omitted from call")
    void constructorReevaluatesParametersIfOmitted() {
        EventMetadata eventMetadata;



        eventMetadata = new EventMetadata(eventVersion, producer);
        Assertions.assertNotEquals(uuid, eventMetadata.eventId());
        Assertions.assertEquals(eventVersion, eventMetadata.eventVersion());
//        Assertions.assertNotEquals(instant.hashCode(), eventMetadata.occurredAt());
        Assertions.assertEquals(producer, eventMetadata.producer());

        eventMetadata = new EventMetadata(producer);
        Assertions.assertNotEquals(uuid, eventMetadata.eventId());
        Assertions.assertEquals(eventVersion, eventMetadata.eventVersion());
//        Assertions.assertNotEquals(instant.hashCode(), eventMetadata.occurredAt());
        Assertions.assertEquals(producer, eventMetadata.producer());
    }
}