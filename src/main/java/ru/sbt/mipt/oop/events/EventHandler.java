package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.objects.SmartHome;

public interface EventHandler {
    void handleEvent(SmartHome smartHome, SensorEvent event);
}
