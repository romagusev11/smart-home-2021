package ru.sbt.mipt.oop.alarm;

public interface AlarmReactor {
    void onAlarmActivatedState();
    void onAlarmDeactivatedState();
    void onAlarmOnAlertState();
}
