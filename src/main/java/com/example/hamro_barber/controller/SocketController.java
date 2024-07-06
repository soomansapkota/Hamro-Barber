package com.example.hamro_barber.controller;

import com.example.hamro_barber.socket.SocketService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
//@RequestMapping("/socket")
@AllArgsConstructor
public class SocketController {

    private final SocketService service;

    @ResponseBody
    @GetMapping("/socket/push")
    public ResponseEntity<?> sendMessage(String m) throws FirebaseMessagingException {
        // This registration token comes from the client FCM SDKs.
        String registrationToken = "fcQkN9TpQj-VAr2zFtYxXm:APA91bFS-8yH37RgjTzQiVEuU1WrP_2hEK024nO_majseZVQI2C9BaieNwZl02dpW-9aZkKZYHG1Qe_R_RQc2ZAHJGzgyc19UWSTbQTVEpx1KckB5HPZkhz-Z4pvhqB6VsQJ2vqzSSMN";

// See documentation on defining a message payload.
        Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle("Normal Haircut")
                        .setBody("User: Bhoju")
                        .build())
                .setToken(registrationToken)
                .build();

// Send a message to the device corresponding to the provided
// registration token.
        String response = FirebaseMessaging.getInstance().send(message);
// Response is a message ID string.
        System.out.println("Successfully sent message: " + response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/socket/test")
    public void test() {
        service.test();
    }

    @MessageMapping("/receive/{id}")
    @SendTo("/topic/send/4")
    public Map<String, Object> greeting(@DestinationVariable String id, Map<String, Object> message) throws Exception {
//        Thread.sleep(1000); // simulated delay
        System.out.println(message.toString());
        System.out.println("MESSAGE FROM topic/test");
        return message;
    }
}
