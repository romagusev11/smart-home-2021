package ru.sbt.mipt.oop.events.alarm;

import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.EventHandler;

public class AlarmEventHandler implements EventHandler {
    private final Alarm alarm;

    public AlarmEventHandler(Alarm alarm) {
        this.alarm = alarm;
    }

    public void handleEvent(Event event) {
        if (event instanceof AlarmEvent) {
            AlarmEvent alarmEvent = (AlarmEvent) event;
            if (alarmEvent.getType() == AlarmEventType.ALARM_ACTIVATE) {
                alarm.activate(alarmEvent.getCode());
            } else if (alarmEvent.getType() == AlarmEventType.ALARM_DEACTIVATE) {
                alarm.deactivate(alarmEvent.getCode());
            }
        }
    }
}
