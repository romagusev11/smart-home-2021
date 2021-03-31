package ru.sbt.mipt.oop.ccs;

import com.coolcompany.smarthome.events.CCSensorEvent;
import ru.sbt.mipt.oop.events.sensors.SensorEvent;
import ru.sbt.mipt.oop.events.sensors.SensorEventType;

public class SensorEventFactory implements EventFactory {
    private final SensorEventType eventType;

    public SensorEventFactory(SensorEventType eventType) {
        this.eventType = eventType;
    }

    public SensorEvent convert(CCSensorEvent ccSensorEvent) {
        return new SensorEvent(eventType, ccSensorEvent.getObjectId());
    }
}
