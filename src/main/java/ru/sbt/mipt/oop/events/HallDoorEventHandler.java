package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.actions.SmartHomeActions;
import ru.sbt.mipt.oop.commands.CommandSender;
import ru.sbt.mipt.oop.io.Logger;
import ru.sbt.mipt.oop.objects.Door;
import ru.sbt.mipt.oop.objects.Room;
import ru.sbt.mipt.oop.objects.SmartHome;

import static ru.sbt.mipt.oop.events.SensorEventType.DOOR_CLOSED;

public class HallDoorEventHandler implements EventHandler{
    private final CommandSender sender;
    private final SmartHome smartHome;

    public HallDoorEventHandler(SmartHome smartHome, CommandSender sender) {
        this.smartHome = smartHome;
        this.sender = sender;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        // если мы получили событие о закрытие двери в холле - это значит, что была закрыта входная дверь.
        // в этом случае мы хотим автоматически выключить свет во всем доме (это же умный дом!)
        if (event.getType() == DOOR_CLOSED) {
            for (Room room : smartHome.getRooms()) {
                for (Door door : room.getDoors()) {
                    if (door.getId().equals(event.getObjectId())) {
                        if (room.getName().equals("hall")) {
                            SmartHomeActions.turnOffLightInHome(smartHome, sender);
                        }
                    }
                }
            }
        }
    }
}
