package ru.sbt.mipt.oop.actions;

import ru.sbt.mipt.oop.commands.CommandSender;
import ru.sbt.mipt.oop.commands.CommandType;
import ru.sbt.mipt.oop.commands.SensorCommand;
import ru.sbt.mipt.oop.objects.Door;
import ru.sbt.mipt.oop.objects.Light;
import ru.sbt.mipt.oop.objects.Room;
import ru.sbt.mipt.oop.objects.SmartHome;

public class SmartHomeActions {
    static public void closeDoor(Door door) {
        door.setOpen(false);
    }

    static public void openDoor(Door door) {
        door.setOpen(true);
    }

    static public void turnOffLightInHome(SmartHome smartHome, CommandSender sender) {
        for (Room homeRoom : smartHome.getRooms()) {
            for (Light light : homeRoom.getLights()) {
                light.setOn(false);
                SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
                sender.sendCommand(command);
            }
        }
    }
}
