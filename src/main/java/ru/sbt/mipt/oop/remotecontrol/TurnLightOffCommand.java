package ru.sbt.mipt.oop.remotecontrol;

import ru.sbt.mipt.oop.actions.senders.TurnOffLightInHomeAction;
import ru.sbt.mipt.oop.commands.CommandSender;
import ru.sbt.mipt.oop.objects.SmartHome;

public class TurnLightOffCommand implements RemoteControlCommand {
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
