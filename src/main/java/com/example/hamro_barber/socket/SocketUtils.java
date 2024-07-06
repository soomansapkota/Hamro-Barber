package com.example.hamro_barber.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


@Component
public class SocketUtils {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    public <T> void invokeWebSocketEndpoint(String endpoint, T payload) {
        this.simpMessagingTemplate.convertAndSend(endpoint, payload);
    }
}
