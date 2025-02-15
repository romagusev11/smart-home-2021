package ru.sbt.mipt.oop.alarm;

public interface AlarmState {
    void activate(String code);
    void deactivate(String code);
    void setOnAlert();
    void react(AlarmReactor reactor);
}
