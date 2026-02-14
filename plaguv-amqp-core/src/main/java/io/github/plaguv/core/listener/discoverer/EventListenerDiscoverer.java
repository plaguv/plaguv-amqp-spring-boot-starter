package io.github.plaguv.core.listener.discoverer;

import io.github.plaguv.core.utlity.helper.Listener;

import java.util.List;

public interface EventListenerDiscoverer {
    List<Listener> getListeners();
}