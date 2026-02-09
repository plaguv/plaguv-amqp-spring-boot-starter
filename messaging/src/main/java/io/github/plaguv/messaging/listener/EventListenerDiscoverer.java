package io.github.plaguv.messaging.listener;

import jakarta.annotation.Nonnull;

import java.lang.reflect.Method;
import java.util.Map;

public interface EventListenerDiscoverer {
    @Nonnull Map<Method, Class<?>> getListeners();
}