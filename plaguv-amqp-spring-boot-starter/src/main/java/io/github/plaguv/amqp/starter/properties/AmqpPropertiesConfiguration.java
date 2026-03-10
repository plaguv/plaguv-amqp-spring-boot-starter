package io.github.plaguv.amqp.starter.properties;

import io.github.plaguv.amqp.core.utlity.properties.AmqpProperties;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "amqp")
@Validated
public record AmqpPropertiesConfiguration(
        @NotBlank
        String centralExchange,
        @NotBlank
        String centralApplication
) implements AmqpProperties {}