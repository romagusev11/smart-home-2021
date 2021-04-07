package ru.sbt.mipt.oop.actions.senders;

import ru.sbt.mipt.oop.actions.Action;
import ru.sbt.mipt.oop.actions.Actionable;
import ru.sbt.mipt.oop.commands.CommandSender;
import ru.sbt.mipt.oop.commands.CommandType;
import ru.sbt.mipt.oop.commands.SensorCommand;
import ru.sbt.mipt.oop.objects.Light;

public class TurnLightOnAction implements Action {
    private final String lightId;
    private boolean result = false;
    private final CommandSender sender;

    public TurnLightOnAction(String lightId, CommandSender sender) {
        this.lightId = lightId;
        this.sender = sender;
    }

    @Override
    public void execute(Actionable object) {
        if (object instanceof Light light) {
            if (light.getId().equals(lightId)) {
                light.setOn(true);
                SensorCommand command = new SensorCommand(CommandType.LIGHT_ON, light.getId());
                sender.sendCommand(command);
                result = true;
            }
        }
    }

    public boolean payload() {
        return result;
    }
}
