package io.github.plaguv.messaging.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "amqp")
public record AmqpProperties(
    String exchange
) {
    public AmqpProperties {
        if (exchange == null || exchange.isBlank()) {
            throw new IllegalArgumentException("Property 'amqp.exchange' cannot be null, empty, or blank");
        }
    }
}