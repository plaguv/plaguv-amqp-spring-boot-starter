![ApacheMaven](https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/Rabbitmq-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)

## 📦 Messaging AMQP Spring Boot Starter

This project provides a standardized, opinionated messaging template built on RabbitMQ for internal event-driven
communication.

Its primary goal is to eliminate repetitive AMQP setup while enforcing domain consistency, type safety, and naming
conventions across services.

The project is designed to be plug-and-play for most use cases, while remaining extensible for advanced scenarios.

## 🧩 Modules

#### 1. `contract`

The `contract` module defines the canonical messaging contracts shared across all services. It provides:

- `EventEnvelope` : standardized message container
- `EventMetadata` : technical metadata (ID, timestamp, version, producer)
- `EventRouting` : routing intent (event type, dispatch type)
- `EventDomain` : logical domain separation
- `EventDispatchType` : delivery semantics (`DIRECT`, `FANOUT`, `TOPIC`)
- Strongly typed event payloads (`EventInstance` implementations)

This module is **transport-agnostic** and contains no AMQP-specific logic.

#### 2. `messaging`

The `messaging` module provides the **RabbitMQ** implementation for both publishing and consuming events. It includes:

- 📤 **Publisher infrastructure**
    - `AmqpEventPublisher`
    - Mandatory publishing with return callbacks

- 📥 **Listener infrastructure**
    - Annotation-based listeners (`@AmqpListener`)
    - Automatic listener registration

- 🧭 **Routing & topology**
    - Convention-based exchange, queue, and routing-key resolution
    - **Just-in-time (JIT)** declaration of queues, exchanges, and bindings

## ⚙️ Configuration

At minimum, each application defines its logical exchange namespace:

````yaml
amqp:
  central_application: application # or ${spring.application.name}
````

This namespace is used to derive exchanges, queues, and bindings consistently across services.

## 📦 Installation

Simply add the dependency using your preferred build.

Maven:
````maven
<dependencies>
    <dependency>
        <groupId>io.github.plaguv</groupId>
        <artifactId>messaging-amqp-starter</artifactId>
        <version>1.0.0-alpha-1</version>
    </dependency>
</dependencies>
````

Gradle:
````gradle
dependencies {
    implementation 'io.github.plaguv:messaging-amqp-starter:1.0.0-alpha-1'
}
````

## 🚀 Examples

To demonstrate the ease of use, here are some common use cases integrated into an everyday java project:

### ✉️ Defining an event
An event recognized by the starter is easy to setup. Simply create a class and append the `@Event` annotation.


The Annotation generally only requires the events domain. The events version (if ommited) will start at `1.0.0`. 
The versioning can also be written as '1', '1.0', '1.0.0', '1_0_0', '1-0-0' or '1;0;0''

Everything else can be freely stylized, such as validation. Do make sure that your class is compatible with `jackson-databind` 3+ serialization.
Otherwise, the message cannot be sent.

`````java
@Event(domain = EventDomain.STORE, version = "1.0.0") // Mandatory annotation 
public record StoreOpenedEvent(
        long storeId // Events Attributes
) {

    public StoreOpenedEvent {
        if (storeId < 1) { // Optional validation
            throw new IllegalArgumentException("StoreOpenedEvents attribute 'storeId' cannot be < 1");
        }
    }
}
`````

### 📤 Publishing an event

The starter automatically infers:

- the correct exchange

- the routing key

- message headers and encoding

It also handles serialization and error logging. 
At minimum, each EventEnvelope builder requires a producer and a payload. 
Payloads can be any object that have the `@Event` annotation.

````java
// eventPublisher is a spring-managed bean that is automatically set by the autoconfiguration. Import via constructor injection or @Autowiring
EventPublisher eventPublisher;

StoreOpenedEvent storeOpenedEvent = new StoreOpenedEvent(5L);
EventEnvelope envelope = EventEnvelope.builder()
        .ofPayload(storeOpenedEvent)
        .build();

eventPublisher.publishMessage(envelope);
````

### 📤 Listening to an event

All required AMQP topology is derived and declared automatically.
Do note that the listener infers your listening intent from the methods' parameter. 
In this case `StoreOpenedEvent`. The parameter has to implement the aforementioned `@Event` annotation.

````java

@AmqpListener
public void onStoreOpened(StoreOpenedEvent event) {
    // handle event
}
````