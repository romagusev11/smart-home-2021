package ru.sbt.mipt.oop.remotecontrol;

import ru.sbt.mipt.oop.actions.senders.TurnOnLightInHomeAction;
import ru.sbt.mipt.oop.commands.CommandSender;
import ru.sbt.mipt.oop.objects.SmartHome;

public class TurnLightOnCommand implements RemoteControlCommand {
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
