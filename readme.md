# REST API limit-order-svc

This is a service that provides a REST API to place
limit orders to buy bitcoins.

The entire application is written in java 8, that runs on vertx server.

Also there is a docker file that creates a container that can be deployed
on kubernetes.
Deployment and Service configs are provided.

Steps to create a docker container
This is a maven built application.
1. `mvn clean install` generates a jar file.
2. `docker build -f config/docker/Dockerfile -t limit-order-svc` creates
a docker container.
3. `minikube image load limit-order-svc` loads the image from local
docker repositoty to minikube docker repo.
4. `kubectl apply -f config/kubernetes/deploymentConfig.yaml`
Deploys a config and tries to deploy the container.
5. `kubectl apply -f limit-order-svc/config/kubernetes/service.yaml`
Deploy a config and tries to expose a service that can be accessed
   from outsite the cluster.
6. `minikube service limit-order-svc` open the web browser in a
in a browser tab.


# REST API

The REST API to the limit-order-svc is described below.

## Get list of Things

### Request

`GET /health`

    curl --location --request GET 'http://127.0.0.1:56996/health' --header 'x-user-id: sandeep'

### Response

    HTTP/1.1 200 OK
    Status: 200 OK
    Connection: close
    Content-Type: application/json
    Content-Length: 85

    {"status":"UP","checks":[{"id":"health-check-handler","status":"UP"}],"outcome":"UP"}

## Create a new limit order

### Request

`POST /orders/limit`

    curl --location --request POST 'http://127.0.0.1:56996/orders/limit' \
    --header 'x-user-id: sandeep' \
    --header 'Content-Type: application/json' \
    --data-raw '{
    "side": "SELL",
    "quantity": "0.100000",
    "price": "10000",
    "pair": "BTCZAR",
    "postOnly": true,
    "customerOrderId": "1234",
    "timeInForce": "GTC"
    }'

### Response

    HTTP/1.1 201 Created
    Status: 201 Created
    Connection: close
    Content-Type: application/json
    Content-Length: 173

    {"id": "f5a02763-7ebe-4c79-81ca-4b8a1cb004e9"}

## Get order book

### Request

`GET /:currPair/orderbook`

Where *currPair* can be any currency pair i.g. BTCZAR

    curl --location --request GET 'http://127.0.0.1:8080/BTCZAR/orderbook' \
    --header 'x-user-id: sandeep'

### Response

    HTTP/1.1 200 OK
    Status: 200 OK
    Connection: close
    Content-Type: application/json
    Content-Length: 204

    [{"side":"SELL","quantity":"0.100000","price":"10000","pair":"BTCZAR","postOnly":true,"customerOrderId":"1234","timeInForce":"GTC"}]

## Get a trade history

### Request

`GET /BTCZAR/tradehistory`

Where *currPair* can be any currency pair i.g. BTCZAR

    curl --location --request POST 'http://127.0.0.1:8080/BTCZAR/tradehistory' \
    --header 'x-user-id: sandeep'

### Response

    HTTP/1.1 200
    Status: 200
    Connection: close
    Content-Type: application/json
    Content-Length: 204

    [{"side":"SELL","quantity":"0.100000","price":"10000","pair":"BTCZAR","postOnly":true,"customerOrderId":"1234","timeInForce":"GTC"}]

## Get missing trade history

### Request

`GET /BTCZAR/tradehistory`

Where *currPair* can be any currency pair i.g. BTCZAR

    curl --location --request POST 'http://127.0.0.1:8080/BTCZAR/tradehistory' \
    --header 'x-user-id: sandeep'

### Response

    HTTP/1.1 404 Not Found
    Status: 404 Not Found
    Connection: close
    Content-Type: application/json
    Content-Length: 114

    No trade history available for user: sandeep1



