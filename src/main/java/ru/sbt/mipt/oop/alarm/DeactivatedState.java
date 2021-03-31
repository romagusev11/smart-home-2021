package ru.sbt.mipt.oop.alarm;

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
        alarm.changeState(new OnAlertState(alarm));
    }

    @Override
    public void react(AlarmReactor reactor) {
        reactor.onAlarmDeactivatedState();
    }
}
