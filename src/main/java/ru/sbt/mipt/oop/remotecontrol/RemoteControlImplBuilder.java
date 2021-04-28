package ru.sbt.mipt.oop.remotecontrol;

import ru.sbt.mipt.oop.commands.Command;

public class RemoteControlImplBuilder {
    private RemoteControlImpl remoteControl = new RemoteControlImpl();

    public void reset() {
        remoteControl = new RemoteControlImpl();
    }

    public RemoteControlImplBuilder addCommand(String buttonCode, Command command) {
        remoteControl.addCommand(buttonCode, command);
        return this;
    }

    public RemoteControlImpl getResult() {
        return remoteControl;
    }
}
