package io.github.plaguv.amqp.core.publisher;

import io.github.plaguv.amqp.api.envelope.EventEnvelope;
import io.github.plaguv.amqp.core.utlity.router.EventRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class AmqpEventPublisher implements EventPublisher {

    private static final Logger log = LoggerFactory.getLogger(AmqpEventPublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final EventRouter eventRouter;

    public AmqpEventPublisher(RabbitTemplate rabbitTemplate, EventRouter eventRouter) {
        this.rabbitTemplate = rabbitTemplate;
        this.eventRouter = eventRouter;
    }

    @Override
    public void publishMessage(EventEnvelope eventEnvelope) {
        try {
            String exchange = eventRouter.resolveExchange(eventEnvelope);
            String routingKey = eventRouter.resolveRoutingKey(eventEnvelope);

            rabbitTemplate.convertAndSend(
                    exchange,
                    routingKey,
                    eventEnvelope
            );
            log.atInfo().log("Successfully send Message");
        } catch (Exception e) {
            log.atError().log("Failed to send Message", e);
        }
    }
}