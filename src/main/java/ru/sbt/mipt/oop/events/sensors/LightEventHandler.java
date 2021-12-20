package ru.sbt.mipt.oop.events.sensors;

import ru.sbt.mipt.oop.actions.recievers.TurnLightOffAction;
import ru.sbt.mipt.oop.actions.recievers.TurnLightOnAction;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.EventHandler;
import ru.sbt.mipt.oop.io.Logger;
import ru.sbt.mipt.oop.objects.SmartHome;

import static ru.sbt.mipt.oop.events.sensors.SensorEventType.LIGHT_OFF;
import static ru.sbt.mipt.oop.events.sensors.SensorEventType.LIGHT_ON;

public class LightEventHandler implements EventHandler {
    private final Logger logger;
    private final SmartHome smartHome;

    public LightEventHandler(SmartHome smartHome, Logger logger) {
        this.smartHome = smartHome;
        this.logger = logger;
    }

    @Override
    public void handleEvent(Event event) {
        if (event instanceof SensorEvent) {
            SensorEvent sensorEvent = (SensorEvent) event;
            if (sensorEvent.getType() == LIGHT_ON) {
                turnLightOn(sensorEvent.getObjectId());
            }
            if (sensorEvent.getType() == LIGHT_OFF) {
                turnLightOff(sensorEvent.getObjectId());
            }
        }
    }

    private void turnLightOff(String lightId) {
        TurnLightOffAction action = new TurnLightOffAction(lightId);
        smartHome.execute(action);
        if (action.payload()) {
            logger.log("Light " + lightId + " was turned off.");
        }
    }

    private void turnLightOn(String lightId) {
        TurnLightOnAction action = new TurnLightOnAction(lightId);
        smartHome.execute(action);
        if (action.payload()) {
            logger.log("Light " + lightId + " was turned on.");
        }
    }
}
