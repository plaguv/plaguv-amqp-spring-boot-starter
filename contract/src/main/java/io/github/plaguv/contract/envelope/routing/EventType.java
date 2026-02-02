package io.github.plaguv.contract.envelope.routing;

import io.github.plaguv.contract.event.EventInstance;
import io.github.plaguv.contract.event.StoreClosedEvent;
import io.github.plaguv.contract.event.StoreOpenedEvent;

import java.util.Arrays;

public enum EventType {

    STORE_OPENED(EventDomain.STORE, StoreOpenedEvent.class),
    STORE_CLOSED(EventDomain.STORE, StoreClosedEvent.class);

    private final EventDomain eventDomain;
    private final Class<? extends EventInstance> eventClass;

    EventType(EventDomain eventDomain, Class<? extends EventInstance> eventClass) {
        if (eventDomain == null) {
            throw new IllegalStateException(
                    "EventType attribute 'eventDomain' cannot be null for eventType '%s'"
                            .formatted(this.name())
            );
        }
        if (eventClass == null) {
            throw new IllegalStateException(
                    "EventType attribute 'eventClass' cannot be null for eventType '%s'"
                            .formatted(this.name())
            );
        }

        this.eventDomain = eventDomain;
        this.eventClass = eventClass;
    }

    public EventDomain getEventDomain() {
        return eventDomain;
    }

    public EventType[] ofEventDomain(EventDomain domain) {
        return Arrays.stream(EventType.values())
                .filter(e -> e.getEventDomain() == domain)
                .toArray(EventType[]::new);
    }

    public Class<? extends EventInstance> getEventClass() {
        return eventClass;
    }

    public EventType[] ofEventClass(Class<? extends EventInstance> eventClass) {
        return Arrays.stream(EventType.values())
                .filter(e -> e.getEventClass() == eventClass)
                .toArray(EventType[]::new);
    }
}