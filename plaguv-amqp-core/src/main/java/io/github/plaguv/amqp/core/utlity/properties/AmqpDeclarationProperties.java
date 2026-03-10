package io.github.plaguv.amqp.core.utlity.properties;

public interface AmqpDeclarationProperties {
    Boolean declareDirectBinding();

    Boolean declareGroupBinding();

    Boolean declareBroadcastBinding();

    Boolean declareExchangeDurable();

    Boolean declareExchangeDeletable();

    Boolean declareQueueDurable();

    Boolean declareQueueExclusive();

    Boolean declareQueueDeletable();
}