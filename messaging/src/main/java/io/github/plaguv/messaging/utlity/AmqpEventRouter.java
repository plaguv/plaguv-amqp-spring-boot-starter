package io.github.plaguv.messaging.utlity;

import io.github.plaguv.contract.envelope.EventEnvelope;
import io.github.plaguv.contract.envelope.EventType;
import io.github.plaguv.messaging.config.properties.AmqpProperties;
import jakarta.annotation.Nonnull;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

@Component
public class AmqpEventRouter implements EventRouter {

    private final AmqpProperties amqpProperties;

    //TODO: add consistent naming scheme

    public AmqpEventRouter(AmqpProperties amqpProperties) {
        this.amqpProperties = amqpProperties;
    }

    @Override
    public String resolveQueue(@Nonnull EventEnvelope eventEnvelope) {
//        return resolveQueue(eventEnvelope.routing().eventType());
        return "";
    }

    @Override
    public String resolveQueue(@NonNull EventType eventType) {
//        return "%s.%s.%s.queue".formatted(
//                amqpProperties.centralExchange(),
//                eventType.getEventDomain().name().toLowerCase(),
//                eventType.name().toLowerCase()
//        );
        return "";
    }

    @Override
    public String resolveExchange(@Nonnull EventEnvelope eventEnvelope) {
//        return "%s.%s.%s".formatted(
//                amqpProperties.centralExchange(),
//                eventEnvelope.routing().eventType().getEventDomain().name().toLowerCase(),
//                eventEnvelope.routing().eventDispatchType().name().toLowerCase()
//        );
        return "";
    }

    @Override
    public String resolveRoutingKey(@Nonnull EventEnvelope eventEnvelope) {
//        String domain = eventEnvelope.routing().eventType().getEventDomain().name().toLowerCase();
//        String name = eventEnvelope.routing().eventType().name().toLowerCase();
//
//        return switch (eventEnvelope.routing().eventDispatchType()) {
//            case DIRECT -> "%s.%s".formatted(domain, name);
//            case TOPIC -> "%s.%s".formatted(domain, name);
//            case BROADCAST -> "";
//        };
        return "";
    }

    @Override
    public String resolveBinding(@Nonnull EventEnvelope eventEnvelope) {
//        String domain = eventEnvelope.routing().eventType().getEventDomain().name().toLowerCase();
//        String name = eventEnvelope.routing().eventType().name().toLowerCase();
//
//        return switch (eventEnvelope.routing().eventDispatchType()) {
//            case DIRECT -> "%s.%s".formatted(domain, name);
//            case TOPIC -> "%s.%s.*".formatted(domain, name);
//            case BROADCAST -> "";
//        };
        return "";
    }
}