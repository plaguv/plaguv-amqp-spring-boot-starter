package io.github.plaguv.core.utlity.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "amqp.declaration")
public record AmqpDeclarationProperties(
        Boolean declareDirectBinding,
        Boolean declareGroupBinding,
        Boolean declareBroadcastBinding,

        Boolean declareExchangeDurable,
        Boolean declareExchangeDeletable,

        Boolean declareQueueDurable,
        Boolean declareQueueExclusive,
        Boolean declareQueueDeletable
) {
    public AmqpDeclarationProperties {
        declareDirectBinding = declareDirectBinding != null
                ? declareDirectBinding
                : true;
        declareGroupBinding = declareGroupBinding != null
                ? declareGroupBinding
                : true;
        declareBroadcastBinding = declareBroadcastBinding != null
                ? declareBroadcastBinding
                : true;

        declareExchangeDurable = declareExchangeDurable != null
                ? declareExchangeDurable
                : true;
        declareExchangeDeletable = declareExchangeDeletable != null
                ? declareExchangeDeletable
                : false;

        declareQueueDurable = declareQueueDurable != null
                ? declareQueueDurable
                : true;
        declareQueueExclusive = declareQueueExclusive != null
                ? declareQueueExclusive
                : false;
        declareQueueDeletable = declareQueueDeletable != null
                ? declareQueueDeletable
                : false;
    }
}