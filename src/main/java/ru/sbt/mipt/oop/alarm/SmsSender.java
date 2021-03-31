package ru.sbt.mipt.oop.alarm;

public class SmsSender implements MessageSender {
    @Override
    public void sendMessage(String message) {
        System.out.println("Sending sms...: " + message);
    }
}
