package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.io.Logger;
import ru.sbt.mipt.oop.objects.*;

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
        if (event.getType() == LIGHT_ON || event.getType() == LIGHT_OFF) {
            // событие от источника света
            for (Room room : smartHome.getRooms()) {
                for (Light light : room.getLights()) {
                    if (light.getId().equals(event.getObjectId())) {
                        if (event.getType() == LIGHT_ON) {
                            turnLightOn(room, light);
                        } else {
                            turnLightOff(room, light);
                        }
                    }
                }
            }
        }
    }

    private void turnLightOff(Room room, Light light) {
        light.setOn(false);
        logger.log("Light " + light.getId() + " in room " + room.getName() + " was turned off.");
    }

    private void turnLightOn(Room room, Light light) {
        light.setOn(true);
        logger.log("Light " + light.getId() + " in room " + room.getName() + " was turned on.");
    }
}
