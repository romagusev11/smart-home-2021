package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.io.Logger;

import java.util.Collection;

public class EventLoopImpl implements EventLoop {
    private final Logger logger;
    private final EventGenerator generator;
    private final Collection<EventHandler> eventHandlers;

    public EventLoopImpl(Logger logger,
                         EventGenerator generator,
                         Collection<EventHandler> eventHandlers) {
        this.logger = logger;
        this.generator = generator;
        this.eventHandlers = eventHandlers;
    }

    @Override
    public void handleEvents() {
        Event event = generator.getNextEvent();
        while (event != null) {
            logger.log("Got event: " + event);
            for (var handler : eventHandlers) {
                handler.handleEvent(event);
            }
            event = generator.getNextEvent();
        }
    }
}
