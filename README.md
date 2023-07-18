### Send messages to a NATS server

This example uses [Vaadin](https://vaadin.com) within a [Spring Boot](https://spring.io) application to send 
messages via [NATS](https://nats.io)


```
# Run NATS server
./gradlew runNatsServer

# Subscribe to vaadin.input
docker run --network nats --rm -it natsio/nats-box nats sub -s nats://nats:4222 vaadin.input

# Start application in another terminal
./gradlew bootRun
```

Open in a browser: [here](http://localhost:8080)