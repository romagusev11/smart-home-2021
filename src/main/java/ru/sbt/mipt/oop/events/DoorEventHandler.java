package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.io.Logger;
import ru.sbt.mipt.oop.commands.*;
import ru.sbt.mipt.oop.objects.*;

import static ru.sbt.mipt.oop.events.SensorEventType.DOOR_CLOSED;
import static ru.sbt.mipt.oop.events.SensorEventType.DOOR_OPEN;

public class DoorEventHandler implements EventHandler {
    private final Logger logger;
    private final CommandSender sender;
    private final SmartHome smartHome;

    public DoorEventHandler(SmartHome smartHome, Logger logger, CommandSender sender) {
        this.smartHome = smartHome;
        this.logger = logger;
        this.sender = sender;
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
                            // если мы получили событие о закрытие двери в холле - это значит, что была закрыта входная дверь.
                            // в этом случае мы хотим автоматически выключить свет во всем доме (это же умный дом!)
                            if (room.getName().equals("hall")) {
                                turnOffLightInHome(smartHome);
                            }
                        }
                    }
                }
            }
        }
    }

    private void turnOffLightInHome(SmartHome smartHome) {
        for (Room homeRoom : smartHome.getRooms()) {
            for (Light light : homeRoom.getLights()) {
                light.setOn(false);
                SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
                sender.sendCommand(command);
            }
        }
    }

    private void closeDoor(Room room, Door door) {
        door.setOpen(false);
        logger.log("Door " + door.getId() + " in room " + room.getName() + " was closed.");
    }

    private void openDoor(Room room, Door door) {
        door.setOpen(true);
        logger.log("Door " + door.getId() + " in room " + room.getName() + " was opened.");
    }
}
