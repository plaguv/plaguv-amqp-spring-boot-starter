package io.github.plaguv.messaging.utlity;

import io.github.plaguv.contract.envelope.routing.EventScope;
import io.github.plaguv.contract.envelope.routing.EventRoutingDescriptor;
import io.github.plaguv.messaging.config.properties.AmqpProperties;
import io.github.plaguv.messaging.utlity.helper.ClassNameExtractor;
import jakarta.annotation.Nonnull;

public class AmqpEventRouter implements EventRouter {

    private final String CENTRAL_APPLICATION;

    public AmqpEventRouter(AmqpProperties amqpProperties) {
        CENTRAL_APPLICATION = amqpProperties.centralApplication().toLowerCase();
    }

    // TODO: naming scheme change in future. central exchange routing to multiple, with unique binding / routing-key. no need for types

    @Override
    public @Nonnull String resolveQueue(@Nonnull EventRoutingDescriptor eventRoutingDescriptor) {
        return "%s.%s.queue".formatted(
                CENTRAL_APPLICATION,
                ClassNameExtractor.extractUpperLower(eventRoutingDescriptor.type())
        );
    }

    @Override
    public @Nonnull String resolveExchange(@Nonnull EventRoutingDescriptor eventRoutingDescriptor) {
        return "%s.%s".formatted(
                ClassNameExtractor.extractUpperLower(eventRoutingDescriptor.type()),
                eventRoutingDescriptor.dispatchType().name().toLowerCase()
        );
    }

    @Override
    public @Nonnull String resolveRoutingKey(@Nonnull EventRoutingDescriptor eventRoutingDescriptor) {
        if (eventRoutingDescriptor.dispatchType() == EventScope.GROUP && eventRoutingDescriptor.wildcard().isPresent()) {
            return eventRoutingDescriptor.wildcard().get();
        } else {
            return "%s.%s.%s".formatted(
                    eventRoutingDescriptor.domain().name().toLowerCase(),
                    ClassNameExtractor.extractUpperLower(eventRoutingDescriptor.type()),
                    eventRoutingDescriptor.dispatchType().name().toLowerCase()
            );
        }
    }

    @Override
    public @Nonnull String resolveBinding(@Nonnull EventRoutingDescriptor eventRoutingDescriptor) {
        return "%s.%s.%s".formatted(
                eventRoutingDescriptor.domain().name().toLowerCase(),
                ClassNameExtractor.extractUpperLower(eventRoutingDescriptor.type()),
                eventRoutingDescriptor.dispatchType().name().toLowerCase()
        );
    }
}