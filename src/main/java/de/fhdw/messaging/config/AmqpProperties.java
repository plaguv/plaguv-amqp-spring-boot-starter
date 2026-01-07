package de.fhdw.messaging.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fhdw.messaging")
public class AmqpProperties implements InitializingBean {

    private String exchange;

    public AmqpProperties() {}

    @Override
    public void afterPropertiesSet() throws Exception {
        if (exchange == null || exchange.isBlank()) {
            throw new IllegalStateException("Missing required property: fhdw.messaging.exchange");
        }
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
}