package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.actions.TurnLightOffAction;
import ru.sbt.mipt.oop.actions.TurnLightOnAction;
import ru.sbt.mipt.oop.io.Logger;
import ru.sbt.mipt.oop.objects.SmartHome;

import static ru.sbt.mipt.oop.events.SensorEventType.LIGHT_OFF;
import static ru.sbt.mipt.oop.events.SensorEventType.LIGHT_ON;

public class LightEventHandler implements EventHandler {
    private final Logger logger;
    private final SmartHome smartHome;

    public LightEventHandler(SmartHome smartHome, Logger logger) {
        this.smartHome = smartHome;
        this.logger = logger;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        if (event.getType() == LIGHT_ON) {
            turnLightOn(event.getObjectId());
        }
        if (event.getType() == LIGHT_OFF) {
            turnLightOff(event.getObjectId());
        }
    }

    private void turnLightOff(String lightId) {
        smartHome.execute(new TurnLightOffAction(lightId));
        logger.log("Light " + lightId + " was turned off.");
    }

    private void turnLightOn(String lightId) {
        smartHome.execute(new TurnLightOnAction(lightId));
        logger.log("Light " + lightId + " was turned on.");
    }
}
