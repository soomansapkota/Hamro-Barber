package com.example.hamro_barber.email;

import com.example.hamro_barber.model.User;

public interface EmailSender {
    void sendEmail(User user);
    void sendForgotPasswordEmail(User user);
    void send(String to, String email);
}
