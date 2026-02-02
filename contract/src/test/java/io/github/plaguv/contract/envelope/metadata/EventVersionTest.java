package io.github.plaguv.contract.envelope.metadata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EventVersionTest {

    @Test
    @DisplayName("Constructor should reject negative numbers for major, minor and patch")
    void throwsOnNegativeNumbers() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EventVersion(-1, 0, 0));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EventVersion(0, -1, 0));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EventVersion(0, 0, -1));
    }

    @Test
    @DisplayName("Should default to zero, if number isn't present")
    void defaultsToZero() {
        EventVersion eventVersion;

        eventVersion = new EventVersion(1);
        Assertions.assertEquals(1, eventVersion.major());
        Assertions.assertEquals(0, eventVersion.minor());
        Assertions.assertEquals(0, eventVersion.patch());

        eventVersion = new EventVersion(1, 2);
        Assertions.assertEquals(1, eventVersion.major());
        Assertions.assertEquals(2, eventVersion.minor());
        Assertions.assertEquals(0, eventVersion.patch());

        eventVersion = new EventVersion(1, 2, 3);
        Assertions.assertEquals(1, eventVersion.major());
        Assertions.assertEquals(2, eventVersion.minor());
        Assertions.assertEquals(3, eventVersion.patch());
    }
}