package io.github.plaguv.messaging.utlity.converter;

import io.github.plaguv.contract.envelope.EventEnvelope;
import org.jspecify.annotations.NonNull;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import tools.jackson.databind.ObjectMapper;

public class EventEnvelopeAmqpConverter implements MessageConverter {

    private final ObjectMapper objectMapper;

    public EventEnvelopeAmqpConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Message toMessage(@NonNull Object object, @NonNull MessageProperties messageProperties) throws MessageConversionException {
        try {
            byte[] body = objectMapper.writeValueAsBytes(object);
            return new Message(body, messageProperties);
        } catch (Exception e) {
            throw new MessageConversionException("Failed to serialize EventEnvelope", e);
        }
    }

    @Override
    public Object fromMessage(@NonNull Message message) throws MessageConversionException {
        try {
            EventEnvelope envelope = objectMapper.readValue(message.getBody(), EventEnvelope.class);
            return envelope.payload().content();
        } catch (Exception e) {
            throw new MessageConversionException("Failed to convert EventEnvelope", e);
        }
    }
}