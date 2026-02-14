# Publishing Documentation

This document provides a complete technical reference for constructing and using the custom ``EventEnvelope`` and its
``EventEnvelopeBuilder``,
as well as publishing it to the messaging broker via the ``EventPublisher``.

## Building an EventEnvelope

To publish an event, you must first create an ``EventEnvelope``. An ``EventEnvelope`` represents the content, intent,
and additional metadata of an event.

In the following sections, we will briefly review its structure and explain how to create an instance that satisfies the
requirements of the ``EventPublisher`` component.

## Contents of an EventEnvelope

An ``EventEnvelope`` is composed of the following elements:

| Name     | Type              | Description                                                                                       | Validation   |
|----------|-------------------|---------------------------------------------------------------------------------------------------|--------------|
| metadata | ``EventMetadata`` | Contains all information exposed in the message headers. Primarily used for debugging and tracing | ``@NotNull`` |
| routing  | ``EventRouting``  | Defines the event’s intent and determines how it is routed by the ``EventPublisher``              | ``@NotNull`` |
| payload  | ``EventPayload``  | The event’s content. A payload may be empty or contain structured data.                           | ``@NotNull`` |

For a more detailed overview of each section and how the builder manages them, see below.

## EventMetadata

| Name       | Type         | Description                                                                                                                                                                      | Validation   |
|------------|--------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| eventId    | ``UUID``     | The unique identifier of the message                                                                                                                                             | ``@NotNull`` |
| occurredAt | ``Instant``  | The timestamp representing when the event occurred                                                                                                                               | ``@NotNull`` |
| producer   | ``Class<?>`` | The originating producer of the message. This field is optional and defaults to ``null``, but specifying the producing service or class is recommended for improved traceability | None         |

## EventRouting

| Name     | Type           | Description                                                                                                                                             | Validation                                              |
|----------|----------------|---------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------|
| scope    | ``EventScope`` | Defines the scope to which the message will be published                                                                                                | ``@NotNull``                                            |
| wildcard | ``String``     | Specifies the routing wildcard used when the scope is not ``BROADCAST``. This should typically reference the target application's configured identifier | ``@NotNull`` and ``@NotBlank`` if scope ≠ ``BROADCAST`` |

## EventPayload

| Name        | Type         | Description                                                             | Validation                                                                                     |
|-------------|--------------|-------------------------------------------------------------------------|------------------------------------------------------------------------------------------------|
| contentType | ``Class<?>`` | Defines the payload’s content type                                      | ``@NotNull``, must be annotated with ``@Event``, and must match the type of content if present |
| content     | ``Object``   | The payload content. May be ``null`` if a ``contentType`` is specified. | If present, must be annotated with ``@Event`` and must match ``contentType``                   |

## Builders for an EventEnvelope

There are two ways to instantiate an ``EventEnvelope``:

- Create an instance directly
- Use the custom ``EventEnvelopeBuilder``

For demonstration purposes, the following example creates an ``EventEnvelope`` with an empty payload using ``EventEnvelopeBuilder``:

````java
 EventEnvelope envelope = EventEnvelopeBuilder.defaults()
        .withContentType(StoreOpenedEvent.class)
        .build();
````

As shown above, an ``EventEnvelope`` with an empty payload can be created with minimal configuration and used to notify listeners.

In general, you can choose between:
- ``.defaults()``
- ``.empty()``

The entry method determines whether default values are preconfigured.

## Default values provided by .defaults()

- ``eventId = UUID.randomUUID()``;
- ``occurredAt = Instant.now()``;
- ``scope = EventScope.BROADCAST``;

These defaults are sufficient to send an empty or non-empty payload to all listeners using the ``BROADCAST`` scope.

If additional customization is required, you can chain the necessary builder methods to construct the desired ``EventEnvelope``.

The following snippet demonstrates the available builder methods:

````java
public static void someMethod() {
    EventEnvelope envelope = EventEnvelopeBuilder.empty()
            // EventMetadata related fields and objects
            .ofEventMetadata()
            .withEventId()
            .withOccurredAt()
            .withProducer()
            // EventRouting related fields and objects
            .ofEventRouting()
            .withScope()
            .withWildcard()
            // EventPayload related fields and objects
            .ofEventPayload()
            .withContentType()
            .withContent()
            .build();
}
````

For clarity:
- The prefix ``of`` is used to provide a complete metaobject instance (metadata, routing, or payload).
- The prefix ``with`` is used to configure individual attributes.

Although verbose, this builder chain compiles (when valid values are provided) and produces a usable ``EventEnvelope`` that can be passed to the ``EventPublisher``.

## Publishing an EventEnvelope

Publishing an ``EventEnvelope`` is straightforward.

Once you have created an ``EventEnvelope``, inject the ``EventPublisher`` bean into your class (assuming you are using the starter). After injection, call its ``publishMessage()`` method with the constructed envelope.

````java
public class SomeService {

    // Constructor injection is recommended
    private final EventPublisher eventPublisher;

    public SomeService(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void someMethod() {
        EventEnvelope envelope = EventEnvelopeBuilder.defaults()
                .withContentType(StoreOpenedEvent.class)
                .build();

        eventPublisher.publishMessage(envelope);
    }
}
````

Once invoked, the message will be routed according to the defined ``EventRouting`` configuration, and all matching listeners or queues will receive the event accordingly.