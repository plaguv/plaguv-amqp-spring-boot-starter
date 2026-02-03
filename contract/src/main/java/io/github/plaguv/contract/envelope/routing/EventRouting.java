package io.github.plaguv.contract.envelope.routing;

import jakarta.annotation.Nonnull;

public record EventRouting(
   EventDispatchType eventDispatchType,
   String eventWildcard
) {
    public EventRouting {
        if (eventDispatchType == null) {
            throw new IllegalArgumentException("EventRouting attribute 'eventDispatchType' cannot be null");
        }
        if (eventWildcard == null) {
            eventWildcard = "";
        }
    }

    public EventRouting(EventDispatchType eventDispatchType) {
        this(eventDispatchType, "");
    }

    public static EventRouting valueOf(EventDispatchType eventDispatchType) {
        return new EventRouting(eventDispatchType, null);
    }

    public static EventRouting valueOf(EventDispatchType eventDispatchType, String wildcard) {
        return new EventRouting(eventDispatchType, wildcard);
    }

    @Override
    public @Nonnull String toString() {
        return "EventRouting{" +
                "eventDispatchType=" + eventDispatchType +
                ", eventWildcard='" + eventWildcard + '\'' +
                '}';
    }
}