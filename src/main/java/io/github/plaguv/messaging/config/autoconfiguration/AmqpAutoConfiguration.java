package io.github.plaguv.messaging.config.autoconfiguration;

import io.github.plaguv.messaging.config.properties.AmqpProperties;
import org.springframework.amqp.core.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(AmqpProperties.class)
public class AmqpAutoConfiguration {

    public AmqpAutoConfiguration() {}

    @Bean(name = "internalExchange")
    @ConditionalOnMissingBean(name = "internalExchange")
    public DirectExchange internalExchange(AmqpProperties properties) {
        return new DirectExchange(
                properties.getInternalExchange(),
                true,
                false
        );
    }

    @Bean(name = "externalExchange")
    @ConditionalOnMissingBean(name = "externalExchange")
    public DirectExchange externalExchange(AmqpProperties properties) {
        return new DirectExchange(
                properties.getExternalExchange(),
                true,
                false
        );
    }
}