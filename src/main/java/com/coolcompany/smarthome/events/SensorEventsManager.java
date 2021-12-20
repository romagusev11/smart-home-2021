package com.coolcompany.smarthome.events;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;

public class SensorEventsManager {
    private final String[] eventTypes = new String[] { "LightIsOn", "LightIsOff", "DoorIsOpen", "DoorIsClosed", "DoorIsLocked", "DoorIsUnlocked" };
    private final SecureRandom random = new SecureRandom();

    private Collection<EventHandler> handlers = new ArrayList<>();

    public void registerEventHandler(EventHandler handler) {
        this.handlers.add(handler);
    }

    public void start() {
        CCSensorEvent event = getNextSensorEvent();
        while (event != null) {
            for (EventHandler handler : handlers) {
                handler.handleEvent(event);
            }
            event = getNextSensorEvent();
        }
    }

    private CCSensorEvent getNextSensorEvent() {
        // pretend like we're getting the events from physical world, but here we're going to just generate some random events
        if (random.nextDouble() < 0.05) return null; // null means end of event stream
        String sensorEventType = eventTypes[(int) (6 * random.nextDouble())];
        String objectId = "" + ((int) (10 * random.nextDouble()));
        return new CCSensorEvent(sensorEventType, objectId);
    }


}
