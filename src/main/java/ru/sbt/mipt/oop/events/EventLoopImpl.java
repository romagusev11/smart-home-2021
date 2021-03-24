package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.alarm.AlarmEvent;
import ru.sbt.mipt.oop.alarm.AlarmEventHandler;
import ru.sbt.mipt.oop.io.Logger;
import ru.sbt.mipt.oop.sensors.SensorEvent;
import ru.sbt.mipt.oop.sensors.SensorEventHandler;

import java.util.Collection;

public class EventLoopImpl implements EventLoop {
    private final Logger logger;
    private final EventGenerator generator;
    private final Collection<SensorEventHandler> sensorHandlers;
    private final AlarmEventHandler alarmHandler; // No events to handle yet

    public EventLoopImpl(Logger logger,
                         EventGenerator generator,
                         Collection<SensorEventHandler> sensorHandlers,
                         AlarmEventHandler alarmHandler) {
        this.logger = logger;
        this.generator = generator;
        this.sensorHandlers = sensorHandlers;
        this.alarmHandler = alarmHandler;
    }

    @Override
    public void handleEvents() {
        Event event = generator.getNextEvent();
        while (event != null) {
            logger.log("Got event: " + event);
            if (event instanceof SensorEvent sensorEvent) {
                for (SensorEventHandler sensorEventHandler : sensorHandlers) {
                    sensorEventHandler.handleEvent(sensorEvent);
                }
            } else if (event instanceof AlarmEvent alarmEvent) {
                alarmHandler.handleEvent(alarmEvent);
            }
            event = generator.getNextEvent();
        }
    }
}
