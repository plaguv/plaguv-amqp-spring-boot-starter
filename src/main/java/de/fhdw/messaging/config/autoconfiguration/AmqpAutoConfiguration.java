package de.fhdw.messaging.config.autoconfiguration;

import de.fhdw.messaging.config.properties.AmqpProperties;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.boot.amqp.autoconfigure.RabbitTemplateCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(AmqpProperties.class)
public class AmqpAutoConfiguration {

    public AmqpAutoConfiguration() {}

    @Bean
    @ConditionalOnMissingBean(RabbitTemplateCustomizer.class)
    public RabbitTemplateCustomizer rabbitTemplateCustomizer(JacksonJsonMessageConverter converter) {
        return template -> template.setMessageConverter(converter);
    }

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