package io.github.plaguv.amqp.core.utility;

import io.github.plaguv.amqp.api.envelope.EventEnvelope;
import io.github.plaguv.amqp.api.envelope.EventEnvelopeBuilder;
import io.github.plaguv.amqp.api.envelope.payload.EventPayload;
import io.github.plaguv.amqp.api.envelope.routing.EventScope;
import io.github.plaguv.amqp.api.event.pos.ArticleOrderEvent;
import io.github.plaguv.amqp.core.utlity.properties.AmqpProperties;
import io.github.plaguv.amqp.core.utlity.router.AmqpEventRouter;
import io.github.plaguv.amqp.core.utlity.router.EventRouter;
import org.junit.jupiter.api.*;

import java.util.Set;

class AmqpEventRouterTest {

    private EventRouter eventRouter;

    private ArticleOrderEvent articleOrderEvent;
    private EventEnvelope eventEnvelope;

    @BeforeEach
    void beforeEach() {
        eventRouter = new AmqpEventRouter(
                new AmqpProperties() {
                    @Override
                    public String centralExchange() {
                        return "central";
                    }

                    @Override
                    public String centralApplication() {
                        return "starter";
                    }
                }
        );

        articleOrderEvent = new ArticleOrderEvent(1, 1, 1);

        eventEnvelope = EventEnvelopeBuilder.defaults()
                .ofEventPayload(EventPayload.valueOf(articleOrderEvent))
                .build();
    }

    @Test
    @DisplayName("Should resolve the queues name based on the event envelope")
    void resolveQueue() {
        Assertions.assertEquals(
                "starter.store.article_order_event.queue",
                eventRouter.resolveQueue(eventEnvelope)
        );
    }

    @Test
    @DisplayName("Should resolve the exchanges name based on the event envelope")
    void resolveExchange() {
        Assertions.assertEquals(
                "central.events",
                eventRouter.resolveExchange(eventEnvelope)
        );
    }

    @Test
    @DisplayName("Should resolve the routing key based on the event envelope")
    void resolveRoutingKeyWith() {
        eventEnvelope = EventEnvelopeBuilder.defaults()
                .ofEventPayload(EventPayload.valueOf(articleOrderEvent))
                .build();
        Assertions.assertEquals(
                "store.article_order_event",
                eventRouter.resolveRoutingKey(eventEnvelope)
        );

        eventEnvelope = EventEnvelopeBuilder.defaults()
                .withScope(EventScope.GROUP)
                .withWildcard("cashier")
                .ofEventPayload(EventPayload.valueOf(articleOrderEvent))
                .build();
        Assertions.assertEquals(
                "store.article_order_event.group.cashier",
                eventRouter.resolveRoutingKey(eventEnvelope)
        );

        eventEnvelope = EventEnvelopeBuilder.defaults()
                .withScope(EventScope.TARGET)
                .withWildcard("cashier")
                .ofEventPayload(EventPayload.valueOf(articleOrderEvent))
                .build();
        Assertions.assertEquals(
                "store.article_order_event.target.cashier",
                eventRouter.resolveRoutingKey(eventEnvelope)
        );
    }

    @Test
    @DisplayName("Should resolve the binding keys based on the event envelope")
    void resolveBindingKey() {
        eventEnvelope = EventEnvelopeBuilder.defaults()
                .withWildcard("starter")
                .ofEventPayload(EventPayload.valueOf(articleOrderEvent))
                .build();

        Set<String> expectedBindings = Set.of(
                "store.article_order_event",                 // Scope: Broadcast
                "store.article_order_event.group.starter",   // Scope: Group
                "store.article_order_event.target.starter"   // Scope: Target
        );
        Set<String> actualBindings = eventRouter.resolveBindingKey(eventEnvelope);

        Assertions.assertEquals(expectedBindings.size(), actualBindings.size());
        Assertions.assertArrayEquals(
                expectedBindings.stream().sorted().toArray(),
                actualBindings.stream().sorted().toArray()
        );
    }
}