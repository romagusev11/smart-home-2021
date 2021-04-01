package ru.sbt.mipt.oop.alarm;

class OnAlertState implements AlarmState {
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

    @Override
    public void react(AlarmReactor reactor) {
        reactor.onAlarmOnAlertState();
    }
}
