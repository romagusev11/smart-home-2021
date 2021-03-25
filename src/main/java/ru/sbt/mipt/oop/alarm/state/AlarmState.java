package ru.sbt.mipt.oop.alarm.state;

import ru.sbt.mipt.oop.alarm.AlarmReactor;

public interface AlarmState {
    void activate(String code);
    void deactivate(String code);
    void setOnAlert();
    void react(AlarmReactor reactor);
}
