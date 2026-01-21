package io.github.plaguv.messaging.config.autoconfiguration;

import io.github.plaguv.messaging.config.properties.AmqpProperties;
import io.github.plaguv.messaging.listener.AmqpEventListenerRegistrar;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(after = AmqpAutoConfiguration.class)
@EnableConfigurationProperties(AmqpProperties.class)
public class AmqpEventListenerAutoConfiguration {

    public AmqpEventListenerAutoConfiguration() {}

    @Bean
    @ConditionalOnMissingBean(AmqpEventListenerRegistrar.class)
    public AmqpEventListenerRegistrar amqpEventListenerRegistrar(AmqpAdmin amqpAdmin) {
        return new AmqpEventListenerRegistrar(amqpAdmin);
    }
}