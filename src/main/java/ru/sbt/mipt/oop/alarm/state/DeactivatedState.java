package ru.sbt.mipt.oop.alarm.state;

import ru.sbt.mipt.oop.alarm.Alarm;

public class DeactivatedState implements AlarmState {
    private final Alarm alarm;

    public DeactivatedState(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public void activate(String code) {
        alarm.setCode(code);
        alarm.changeState(new ActivatedState(alarm));
    }

    @Override
    public void deactivate(String code) {
        // Do nothing
    }

    @Override
    public void setOnAlert() {
        // Do nothing
    }
}