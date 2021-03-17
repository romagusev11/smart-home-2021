package ru.sbt.mipt.oop.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HandlerCollectionImpl implements HandlerCollection{
    private final List<EventHandler> eventHandlerList = new ArrayList<>();

    @Override
    public Collection<EventHandler> getHandlers() {
        return eventHandlerList;
    }

    @Override
    public void addHandler(EventHandler eventHandler) {
        eventHandlerList.add(eventHandler);
    }
}
