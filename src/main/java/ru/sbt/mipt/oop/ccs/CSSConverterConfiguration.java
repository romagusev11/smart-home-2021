package ru.sbt.mipt.oop.ccs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sbt.mipt.oop.events.alarm.AlarmEventType;
import ru.sbt.mipt.oop.events.sensors.SensorEventType;

@Configuration
public class CSSConverterConfiguration {
    @Bean
    CCSEventToEventConverter defaultConverter() {
        CCSEventToEventConverter eventConverter = new CCSEventToEventConverter();
        eventConverter.addConverter("LightIsOn", new SensorEventFactory(SensorEventType.LIGHT_ON));
        eventConverter.addConverter("LightIsOff", new SensorEventFactory(SensorEventType.LIGHT_OFF));
        eventConverter.addConverter("DoorIsOpen", new SensorEventFactory(SensorEventType.DOOR_OPEN));
        eventConverter.addConverter("DoorIsClosed", new SensorEventFactory(SensorEventType.DOOR_CLOSED));
        eventConverter.addConverter("DoorIsLocked", new AlarmEventFactory(AlarmEventType.ALARM_ACTIVATE, "111"));
        eventConverter.addConverter("DoorIsUnlocked", new AlarmEventFactory(AlarmEventType.ALARM_DEACTIVATE, "111"));
        return  eventConverter;
    }
}
