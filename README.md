# Docker OAuth Proxy
(for lack of a better name)

The main goal of this repository is to exercise the use of the Traefik reverse proxy to verify a Bearer token and to convert the username claim to a custom header that is forwarded to the resource server.

The bonuses are to exercise the Keycloak automated configuration and Spring OAuth2 Client library

A docker-compose enabling the deployment of several containers:
- Resource server (rs): A Springboot application that responds to requests from clients. No OAuth security mechanisms are put in place
- Client: A Springboot Web Application that aims to interact with the resource server
- Authorization Server (as): A Keycloak instance enabling the OAuth2 flow
- Proxy: A Traefik reverse proxy dealing with routing requests/responses. It relies on the third-party plugin to  

Under the hood, the `client` is obtaining the access token from keycloak and including it in the authorized request to the `proxy` that then validates the token, takes the `preferred_username` claim value and puts it in the `Shelf-User` custom HTTP header value that is added to the request to the `rs`. The `rs` responds with the books for the configured user.

# Requirements

To build the [client](./client) and [resource-server](./resource-server) artifacts:

* JDK17
* Apache Maven 3.9.4

To run the containers:
* Docker engine
* docker-compose

# Disclaimer

On the first try, starting up all the containers with `docker-compose up -d` will not bring good results. The keycloak instance health check is [not easy to accomplish](https://www.keycloak.org/server/health#_using_the_health_checks):

> It is recommended that the health endpoints be monitored by external HTTP requests

it is not possible to leverage the docker-compose `depends_on` to have the `proxy` and `client` containers wait until the `as` container is healthy.

# How to run

## (only first time) Build the client and resource server artifacts

> mvn clean package -f ./client/pom.xml

> mvn clean package -f ./resource_server/pom.xml

## Start the services

> docker-compose up -d

## Have fun

In your browser, go to:

> localhost:8080

The user credentials are:
username: `sherlock`
password: `holmes`