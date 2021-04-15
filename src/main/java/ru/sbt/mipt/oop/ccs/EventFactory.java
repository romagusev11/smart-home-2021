package ru.sbt.mipt.oop.ccs;

import com.coolcompany.smarthome.events.CCSensorEvent;
import ru.sbt.mipt.oop.events.Event;

public interface EventFactory {
    Event convert(CCSensorEvent ccSensorEvent);
}
