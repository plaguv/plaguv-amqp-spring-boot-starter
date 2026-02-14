# Listening Documentation

This document provides a complete technical reference for defining a custom event component using the ``@Event``
annotation.

## Creating an Event

To define an event, annotate a class with ``@Event`` and specify the required metadata attributes.

| Name    | Type            | Description                                                                     | Validation                                        |
|---------|-----------------|---------------------------------------------------------------------------------|---------------------------------------------------|
| domain  | ``EventDomain`` | Specifies the event's domain                                                    | ``@NotNull``                                      |
| version | String          | Specifies the event's version. Defaults to ``1.0.0`` if not explicitly provided | ``@NotNull`` and must be a valid ``EventVersion`` |

A version string is considered valid if it follows one of these formats:

- ``1.0.0``
- ``1_0_0``
- ``1-0-0``
- ``1;0;0``
- ``1.0``
- ``1``

## Example

A valid ``@Event``-annotated class is shown below:

````java

@Event(domain = EventDomain.STORE, version = "1.0.0")
public record StoreOpenedEvent(
        long storeId
) {
    public StoreOpenedEvent {
        if (storeId < 1) {
            throw new IllegalArgumentException(
                    "StoreOpenedEvent attribute 'storeId' must be greater than 0"
            );
        }
    }
}
````

Java records are preferred, as they provide immutability guarantees for their fields and allow concise runtime
validation within the canonical constructor.

## Versioning Guidelines

It is considered good practice to increment the event version whenever:

- A field is added
- A field is removed
- A field’s type changes
- The semantic meaning of the event changes

Maintaining explicit versioning ensures backward compatibility and supports safe event evolution across distributed
systems.