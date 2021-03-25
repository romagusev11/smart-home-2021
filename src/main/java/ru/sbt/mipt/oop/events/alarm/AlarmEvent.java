package ru.sbt.mipt.oop.events.alarm;

import ru.sbt.mipt.oop.events.Event;

public class AlarmEvent implements Event {
    private final AlarmEventType type;
    private final String code;

    public AlarmEvent(AlarmEventType type, String code) {
        this.type = type;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public AlarmEventType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "AlarmEvent{" +
                "type=" + type +
                ", code='" + code + '\'' +
                '}';
    }
}
