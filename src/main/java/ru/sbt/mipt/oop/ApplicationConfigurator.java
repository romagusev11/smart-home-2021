package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.commands.CommandSender;
import ru.sbt.mipt.oop.commands.ConsoleCommandSender;
import ru.sbt.mipt.oop.events.*;
import ru.sbt.mipt.oop.io.*;
import ru.sbt.mipt.oop.objects.SmartHome;

public class ApplicationConfigurator {
    public static void main(String... args) {
        // считываем состояние дома из файла
        SmartHomeReader reader = new FileSmartHomeReader("smart-home-1.js");
        SmartHome smartHome = reader.readSmartHome();

        SensorEventGenerator generator = new RandomSensorEventGenerator();
        CommandSender sender = new ConsoleCommandSender();
        Logger logger = new ConsoleLogger();
        HandlerCollection handlerCollection = new HandlerCollectionImpl();
        EventLoop eventLoop = new EventLoopImpl(logger, generator, handlerCollection);
        Application application = new Application(eventLoop);

        EventHandler lightEventHandler = new LightEventHandler(smartHome, logger);
        EventHandler doorEventHandler = new DoorEventHandler(smartHome, logger);
        EventHandler hallDoorEventHandler = new HallDoorEventHandler(smartHome, sender);

        handlerCollection.addHandler(lightEventHandler);
        handlerCollection.addHandler(doorEventHandler);
        handlerCollection.addHandler(hallDoorEventHandler);

        application.run();
    }
}
