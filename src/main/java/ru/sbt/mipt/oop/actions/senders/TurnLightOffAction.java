package ru.sbt.mipt.oop.actions.senders;

import ru.sbt.mipt.oop.actions.Action;
import ru.sbt.mipt.oop.actions.Actionable;
import ru.sbt.mipt.oop.objects.Door;
import ru.sbt.mipt.oop.objects.Light;
import ru.sbt.mipt.oop.sensor_commands.CommandSender;
import ru.sbt.mipt.oop.sensor_commands.CommandType;
import ru.sbt.mipt.oop.sensor_commands.SensorCommand;

public class TurnLightOffAction implements Action {
    private final String lightId;
    private boolean result = false;
    private final CommandSender sender;

    public TurnLightOffAction(String lightId, CommandSender sender) {
        this.lightId = lightId;
        this.sender = sender;
    }

    @Override
    public void execute(Actionable object) {
        if (object instanceof Light) {
            Light light = (Light) object;
            if (light.getId().equals(lightId)) {
                light.setOn(false);
                SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
                sender.sendCommand(command);
                result = true;
            }
        }
    }

    public boolean payload() {
        return result;
    }
}
