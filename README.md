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