package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.actions.SmartHomeActions;
import ru.sbt.mipt.oop.io.Logger;
import ru.sbt.mipt.oop.commands.*;
import ru.sbt.mipt.oop.objects.*;

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
        if (event.getType() == DOOR_OPEN || event.getType() == DOOR_CLOSED) {
            // событие от двери
            for (Room room : smartHome.getRooms()) {
                for (Door door : room.getDoors()) {
                    if (door.getId().equals(event.getObjectId())) {
                        if (event.getType() == DOOR_OPEN) {
                            openDoor(room, door);
                        } else {
                            closeDoor(room, door);
                        }
                    }
                }
            }
        }
    }

    private void closeDoor(Room room, Door door) {
        SmartHomeActions.closeDoor(door);
        logger.log("Door " + door.getId() + " in room " + room.getName() + " was closed.");
    }

    private void openDoor(Room room, Door door) {
        SmartHomeActions.openDoor(door);
        logger.log("Door " + door.getId() + " in room " + room.getName() + " was opened.");
    }
}
