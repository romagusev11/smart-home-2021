package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.alarm.AlarmConnectedSensorHandler;
import ru.sbt.mipt.oop.alarm.AlarmEventHandler;
import ru.sbt.mipt.oop.commands.CommandSender;
import ru.sbt.mipt.oop.commands.ConsoleCommandSender;
import ru.sbt.mipt.oop.events.EventGenerator;
import ru.sbt.mipt.oop.events.EventLoop;
import ru.sbt.mipt.oop.events.EventLoopImpl;
import ru.sbt.mipt.oop.events.RandomEventGenerator;
import ru.sbt.mipt.oop.io.ConsoleLogger;
import ru.sbt.mipt.oop.io.FileSmartHomeReader;
import ru.sbt.mipt.oop.io.Logger;
import ru.sbt.mipt.oop.io.SmartHomeReader;
import ru.sbt.mipt.oop.objects.SmartHome;
import ru.sbt.mipt.oop.sensors.DoorEventHandler;
import ru.sbt.mipt.oop.sensors.HallDoorEventHandler;
import ru.sbt.mipt.oop.sensors.LightEventHandler;
import ru.sbt.mipt.oop.sensors.SensorEventHandler;

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
        List<SensorEventHandler> sensorHandlers = new ArrayList<>();
        Alarm alarm = new Alarm();
        AlarmEventHandler alarmHandler = new AlarmEventHandler(alarm);

        EventLoop eventLoop = new EventLoopImpl(logger, generator, sensorHandlers, alarmHandler);
        Application application = new Application(eventLoop);

        SensorEventHandler lightEventHandler =
                new AlarmConnectedSensorHandler(alarm, new LightEventHandler(smartHome, logger), logger);
        SensorEventHandler doorEventHandler =
                new AlarmConnectedSensorHandler(alarm, new DoorEventHandler(smartHome, logger), logger);
        SensorEventHandler hallDoorEventHandler =
                new AlarmConnectedSensorHandler(alarm, new HallDoorEventHandler(smartHome, sender), logger);

        sensorHandlers.add(lightEventHandler);
        sensorHandlers.add(doorEventHandler);
        sensorHandlers.add(hallDoorEventHandler);

        application.run();
    }
}
