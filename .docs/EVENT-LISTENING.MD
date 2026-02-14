# Listening Documentation

This document provides a complete technical reference for using the custom ``@AmqpListener`` annotation on application methods.

## Listening to an Event

To listen to an event, annotate a method with ``@AmqpListener``.
The method must be discoverable by Spring; therefore, the enclosing class must be a Spring-managed component (e.g., annotated with ``@Component``).

The following rules apply when annotating a method:

- The method must declare exactly one parameter
- The parameter type must be annotated with ``@Event``
- Method overloading is currently not supported. Therefore, method names must be unique within the same component if you intend to handle multiple event types.

A valid example is shown below:

````java
@Component
public class SomeComponent {

    public SomeComponent() {}

    @AmqpListener
    public void onStoreOpenedEvent(StoreOpenedEvent event) {
        // handle event
    }
}
````
> Note that the method parameter may be ``null``. This depends on the received message, as empty payloads are permitted.

## Configuring the Listener

Listener configuration is not currently supported. Additional configuration options may be introduced in future releases if required.