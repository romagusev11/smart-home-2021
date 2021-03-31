package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.alarm.SmsSender;
import ru.sbt.mipt.oop.commands.CommandSender;
import ru.sbt.mipt.oop.commands.ConsoleCommandSender;
import ru.sbt.mipt.oop.events.*;
import ru.sbt.mipt.oop.events.alarm.AlarmConnectedHandler;
import ru.sbt.mipt.oop.events.alarm.AlarmEventHandler;
import ru.sbt.mipt.oop.events.sensors.DoorEventHandler;
import ru.sbt.mipt.oop.events.sensors.HallDoorEventHandler;
import ru.sbt.mipt.oop.events.sensors.LightEventHandler;
import ru.sbt.mipt.oop.io.ConsoleLogger;
import ru.sbt.mipt.oop.io.FileSmartHomeReader;
import ru.sbt.mipt.oop.io.Logger;
import ru.sbt.mipt.oop.io.SmartHomeReader;
import ru.sbt.mipt.oop.objects.SmartHome;

import java.util.ArrayList;
import java.util.List;

public class ApplicationConfigurator {
    public static void main(String... args) {
        // считываем состояние дома из файла
        SmartHomeReader reader = new FileSmartHomeReader("smart-home-1.js");
        SmartHome smartHome = reader.readSmartHome();

        EventGenerator generator = new RandomEventGenerator();
        CommandSender sender = new ConsoleCommandSender();
        Logger logger = new ConsoleLogger();
        List<EventHandler> eventHandlers = new ArrayList<>();
        Alarm alarm = new Alarm(new SmsSender());
        AlarmEventHandler alarmHandler = new AlarmEventHandler(alarm);

        EventLoop eventLoop = new EventLoopImpl(logger, generator, eventHandlers);
        Application application = new Application(eventLoop);

        EventHandler lightEventHandler =
                new AlarmConnectedHandler(alarm, new LightEventHandler(smartHome, logger));
        EventHandler doorEventHandler =
                new AlarmConnectedHandler(alarm, new DoorEventHandler(smartHome, logger));
        EventHandler hallDoorEventHandler =
                new AlarmConnectedHandler(alarm, new HallDoorEventHandler(smartHome, sender));

        eventHandlers.add(lightEventHandler);
        eventHandlers.add(doorEventHandler);
        eventHandlers.add(hallDoorEventHandler);
        eventHandlers.add(alarmHandler);

        application.run();
    }
}
