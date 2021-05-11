package ru.sbt.mipt.oop.commands;

import ru.sbt.mipt.oop.actions.senders.TurnOffLightInHomeAction;
import ru.sbt.mipt.oop.objects.SmartHome;
import ru.sbt.mipt.oop.sensor_commands.CommandSender;

public class TurnLightOffCommand implements Command {
    private final SmartHome smartHome;
    private final CommandSender sender;

    public TurnLightOffCommand(SmartHome smartHome, CommandSender sender) {
        this.smartHome = smartHome;
        this.sender = sender;
    }

    @Override
    public void execute() {
        smartHome.execute(new TurnOffLightInHomeAction(sender));
    }
}
