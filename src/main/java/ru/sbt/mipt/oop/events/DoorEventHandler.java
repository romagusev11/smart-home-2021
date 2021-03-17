package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.actions.CloseDoorAction;
import ru.sbt.mipt.oop.actions.OpenDoorAction;
import ru.sbt.mipt.oop.io.Logger;
import ru.sbt.mipt.oop.objects.SmartHome;

import static ru.sbt.mipt.oop.events.SensorEventType.DOOR_CLOSED;
import static ru.sbt.mipt.oop.events.SensorEventType.DOOR_OPEN;

public class DoorEventHandler implements EventHandler {
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
        smartHome.execute(new CloseDoorAction(doorId));
        logger.log("Door " + doorId + " was closed.");
    }

    private void openDoor(String doorId) {
        smartHome.execute(new OpenDoorAction(doorId));
        logger.log("Door " + doorId + " was opened.");
    }
}
