package io.github.plaguv.amqp.core.listener;

import org.jspecify.annotations.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

public class MessageRejectedException extends MessagingException {
    public MessageRejectedException(String description) {
        super(description);
    }

    public MessageRejectedException(Message<?> undeliveredMessage) {
        super(undeliveredMessage);
    }

    public MessageRejectedException(Message<?> undeliveredMessage, String description) {
        super(undeliveredMessage, description);
    }

    public MessageRejectedException(Message<?> message, @Nullable Throwable cause) {
        super(message, cause);
    }

    public MessageRejectedException(Message<?> undeliveredMessage, String description, @Nullable Throwable cause) {
        super(undeliveredMessage, description, cause);
    }
}