package io.github.plaguv.amqp.starter.autoconfigure;

import io.github.plaguv.amqp.core.utlity.properties.AmqpDeclarationProperties;
import io.github.plaguv.amqp.core.listener.discoverer.AmqpEventListenerDiscoverer;
import io.github.plaguv.amqp.core.listener.discoverer.EventListenerDiscoverer;
import io.github.plaguv.amqp.core.listener.registrar.AmqpEventListenerRegistrar;
import io.github.plaguv.amqp.core.listener.registrar.EventListenerRegistrar;
import io.github.plaguv.amqp.core.listener.topology.AmqpEventListenerTopology;
import io.github.plaguv.amqp.core.listener.topology.EventListenerTopology;
import io.github.plaguv.amqp.core.utlity.properties.AmqpProperties;
import io.github.plaguv.amqp.core.utlity.router.AmqpEventRouter;
import io.github.plaguv.amqp.core.utlity.router.EventRouter;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@AutoConfiguration(after = AmqpAutoConfiguration.class)
public class AmqpEventListenerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public EventRouter eventRouter(AmqpProperties amqpProperties) {
        return new AmqpEventRouter(amqpProperties);
    }

    @Bean
    @ConditionalOnMissingBean(EventListenerDiscoverer.class)
    public EventListenerDiscoverer eventListenerDiscoverer(ListableBeanFactory listableBeanFactory) {
        return new AmqpEventListenerDiscoverer(listableBeanFactory);
    }

    @Bean
    @ConditionalOnMissingBean(EventListenerRegistrar.class)
    public EventListenerRegistrar eventListenerRegistrar(
            @Qualifier("amqpEventListenerContainerFactory") SimpleRabbitListenerContainerFactory containerFactory,
            @Qualifier("amqpMessageHandlerMethodFactory") MessageHandlerMethodFactory methodFactory,
            EventListenerDiscoverer discoverer,
            EventRouter router
    ) {
        return new AmqpEventListenerRegistrar(containerFactory, methodFactory, discoverer, router);
    }

    @Bean
    @ConditionalOnMissingBean(EventListenerTopology.class)
    public EventListenerTopology topologyDeclarer(AmqpDeclarationProperties declarationProperties, EventRouter eventRouter) {
        return new AmqpEventListenerTopology(declarationProperties, eventRouter);
    }

    @Bean
    public Declarables amqpDeclarables(EventListenerDiscoverer discoverer, EventListenerTopology eventListenerTopology) {
        return eventListenerTopology.getDeclarablesFromListeners(discoverer.getListeners());
    }
}