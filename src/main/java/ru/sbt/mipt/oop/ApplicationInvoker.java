package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.commands.CommandSender;
import ru.sbt.mipt.oop.commands.ConsoleCommandSender;
import ru.sbt.mipt.oop.events.*;
import ru.sbt.mipt.oop.io.*;

public class ApplicationInvoker {
    public static void main(String... args) {
        SmartHomeReader reader = new FileSmartHomeReader("smart-home-1.js");
        SensorEventGenerator generator = new RandomSensorEventGenerator();
        CommandSender sender = new ConsoleCommandSender();
        Logger logger = new ConsoleLogger();
        Application application = new Application(reader, generator, logger);

        EventHandler lightEventHandler = new LightEventHandler(logger);
        EventHandler doorEventHandler = new DoorEventHandler(logger, sender);

        application.subscribeHandler(SensorEventType.LIGHT_OFF, lightEventHandler);
        application.subscribeHandler(SensorEventType.LIGHT_OFF, lightEventHandler);
        application.subscribeHandler(SensorEventType.DOOR_OPEN, doorEventHandler);
        application.subscribeHandler(SensorEventType.DOOR_CLOSED, doorEventHandler);

        application.run();
    }
}
