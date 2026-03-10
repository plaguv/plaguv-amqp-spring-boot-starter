package io.github.plaguv.amqp.core.listener.topology;

import io.github.plaguv.amqp.core.utlity.helper.Listener;
import org.springframework.amqp.core.Declarables;

import java.util.Collection;

public interface EventListenerTopology {
    Declarables getDeclarablesFromListeners(Collection<Listener> listeners);
}