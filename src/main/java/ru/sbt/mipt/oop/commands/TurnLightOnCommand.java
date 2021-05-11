package ru.sbt.mipt.oop.commands;

import ru.sbt.mipt.oop.actions.senders.TurnOnLightInHomeAction;
import ru.sbt.mipt.oop.objects.SmartHome;
import ru.sbt.mipt.oop.sensor_commands.CommandSender;

public class TurnLightOnCommand implements Command {
    private final SmartHome smartHome;
    private final CommandSender sender;

    public TurnLightOnCommand(SmartHome smartHome, CommandSender sender) {
        this.smartHome = smartHome;
        this.sender = sender;
    }

    @Override
    public void execute() {
        smartHome.execute(new TurnOnLightInHomeAction(sender));
    }
}
