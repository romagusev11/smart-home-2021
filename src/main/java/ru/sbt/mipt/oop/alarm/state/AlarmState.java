package ru.sbt.mipt.oop.alarm.state;

public interface AlarmState {
    void activate(String code);
    void deactivate(String code);
    void setOnAlert();
}
