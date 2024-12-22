package org.cognitivefinder.tracking.hello;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    private final HelloWebSocketHandler helloWebSocketHandler;

    public HelloService(HelloWebSocketHandler helloWebSocketHandler) {
        this.helloWebSocketHandler = helloWebSocketHandler;
    }

    @Scheduled(fixedRate = 10000)
    public void sendHelloToClient() {
        Integer i = 0;
        helloWebSocketHandler.sendHelloToClient("Hello from the server! " + i);
        i++;
    }
}
