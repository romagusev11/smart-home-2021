package ru.sbt.mipt.oop.remotecontrol;

import ru.sbt.mipt.oop.alarm.Alarm;

public class SetAlarmOnAlertCommand implements RemoteControlCommand {
    private final Alarm alarm;

    public SetAlarmOnAlertCommand(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public void execute() {
        alarm.setOnAlert();
    }
}
