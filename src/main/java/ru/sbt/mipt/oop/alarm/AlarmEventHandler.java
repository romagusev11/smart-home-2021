package ru.sbt.mipt.oop.alarm;

public class AlarmEventHandler {
    private final Alarm alarm;

    public AlarmEventHandler(Alarm alarm) {
        this.alarm = alarm;
    }

    public void handleEvent(AlarmEvent event) {
        if (event.getType() == AlarmEventType.ALARM_ACTIVATE) {
            alarm.activate(event.getCode());
        } else if (event.getType() == AlarmEventType.ALARM_DEACTIVATE) {
            alarm.deactivate(event.getCode());
        }
    }
}
