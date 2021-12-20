package com.coolcompany.smarthome.sample;

import com.coolcompany.smarthome.events.SensorEventsManager;

public class Sample {

    public static void main(String[] args) {
        SensorEventsManager sensorEventsManager = new SensorEventsManager();
        Logger logger = str -> {};
        sensorEventsManager.registerEventHandler(event -> logger.log("Event type [" + event.getEventType() + "] from object with id=" + event.getObjectId() + "]"));
        sensorEventsManager.start();
    }

    private interface Logger {
        void log(String str);
    }
}
