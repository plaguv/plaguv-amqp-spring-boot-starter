package io.github.plaguv.contract.envelope.routing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EventRoutingTest {

    @Test
    @DisplayName("Should throw if null parameter in constructor")
    void throwsOnNull() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EventRouting(null));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EventRouting(null, null));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> EventRouting.valueOf(null));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> EventRouting.valueOf(null, null));
    }

    @Test
    @DisplayName("Constructor should keep field values")
    void constructorKeepsParameters() {
        String wildcard = "topic";
        EventDispatchType eventDispatchType = EventDispatchType.FANOUT;
        EventRouting eventRouting;

        eventRouting = new EventRouting(eventDispatchType, wildcard);
        Assertions.assertEquals(eventDispatchType, eventRouting.eventDispatchType());
        Assertions.assertEquals(wildcard, eventRouting.eventWildcard());

        eventRouting = new EventRouting(eventDispatchType);
        Assertions.assertEquals(eventDispatchType, eventRouting.eventDispatchType());
        Assertions.assertNotEquals(wildcard, eventRouting.eventWildcard());

        eventRouting = EventRouting.valueOf(eventDispatchType, wildcard);
        Assertions.assertEquals(eventDispatchType, eventRouting.eventDispatchType());
        Assertions.assertEquals(wildcard, eventRouting.eventWildcard());

        eventRouting = EventRouting.valueOf(eventDispatchType);
        Assertions.assertEquals(eventDispatchType, eventRouting.eventDispatchType());
        Assertions.assertNotEquals(wildcard, eventRouting.eventWildcard());
    }
}