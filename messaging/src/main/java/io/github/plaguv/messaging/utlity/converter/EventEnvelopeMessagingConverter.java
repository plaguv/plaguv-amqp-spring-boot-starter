package io.github.plaguv.messaging.utlity.converter;

import io.github.plaguv.contract.envelope.EventEnvelope;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.converter.MessageConverter;

public class EventEnvelopeMessagingConverter implements MessageConverter {

    public EventEnvelopeMessagingConverter() {}

    @Override
    public @Nullable Object fromMessage(Message<?> message, @NonNull Class<?> targetClass) {
        Object payload = message.getPayload();

        if (!(payload instanceof EventEnvelope envelope)) {
            return payload;
        }

        Object content = envelope.payload().content();

        if (!targetClass.isInstance(content)) {
            throw new MessageConversionException(
                    "Expected %s but got %s"
                            .formatted(targetClass, content.getClass())
            );
        }

        return content;
    }

    @Override
    public @Nullable Message<?> toMessage(@NonNull Object payload, @Nullable MessageHeaders headers) {
        return org.springframework.messaging.support.MessageBuilder
                .withPayload(payload)
                .copyHeaders(headers)
                .build();
    }
}
