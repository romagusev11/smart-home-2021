package ru.sbt.mipt.oop.actions.senders;

import ru.sbt.mipt.oop.actions.Action;
import ru.sbt.mipt.oop.actions.Actionable;
import ru.sbt.mipt.oop.commands.CommandSender;
import ru.sbt.mipt.oop.commands.CommandType;
import ru.sbt.mipt.oop.commands.SensorCommand;
import ru.sbt.mipt.oop.objects.Light;

public class TurnOffLightInHomeAction implements Action {
    private final CommandSender sender;

    public TurnOffLightInHomeAction(CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public void execute(Actionable object) {
        if (object instanceof Light light) {
            light.setOn(false);
            SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
            sender.sendCommand(command);
        }
    }
}
