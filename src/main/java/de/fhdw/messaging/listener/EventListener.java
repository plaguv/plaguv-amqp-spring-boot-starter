package de.fhdw.messaging.listener;

public interface EventListener {
    void handleMessage(Object message);
}