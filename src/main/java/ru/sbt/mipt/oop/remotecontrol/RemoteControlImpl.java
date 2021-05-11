package ru.sbt.mipt.oop.remotecontrol;

import en.supercompany.remotecontrol.RemoteControl;
import ru.sbt.mipt.oop.commands.Command;

import java.util.HashMap;
import java.util.Map;

public class RemoteControlImpl implements RemoteControl {
    private final Map<String, Command> commandMap = new HashMap<>();

    void addCommand(String buttonCode, Command command) {
        commandMap.put(buttonCode, command);
    }

    @Override
    public void onButtonPressed(String buttonCode, String rcId) {
        if (commandMap.containsKey(buttonCode)) {
            commandMap.get(buttonCode).execute();
        }
    }
}
