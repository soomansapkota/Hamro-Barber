package com.example.hamro_barber.socket;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SocketService {
    private final SocketTopics socketTopics;

    public void test() {
        socketTopics.hello();
    }
}
