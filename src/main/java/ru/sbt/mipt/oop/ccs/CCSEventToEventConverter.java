package ru.sbt.mipt.oop.ccs;

import com.coolcompany.smarthome.events.CCSensorEvent;
import ru.sbt.mipt.oop.events.Event;

import java.util.HashMap;
import java.util.Map;

public class CCSEventToEventConverter {
    private final Map<String, EventFactory> ccsEventFactoryMapping = new HashMap<>();

    public void addConverter(String cssSensorEventType, EventFactory eventFactory) {
        ccsEventFactoryMapping.put(cssSensorEventType, eventFactory);
    }

    public Event convert(CCSensorEvent ccSensorEvent) throws ClassCastException {
        try {
            return ccsEventFactoryMapping.get(ccSensorEvent.getEventType()).convert(ccSensorEvent);
        } catch (Exception e) {
            throw new ClassCastException("Cant convert ccSensorEvent to normal Event");
        }
    }
}
