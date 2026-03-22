package io.github.plaguv.amqp.starter.properties;

import io.github.plaguv.amqp.core.utlity.properties.AmqpStartupProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "amqp.skip")
public record AmqpStartupPropertiesConfiguration(
        Boolean registerListeners
) implements AmqpStartupProperties {
    public AmqpStartupPropertiesConfiguration {
        registerListeners = registerListeners != null
                ? registerListeners
                : false;
    }
}