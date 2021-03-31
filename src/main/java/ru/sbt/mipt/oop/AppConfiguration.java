package ru.sbt.mipt.oop;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.alarm.SmsSender;
import ru.sbt.mipt.oop.ccs.CCSAdapter;
import ru.sbt.mipt.oop.ccs.CCSEventToEventConverter;
import ru.sbt.mipt.oop.ccs.CSSConverterConfiguration;
import ru.sbt.mipt.oop.commands.CommandSender;
import ru.sbt.mipt.oop.commands.ConsoleCommandSender;
import ru.sbt.mipt.oop.events.EventHandler;
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

@Configuration
@Import(CSSConverterConfiguration.class)
public class AppConfiguration {

    @Autowired
    CCSEventToEventConverter defaultConverter;

    @Bean
    SensorEventsManager sensorEventsManager() {
        // считываем состояние дома из файла
        SmartHomeReader reader = new FileSmartHomeReader("smart-home-1.js");
        SmartHome smartHome = reader.readSmartHome();
        CommandSender sender = new ConsoleCommandSender();
        Logger logger = new ConsoleLogger();

        Alarm alarm = new Alarm(new SmsSender());
        AlarmEventHandler alarmHandler = new AlarmEventHandler(alarm);

        EventHandler lightEventHandler =
                new AlarmConnectedHandler(alarm, new LightEventHandler(smartHome, logger));
        EventHandler doorEventHandler =
                new AlarmConnectedHandler(alarm, new DoorEventHandler(smartHome, logger));
        EventHandler hallDoorEventHandler =
                new AlarmConnectedHandler(alarm, new HallDoorEventHandler(smartHome, sender));

        SensorEventsManager sensorEventsManager = new SensorEventsManager();

        CCSEventToEventConverter eventConverter = defaultConverter;

        sensorEventsManager.registerEventHandler(new CCSAdapter(alarmHandler, eventConverter));
        sensorEventsManager.registerEventHandler(new CCSAdapter(lightEventHandler, eventConverter));
        sensorEventsManager.registerEventHandler(new CCSAdapter(doorEventHandler, eventConverter));
        sensorEventsManager.registerEventHandler(new CCSAdapter(hallDoorEventHandler, eventConverter));
        return sensorEventsManager;
    }
}
