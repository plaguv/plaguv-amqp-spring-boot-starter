package io.github.plaguv.messaging.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "amqp")
public record AmqpProperties(
    String centralExchange
) {
    public AmqpProperties {
        if (centralExchange == null || centralExchange.isBlank()) {
            throw new IllegalArgumentException("Property 'amqp.central-exchange' cannot be null or blank");
        }
    }
}