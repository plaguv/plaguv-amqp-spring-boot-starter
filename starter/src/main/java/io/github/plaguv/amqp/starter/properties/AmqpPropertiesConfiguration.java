package io.github.plaguv.amqp.starter.properties;

import io.github.plaguv.amqp.core.utlity.properties.AmqpProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "amqp")
public record AmqpPropertiesConfiguration(
        String centralExchange,
        String centralApplication
) implements AmqpProperties {
        public AmqpPropertiesConfiguration {
                if (centralExchange == null || centralExchange.isBlank()) {
                        throw new IllegalStateException("property 'amqp.central-exchange' is required and cannot be blank");
                }
                if (centralApplication == null || centralApplication.isBlank()) {
                        throw new IllegalStateException("property 'amqp.central-application' is required and cannot be blank");
                }
        }
}