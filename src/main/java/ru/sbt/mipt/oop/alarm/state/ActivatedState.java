package ru.sbt.mipt.oop.alarm.state;

import ru.sbt.mipt.oop.alarm.Alarm;

public class ActivatedState implements AlarmState {
    private final Alarm alarm;

    public ActivatedState(Alarm alarm) {
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
        } else {
            alarm.changeState(new OnAlertState(alarm));
        }
    }

    @Override
    public void setOnAlert() {
        alarm.changeState(new OnAlertState(alarm));
    }
}
