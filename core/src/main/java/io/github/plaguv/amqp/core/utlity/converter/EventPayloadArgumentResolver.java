package io.github.plaguv.amqp.core.utlity.converter;

import io.github.plaguv.amqp.api.envelope.EventEnvelope;
import io.github.plaguv.amqp.api.event.Event;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;

public class EventPayloadArgumentResolver implements HandlerMethodArgumentResolver {

    private static final Logger log = LoggerFactory.getLogger(EventPayloadArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Event.class) || parameter.getParameterType().isAnnotationPresent(Event.class);
    }

    @Override
    public @Nullable Object resolveArgument(MethodParameter parameter, Message<?> message) throws MessageConversionException {
        log.atDebug().log("Resolving event payload arguments for event payload argument resolver");
        try {
            EventEnvelope envelope = (EventEnvelope) message.getPayload();
            Object payload = envelope.payload().contentType().cast(envelope.payload().content());
            log.atDebug().log("Resolved event payload arguments for event payload argument resolver");
            return payload;
        } catch (MessageConversionException e) {
            throw new MessageConversionException("Could not convert payload to EventPayload", e);
        }
    }
}