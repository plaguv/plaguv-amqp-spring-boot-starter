package io.github.plaguv.messaging.config.properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fhdw.messaging")
public class AmqpProperties implements InitializingBean {

    public AmqpProperties() {}

    @Override
    public void afterPropertiesSet() throws Exception {}
}