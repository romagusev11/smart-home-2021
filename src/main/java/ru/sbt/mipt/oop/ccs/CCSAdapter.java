package ru.sbt.mipt.oop.ccs;

import com.coolcompany.smarthome.events.CCSensorEvent;
import com.coolcompany.smarthome.events.EventHandler;

public class CCSAdapter implements EventHandler {
    private final ru.sbt.mipt.oop.events.EventHandler eventHandler;
    private final CCSEventToEventConverter eventConverter;

    public CCSAdapter(ru.sbt.mipt.oop.events.EventHandler eventHandler,
                      CCSEventToEventConverter eventConverter) {
        this.eventHandler = eventHandler;
        this.eventConverter = eventConverter;
    }

    @Override
    public void handleEvent(CCSensorEvent event) {
        eventHandler.handleEvent(eventConverter.convert(event));
    }
}
