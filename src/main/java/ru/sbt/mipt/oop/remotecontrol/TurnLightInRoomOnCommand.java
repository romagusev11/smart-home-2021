package ru.sbt.mipt.oop.remotecontrol;

import ru.sbt.mipt.oop.actions.finders.GetRoomLightsAction;
import ru.sbt.mipt.oop.actions.senders.TurnLightOnAction;
import ru.sbt.mipt.oop.commands.CommandSender;
import ru.sbt.mipt.oop.objects.SmartHome;

import java.util.List;

public class TurnLightInRoomOnCommand implements RemoteControlCommand {
    private final SmartHome smartHome;
    private final CommandSender sender;
    private final String roomName;

    public TurnLightInRoomOnCommand(SmartHome smartHome, CommandSender sender, String roomName) {
        this.smartHome = smartHome;
        this.sender = sender;
        this.roomName = roomName;
    }

    @Override
    public void execute() {
        GetRoomLightsAction action = new GetRoomLightsAction(roomName);
        smartHome.execute(action);
        List<String> lightIds = action.payload();

        for (var lightId : lightIds) {
            TurnLightOnAction turnLightOn = new TurnLightOnAction(lightId, sender);
            smartHome.execute(turnLightOn);
        }
    }
}
