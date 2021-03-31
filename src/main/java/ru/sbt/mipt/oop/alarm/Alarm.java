package ru.sbt.mipt.oop.alarm;

import java.util.Objects;

public class Alarm {
    private AlarmState state;
    private String code;
    private final MessageSender sender;

    public Alarm(MessageSender sender) {
        this.state = new DeactivatedState(this);
        this.sender = sender;
    }

    public void activate(String code) {
        state.activate(code);
    }

    public void deactivate(String code) {
        state.deactivate(code);
    }

    public void setOnAlert() {
        state.setOnAlert();
    }

    void changeState(AlarmState state) {
        this.state = state;
    }

    public boolean isCorrectCode(String code) {
        return Objects.equals(code, this.code);
    }

    void setCode(String code) {
        this.code = code;
    }

    public void react(AlarmReactor reactor) {
        state.react(reactor);
    }

    public void sendMessage(String message) {
        sender.sendMessage(message);
    }

    public boolean isActivated() {
        return this.state instanceof ActivatedState;
    }

    public boolean isDeactivated() {
        return this.state instanceof DeactivatedState;
    }

    public boolean isOnAlert() {
        return this.state instanceof OnAlertState;
    }
}
