package ru.sbt.mipt.oop.events.sensors;

import ru.sbt.mipt.oop.actions.recievers.CloseDoorAction;
import ru.sbt.mipt.oop.actions.recievers.OpenDoorAction;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.EventHandler;
import ru.sbt.mipt.oop.io.Logger;
import ru.sbt.mipt.oop.objects.SmartHome;

import static ru.sbt.mipt.oop.events.sensors.SensorEventType.DOOR_CLOSED;
import static ru.sbt.mipt.oop.events.sensors.SensorEventType.DOOR_OPEN;

public class DoorEventHandler implements EventHandler {
    private final Logger logger;
    private final SmartHome smartHome;

    public DoorEventHandler(SmartHome smartHome, Logger logger) {
        this.smartHome = smartHome;
        this.logger = logger;
    }

    @Override
    public void handleEvent(Event event) {
        if (event instanceof SensorEvent) {
            SensorEvent sensorEvent = (SensorEvent) event;
            if (sensorEvent.getType() == DOOR_OPEN) {
                openDoor(sensorEvent.getObjectId());
            }

            if (sensorEvent.getType() == DOOR_CLOSED) {
                closeDoor(sensorEvent.getObjectId());
            }
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
