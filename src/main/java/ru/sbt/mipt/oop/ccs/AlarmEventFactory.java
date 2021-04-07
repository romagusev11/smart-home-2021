package ru.sbt.mipt.oop.ccs;

import com.coolcompany.smarthome.events.CCSensorEvent;
import ru.sbt.mipt.oop.events.alarm.AlarmEvent;
import ru.sbt.mipt.oop.events.alarm.AlarmEventType;

public class AlarmEventFactory implements EventFactory {
    private final AlarmEventType eventType;
    private final String code;

    public AlarmEventFactory(AlarmEventType eventType, String code) {
        this.code= code;
        this.eventType = eventType;
    }

    public AlarmEvent convert(CCSensorEvent ccSensorEvent) {
        return new AlarmEvent(eventType, code);
    }
}
