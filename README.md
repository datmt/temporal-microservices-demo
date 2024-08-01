# Introduction

* This is a demo of using Temporal to orchestrate a workflow with spring boot
* This demo *just* work, I don't guarantee this is the correct way to implement a workflow with Temporal

# How to run

1. You need to setup temporal first, ideally using docker-compose.
   The file is available here:

```
 rev: c66f523
https://github.com/temporalio/docker-compose/blob/main/docker-compose.yml
```
or you can copy this content to get the right docker compose content at the time I recorded the video:
```
version: "3.5"
services:
  elasticsearch:
    container_name: temporal-elasticsearch
    environment:
      - cluster.routing.allocation.disk.threshold_enabled=true
      - cluster.routing.allocation.disk.watermark.low=512mb
      - cluster.routing.allocation.disk.watermark.high=256mb
      - cluster.routing.allocation.disk.watermark.flood_stage=128mb
      - discovery.type=single-node
      - ES_JAVA_OPTS=-Xms256m -Xmx256m
      - xpack.security.enabled=false
    image: elasticsearch:${ELASTICSEARCH_VERSION}
    networks:
      - temporal-network
    expose:
      - 9200
    volumes:
      - /var/lib/elasticsearch/data
  postgresql:
    container_name: temporal-postgresql
    environment:
      POSTGRES_PASSWORD: temporal
      POSTGRES_USER: temporal
    image: postgres:${POSTGRESQL_VERSION}
    networks:
      - temporal-network
    expose:
      - 5432
    volumes:
      - /var/lib/postgresql/data
  temporal:
    container_name: temporal
    depends_on:
      - postgresql
      - elasticsearch
    environment:
      - DB=postgresql
      - DB_PORT=5432
      - POSTGRES_USER=temporal
      - POSTGRES_PWD=temporal
      - POSTGRES_SEEDS=postgresql
      - DYNAMIC_CONFIG_FILE_PATH=config/dynamicconfig/development-sql.yaml
      - ENABLE_ES=true
      - ES_SEEDS=elasticsearch
      - ES_VERSION=v7
    image: temporalio/auto-setup:${TEMPORAL_VERSION}
    networks:
      - temporal-network
    ports:
      - 7233:7233
    labels:
      kompose.volume.type: configMap
    volumes:
      - ./dynamicconfig:/etc/temporal/config/dynamicconfig
  temporal-admin-tools:
    container_name: temporal-admin-tools
    depends_on:
      - temporal
    environment:
      - TEMPORAL_ADDRESS=temporal:7233
      - TEMPORAL_CLI_ADDRESS=temporal:7233
    image: temporalio/admin-tools:${TEMPORAL_VERSION}
    networks:
      - temporal-network
    stdin_open: true
    tty: true
  temporal-ui:
    container_name: temporal-ui
    depends_on:
      - temporal
    environment:
      - TEMPORAL_ADDRESS=temporal:7233
      - TEMPORAL_CORS_ORIGINS=http://localhost:3000
    image: temporalio/ui:${TEMPORAL_UI_VERSION}
    networks:
      - temporal-network
    ports:
      - 8080:8080
networks:
  temporal-network:
    driver: bridge
    name: temporal-network

```

Setting up by running `docker-compose up` in the same directory as the file.

Make sure you have a postgres database running on port 5432, with the following credentials:
username: postgres
password: postgres

or configure your own database in the docker-compose file.

2. Run the microservices

There are three services

- `order-service`
- `payment-service`
- `shipping-service`

You can run them in any order.

3. Run the workflow
   There is a controller in the order service that you can use to start the workflow.

```shell
curl --location 'http://localhost:7171/orders' \
--header 'Content-Type: application/json' \
--data '{
    "customerId": "Alice",
    "items": {
        "1": "1",
        "2": "2"
    }
}'
```

In the items array, the key is the item id, and the value is the quantity.
Currently, there are two items in the system, with id 1 and 2.
