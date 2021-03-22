package ru.sbt.mipt.oop.events;


import java.util.Collection;

public interface HandlerCollection {
    Collection<EventHandler> getHandlers();

    void addHandler(EventHandler eventHandler);
}
