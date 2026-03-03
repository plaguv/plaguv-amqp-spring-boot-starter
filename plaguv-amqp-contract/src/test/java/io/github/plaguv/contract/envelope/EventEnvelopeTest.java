package io.github.plaguv.contract.envelope;

import io.github.plaguv.contract.envelope.metadata.EventMetadata;
import io.github.plaguv.contract.envelope.payload.EventPayload;
import io.github.plaguv.contract.envelope.routing.EventScope;
import io.github.plaguv.contract.envelope.routing.EventRouting;
import io.github.plaguv.contract.event.pos.LogisticArticleOrderEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EventEnvelopeTest {

    @Test
    @DisplayName("Constructor should throw if null parameter in constructor")
    void throwsOnNull() {
        EventMetadata eventMetadata = EventMetadata.now();
        EventRouting eventRouting = new EventRouting(EventScope.BROADCAST, null);
        EventPayload eventPayload = EventPayload.valueOf(new LogisticArticleOrderEvent(1, 1, 1));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EventEnvelope(null, null, null));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EventEnvelope(eventMetadata, eventRouting, null));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EventEnvelope(eventMetadata, null, eventPayload));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EventEnvelope(null, eventRouting, eventPayload));
    }
}