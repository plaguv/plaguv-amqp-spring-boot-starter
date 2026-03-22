package io.github.plaguv.amqp.core.utlity.converter;

import io.github.plaguv.amqp.api.envelope.EventEnvelope;
import io.github.plaguv.amqp.api.event.Event;
import io.github.plaguv.amqp.core.utlity.helper.ClassNameExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import tools.jackson.databind.ObjectMapper;

import java.util.Date;

public class EventPayloadByteConverter implements MessageConverter {

    private static final Logger log = LoggerFactory.getLogger(EventPayloadByteConverter.class);

    private final ObjectMapper objectMapper;

    public EventPayloadByteConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        log.atDebug().log("Serializing EventEnvelope to Message");
        try {
            if (!(object instanceof EventEnvelope eventEnvelope)) {
                throw new MessageConversionException("Unsupported payload type '%s'".formatted(object.getClass()));
            }

            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            messageProperties.setContentEncoding("utf-8");
            messageProperties.setMessageId(eventEnvelope.metadata().eventId().toString());
            messageProperties.setCorrelationId(eventEnvelope.metadata().eventId().toString());
            messageProperties.setTimestamp(Date.from(eventEnvelope.metadata().occurredAt()));

            Class<?> contentType = eventEnvelope.payload().contentType();
            Event eventAnnotation = contentType.getAnnotation(Event.class);

            if (eventAnnotation != null) {
                messageProperties.setHeader(
                        "x-event-domain",
                        eventAnnotation.domain().name().toLowerCase()
                );
                messageProperties.setHeader(
                        "x-event-version",
                        eventAnnotation.version()
                );
            }

            messageProperties.setHeader(
                    "x-producer",
                    eventEnvelope.metadata().producer() != null
                            ? ClassNameExtractor.extractUpperLower(eventEnvelope.metadata().producer())
                            : ClassNameExtractor.extractUpperLower(
                            org.springframework.aop.support.AopUtils.getTargetClass(this)
                    )
            );

            byte[] body = objectMapper.writeValueAsBytes(eventEnvelope);
            log.atDebug().log("Successfully serialized EventPayload to Message");
            return new Message(body, messageProperties);
        } catch (Exception e) {
            throw new MessageConversionException("Failed to serialize EventEnvelope", e);
        }
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        log.atDebug().log("Deserializing Message to EventPayload");
        try {
            EventEnvelope eventEnvelope = objectMapper.readValue(message.getBody(), EventEnvelope.class);
            log.atDebug().log("Successfully deserialized Message to EventPayload");
            return eventEnvelope;
        } catch (Exception e) {
            throw new MessageConversionException("Failed to deserialize Message", e);
        }
    }
}