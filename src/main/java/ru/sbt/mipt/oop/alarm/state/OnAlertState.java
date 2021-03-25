package ru.sbt.mipt.oop.alarm.state;

import ru.sbt.mipt.oop.alarm.Alarm;

public class OnAlertState implements AlarmState {
    private final Alarm alarm;

    public OnAlertState(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public void activate(String code) {
        // Do nothing
    }

    @Override
    public void deactivate(String code) {
        if (alarm.isCorrectCode(code)) {
            alarm.changeState(new DeactivatedState(alarm));
        }
    }

    @Override
    public void setOnAlert() {
        // Do nothing
    }
}
