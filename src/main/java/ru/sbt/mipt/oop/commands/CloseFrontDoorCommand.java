package ru.sbt.mipt.oop.commands;

import ru.sbt.mipt.oop.actions.finders.GetRoomDoorsAction;
import ru.sbt.mipt.oop.actions.senders.CloseDoorAction;
import ru.sbt.mipt.oop.objects.SmartHome;
import ru.sbt.mipt.oop.sensor_commands.CommandSender;

import java.util.List;

public class CloseFrontDoorCommand implements Command {
    private final SmartHome smartHome;
    private final CommandSender sender;

    public CloseFrontDoorCommand(SmartHome smartHome, CommandSender sender) {
        this.smartHome = smartHome;
        this.sender = sender;
    }


    @Override
    public void execute() {
        GetRoomDoorsAction action = new GetRoomDoorsAction("hall");
        smartHome.execute(action);
        List<String> doorIds = action.payload();
        if (doorIds.size() != 1) {
            throw new RuntimeException("Can't find front door!");
        }

        CloseDoorAction closeDoorAction = new CloseDoorAction(doorIds.get(0), sender);
        smartHome.execute(closeDoorAction);
    }

}
