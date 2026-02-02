package io.github.plaguv.contract.envelope.metadata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

class EventMetadataTest {

    @Test
    @DisplayName("Should throw if null parameter in constructor")
    void throwsOnNull() {
        UUID uuid = UUID.randomUUID();
        EventVersion eventVersion = new EventVersion(1);
        Instant instant = Instant.now();
        Class<?> producer = EventMetadataTest.class;

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
}