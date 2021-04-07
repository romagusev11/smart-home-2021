package ru.sbt.mipt.oop.actions.senders;

import ru.sbt.mipt.oop.actions.Action;
import ru.sbt.mipt.oop.actions.Actionable;
import ru.sbt.mipt.oop.commands.CommandSender;
import ru.sbt.mipt.oop.commands.CommandType;
import ru.sbt.mipt.oop.commands.SensorCommand;
import ru.sbt.mipt.oop.objects.Light;

public class TurnOnLightInHomeAction implements Action {
    private final CommandSender sender;

    public TurnOnLightInHomeAction(CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public void execute(Actionable object) {
        if (object instanceof Light) {
            Light light = (Light) object;
            light.setOn(true);
            SensorCommand command = new SensorCommand(CommandType.LIGHT_ON, light.getId());
            sender.sendCommand(command);
        }
    }
}
