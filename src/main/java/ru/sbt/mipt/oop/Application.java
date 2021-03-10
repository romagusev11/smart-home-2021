package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.events.*;
import ru.sbt.mipt.oop.io.Logger;
import ru.sbt.mipt.oop.io.SmartHomeReader;
import ru.sbt.mipt.oop.objects.SmartHome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {
    private final SmartHomeReader reader;
    private final SensorEventGenerator generator;
    private final Logger logger;
    private final Map<SensorEventType, List<EventHandler>> subscribers;

    public Application(SmartHomeReader reader,
                       SensorEventGenerator generator,
                       Logger logger) {
        this.reader = reader;
        this.generator = generator;
        this.logger = logger;
        this.subscribers = new HashMap<>();
    }

    public void subscribeHandler(SensorEventType eventType, EventHandler handler) {
        if (!subscribers.containsKey(eventType)) {
            subscribers.put(eventType, new ArrayList<>());
        }
        subscribers.get(eventType).add(handler);
    }

    public void run() {
        // считываем состояние дома из файла
        // (чтение происходит после конфигурации)
        SmartHome smartHome = reader.getSmartHome();
        // начинаем цикл обработки событий
        SensorEvent event = generator.getNextSensorEvent();
        while (event != null) {
            logger.log("Got event: " + event);
            // Checking if someone is subscribed to this type of event
            if (subscribers.containsKey(event.getType())) {
                for (EventHandler eventHandler : subscribers.get(event.getType())) {
                    eventHandler.handleEvent(smartHome, event);
                }
            }
            event = generator.getNextSensorEvent();
        }
    }
}
