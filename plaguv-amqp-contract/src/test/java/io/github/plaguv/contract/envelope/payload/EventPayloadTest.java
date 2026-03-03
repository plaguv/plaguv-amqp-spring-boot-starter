package io.github.plaguv.contract.envelope.payload;

import io.github.plaguv.contract.event.pos.LogisticArticleOrderEvent;
import io.github.plaguv.contract.event.pos.LogisticArticleUrgentOrderEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EventPayloadTest {

    private Class<?> payloadType;
    private Object payload;

    @BeforeEach
    void beforeEach() {
        payloadType = LogisticArticleOrderEvent.class;
        payload = new LogisticArticleOrderEvent(1, 1, 1);
    }

    @Test
    @DisplayName("Constructor should throw if null parameter is present in constructor")
    void throwsOnNull() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EventPayload(null, null));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EventPayload((Object) null));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> EventPayload.empty(null));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EventPayload(null));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> EventPayload.valueOf(null));
    }

    @Test
    @DisplayName("Constructor accepts payloads with no type specified")
    void constructorInfersContentTypeFromPayload() {
        Assertions.assertDoesNotThrow(
                () -> new EventPayload(null, payload));
        Assertions.assertDoesNotThrow(
                () -> new EventPayload(payload));
        Assertions.assertDoesNotThrow(
                () -> EventPayload.valueOf(payload));
    }


    @Test
    @DisplayName("Constructor accepts empty payloads, that specify a type")
    void constructorAcceptsEmptyPayloads() {
        Assertions.assertDoesNotThrow(
                () -> new EventPayload(payloadType, null));
        Assertions.assertDoesNotThrow(
                () -> new EventPayload(payloadType));
        Assertions.assertDoesNotThrow(
                () -> EventPayload.empty(payloadType));
    }

    @Test
    @DisplayName("Constructor should only accept classes that implement @Event")
    void constructorAcceptsOnlyEventContent() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> EventPayload.empty(Object.class));
        Assertions.assertDoesNotThrow(
                () -> EventPayload.empty(LogisticArticleOrderEvent.class));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> EventPayload.valueOf(new Object()));
        Assertions.assertDoesNotThrow(
                () -> EventPayload.valueOf(payload));
    }

    @Test
    @DisplayName("Constructor should only accept parameters that point towards the same event (contentType/content)")
    void constructorAcceptsOnlyMatchingEventContent() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EventPayload(LogisticArticleUrgentOrderEvent.class, payload));

        Assertions.assertDoesNotThrow(
                () -> new EventPayload(payloadType, payload));
    }
}