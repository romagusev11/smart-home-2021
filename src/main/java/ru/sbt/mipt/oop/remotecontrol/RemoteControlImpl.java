package ru.sbt.mipt.oop.remotecontrol;

import en.supercompany.remotecontrol.RemoteControl;

import java.util.HashMap;
import java.util.Map;

public class RemoteControlImpl implements RemoteControl {
    private final Map<String, RemoteControlCommand> commandMap = new HashMap<>();

    public RemoteControlImpl addCommand(String buttonCode, RemoteControlCommand command) {
        commandMap.put(buttonCode, command);
        return this;
    }

    @Override
    public void onButtonPressed(String buttonCode, String rcId) {
        if (commandMap.containsKey(buttonCode)) {
            commandMap.get(buttonCode).execute();
        }
    }
}
