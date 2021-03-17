package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.io.Logger;

public class EventLoopImpl implements EventLoop {
    private final Logger logger;
    private final SensorEventGenerator generator;
    private final HandlerCollection handlerCollection;

    public EventLoopImpl(Logger logger,
                         SensorEventGenerator generator,
                         HandlerCollection handlerCollection) {
        this.logger = logger;
        this.generator = generator;
        this.handlerCollection = handlerCollection;
    }

    @Override
    public void handleEvents() {
        SensorEvent event = generator.getNextSensorEvent();
        while (event != null) {
            logger.log("Got event: " + event);
            for (EventHandler eventHandler : handlerCollection.getHandlers()) {
                eventHandler.handleEvent(event);
            }
            event = generator.getNextSensorEvent();
        }
    }
}
