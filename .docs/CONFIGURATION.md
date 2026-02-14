# Configuration Documentation

## ``amqp``

Class: ``io.github.plaguv.core.properties.AmqpProperties``

| Key                          | Type       | Description                                                                  | Default value | Deprecation |
|------------------------------|------------|------------------------------------------------------------------------------|---------------|-------------|
| ``amqp.central-exchange``    | ``String`` | Describes the central exchange name, which will be used in routing naming    | N/A           | N/A         |
| ``amqp.central-application`` | ``String`` | Describes the central application name, which will be used in routing naming | N/A           | N/A         |

## ``amqp.declaration``

Class: ``io.github.plaguv.core.properties.AmqpDeclarationProperties``

| Key                                             | Type        | Description                                   | Default value | Deprecation |
|-------------------------------------------------|-------------|-----------------------------------------------|---------------|-------------|
| ``amqp.declaration.declare-direct-queue``       | ``Boolean`` | Automatically declares a direct queue         | ``true``      | N/A         |
| ``amqp.declaration.declare-group-queue``        | ``Boolean`` | Automatically declares a group queue          | ``true``      | N/A         |
| ``amqp.declaration.declare-broadcast-queue``    | ``Boolean`` | Automatically declares a broadcast queue      | ``true``      | N/A         |
| ``amqp.declaration.declare-exchange-durable``   | ``Boolean`` | Automatically declares exchanges as durable   | ``true``      | N/A         |
| ``amqp.declaration.declare-exchange-deletable`` | ``Boolean`` | Automatically declares exchanges as deletable | ``false``     | N/A         |
| ``amqp.declaration.declare-queue-durable``      | ``Boolean`` | Automatically declares queues as durable      | ``true``      | N/A         |
| ``amqp.declaration.declare-queue-exclusive``    | ``Boolean`` | Automatically declares queues as exclusive    | ``false``     | N/A         |
| ``amqp.declaration.declare-queue-deletable``    | ``Boolean`` | Automatically declares queues as deletable    | ``false``     | N/A         |

## ``amqp.skip``

Class: ``io.github.plaguv.core.properties.AmqpStartupProperties``

| Key                              | Type        | Description                                                          | Default value | Deprecation |
|----------------------------------|-------------|----------------------------------------------------------------------|---------------|-------------|
| ``amqp.skip.register-listeners`` | ``Boolean`` | Decides if the automatic registration of listeners should take place | ``false``     | N/A         |