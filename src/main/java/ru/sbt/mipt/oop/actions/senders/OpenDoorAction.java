package ru.sbt.mipt.oop.actions.senders;

import ru.sbt.mipt.oop.actions.Action;
import ru.sbt.mipt.oop.actions.Actionable;
import ru.sbt.mipt.oop.objects.Door;
import ru.sbt.mipt.oop.sensor_commands.CommandSender;
import ru.sbt.mipt.oop.sensor_commands.CommandType;
import ru.sbt.mipt.oop.sensor_commands.SensorCommand;

public class OpenDoorAction implements Action {
    private final String doorId;
    private boolean result = false;
    private final CommandSender sender;

    public OpenDoorAction(String doorId, CommandSender sender) {
        this.doorId = doorId;
        this.sender = sender;
    }

    @Override
    public void execute(Actionable object) {
        if (object instanceof Door) {
            Door door = (Door) object;
            if (door.getId().equals(doorId)) {
                door.setOpen(true);
                SensorCommand command = new SensorCommand(CommandType.OPEN_DOOR, door.getId());
                sender.sendCommand(command);
                result = true;
            }
        }
    }

    public boolean payload() {
        return result;
    }
}
