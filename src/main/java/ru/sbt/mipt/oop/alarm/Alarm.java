package ru.sbt.mipt.oop.alarm;

import ru.sbt.mipt.oop.alarm.state.AlarmState;
import ru.sbt.mipt.oop.alarm.state.DeactivatedState;

import java.util.Objects;

public class Alarm {
    private AlarmState state;
    private String code;

    public Alarm() {
        this.state = new DeactivatedState(this);
    }

    public void activate(String code) {
        state.activate(code);
    }

    public void deactivate(String code) {
        state.deactivate(code);
    }

    public void setOnAlert() {
        state.setOnAlert();
    }

    public void changeState(AlarmState state) {
        this.state = state;
    }

    public AlarmState getState() {
        return state;
    }

    public boolean isCorrectCode(String code) {
        return Objects.equals(code, this.code);
    }

    public void setCode(String code) {
        this.code = code;
    }
}
