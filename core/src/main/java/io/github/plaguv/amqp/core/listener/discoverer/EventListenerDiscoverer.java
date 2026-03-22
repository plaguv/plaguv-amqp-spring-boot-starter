package io.github.plaguv.amqp.core.listener.discoverer;

import io.github.plaguv.amqp.core.utlity.helper.Listener;

import java.util.List;

public interface EventListenerDiscoverer {
    List<Listener> getListeners();
}