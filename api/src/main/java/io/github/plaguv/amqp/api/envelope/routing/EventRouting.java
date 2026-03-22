package io.github.plaguv.amqp.api.envelope.routing;

import org.jspecify.annotations.Nullable;

public record EventRouting(
        EventScope scope,
        @Nullable String wildcard
) {
    public EventRouting {
        if (scope == null) {
            throw new IllegalArgumentException("EventRouting attribute 'scope' cannot be null");
        }
        if (scope != EventScope.BROADCAST && (wildcard == null || wildcard.isBlank())) {
            throw new IllegalArgumentException(
                    "EventRouting attribute 'wildcard' cannot be null or empty when scope is set to '%s' or '%s'"
                    .formatted(EventScope.GROUP, EventScope.TARGET)
            );
        }
    }
}