package ru.sbt.mipt.oop.sensors;

import ru.sbt.mipt.oop.actions.CloseDoorAction;
import ru.sbt.mipt.oop.actions.OpenDoorAction;
import ru.sbt.mipt.oop.io.Logger;
import ru.sbt.mipt.oop.objects.SmartHome;

import static ru.sbt.mipt.oop.sensors.SensorEventType.DOOR_CLOSED;
import static ru.sbt.mipt.oop.sensors.SensorEventType.DOOR_OPEN;

public class DoorEventHandler implements SensorEventHandler {
    private final Logger logger;
    private final SmartHome smartHome;

    public DoorEventHandler(SmartHome smartHome, Logger logger) {
        this.smartHome = smartHome;
        this.logger = logger;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        if (event.getType() == DOOR_OPEN) {
            openDoor(event.getObjectId());
        }

        if (event.getType() == DOOR_CLOSED) {
            closeDoor(event.getObjectId());
        }
    }

    private void closeDoor(String doorId) {
        CloseDoorAction action = new CloseDoorAction(doorId);
        smartHome.execute(action);
        if (action.payload()) {
            logger.log("Door " + doorId + " was closed.");
        }
    }

    private void openDoor(String doorId) {
        OpenDoorAction action = new OpenDoorAction(doorId);
        smartHome.execute(action);
        if (action.payload()) {
            logger.log("Door " + doorId + " was opened.");
        }
    }
}
