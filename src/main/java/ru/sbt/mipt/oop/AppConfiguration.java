package ru.sbt.mipt.oop;

import com.coolcompany.smarthome.events.EventHandler;
import com.coolcompany.smarthome.events.SensorEventsManager;
import en.supercompany.remotecontrol.RemoteControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.alarm.AlertMessageSender;
import ru.sbt.mipt.oop.alarm.SmsSender;
import ru.sbt.mipt.oop.ccs.CCSAdapter;
import ru.sbt.mipt.oop.ccs.CCSEventToEventConverter;
import ru.sbt.mipt.oop.ccs.CSSConverterConfiguration;
import ru.sbt.mipt.oop.commands.*;
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
import ru.sbt.mipt.oop.remotecontrol.RemoteControlImplBuilder;
import ru.sbt.mipt.oop.sensor_commands.CommandSender;
import ru.sbt.mipt.oop.sensor_commands.ConsoleCommandSender;

import java.util.Collection;

@Configuration
@Import(CSSConverterConfiguration.class)
public class AppConfiguration {

    @Autowired
    CCSEventToEventConverter defaultConverter;

    @Bean
    Alarm alarm() {
        return new Alarm();
    }

    @Bean
    AlertMessageSender alertMessageSender() {
        return new SmsSender();
    }

    @Bean
    SmartHome smartHome() {
        SmartHomeReader reader = new FileSmartHomeReader("smart-home-1.js");
        return reader.readSmartHome();
    }

    @Bean
    CommandSender commandSender() {
        return new ConsoleCommandSender();
    }

    @Bean
    Logger logger() {
        return new ConsoleLogger();
    }

    @Bean
    EventHandler lightEventHandler(Alarm alarm, SmartHome smartHome, Logger logger,
                                   AlertMessageSender alertMessageSender) {
        return new CCSAdapter(new AlarmConnectedHandler(alarm,
                new LightEventHandler(smartHome, logger), alertMessageSender), defaultConverter);
    }

    @Bean
    EventHandler doorEventHandler(Alarm alarm, SmartHome smartHome, Logger logger,
                                  AlertMessageSender alertMessageSender) {
        return new CCSAdapter(new AlarmConnectedHandler(alarm,
                new DoorEventHandler(smartHome, logger), alertMessageSender), defaultConverter);
    }

    @Bean
    EventHandler hallDoorEventHandler(Alarm alarm, SmartHome smartHome, CommandSender sender,
                                      AlertMessageSender alertMessageSender) {
        return new CCSAdapter(new AlarmConnectedHandler(alarm,
                new HallDoorEventHandler(smartHome, sender), alertMessageSender), defaultConverter);
    }

    @Bean
    EventHandler alarmHandler(Alarm alarm) {
        return new CCSAdapter(new AlarmEventHandler(alarm), defaultConverter);
    }

    @Bean
    SensorEventsManager sensorEventsManager(Collection<EventHandler> eventHandlers) {
        SensorEventsManager sensorEventsManager = new SensorEventsManager();
        for (var handler : eventHandlers) {
            sensorEventsManager.registerEventHandler(handler);
        }
        return sensorEventsManager;
    }

    @Bean
    RemoteControl remoteControl(SmartHome smartHome, Alarm alarm, CommandSender sender) {
        return new RemoteControlImplBuilder()
                .addCommand("A", new ActivateAlarmCommand(alarm, "111"))
                .addCommand("B", new SetAlarmOnAlertCommand(alarm))
                .addCommand("C", new CloseFrontDoorCommand(smartHome, sender))
                .addCommand("D", new CloseFrontDoorCommand(smartHome, sender))
                .addCommand("1", new TurnLightInRoomOnCommand(smartHome, sender, "hall"))
                .addCommand("2", new TurnLightOffCommand(smartHome, sender))
                .addCommand("3", new TurnLightOnCommand(smartHome, sender))
                .getResult();
    }
}
