package ru.sbt.mipt.oop.events.alarm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.actions.finders.FindDoorByIdAction;
import ru.sbt.mipt.oop.actions.finders.FindLightByIdAction;
import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.alarm.AlertMessageSender;
import ru.sbt.mipt.oop.events.EventHandler;
import ru.sbt.mipt.oop.events.sensors.DoorEventHandler;
import ru.sbt.mipt.oop.events.sensors.LightEventHandler;
import ru.sbt.mipt.oop.events.sensors.SensorEvent;
import ru.sbt.mipt.oop.events.sensors.SensorEventType;
import ru.sbt.mipt.oop.io.FileSmartHomeReader;
import ru.sbt.mipt.oop.io.Logger;
import ru.sbt.mipt.oop.io.SmartHomeReader;
import ru.sbt.mipt.oop.objects.Door;
import ru.sbt.mipt.oop.objects.Light;
import ru.sbt.mipt.oop.objects.SmartHome;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlarmConnectedHandlerTest {
    private EventHandler doorSensorEventHandler;
    private EventHandler lightSensorEventHandler;
    private SmartHome smartHome;
    private Alarm alarm;

    @BeforeEach
    public void configureSmartHomeAndAlarm() {
        String filename = "src/test/resources/smartHomeTesting.js";
        SmartHomeReader reader = new FileSmartHomeReader(filename);
        smartHome = reader.readSmartHome();
        alarm = new Alarm();

        AlertMessageSender alertMessageSender = str -> {};
        Logger logger = str -> {};
        lightSensorEventHandler = new AlarmConnectedHandler(alarm,
                new LightEventHandler(smartHome, logger), alertMessageSender);
        doorSensorEventHandler = new AlarmConnectedHandler(alarm,
                new DoorEventHandler(smartHome, logger), alertMessageSender);
    }

    @Test
    public void openDoorAlarmOffTest() {
        String doorId = "2";

        FindDoorByIdAction action = new FindDoorByIdAction(doorId);
        smartHome.execute(action);
        Door door = action.payload();

        assertFalse(door.isOpen());

        SensorEvent event = new SensorEvent(SensorEventType.DOOR_OPEN, doorId);
        doorSensorEventHandler.handleEvent(event);
        assertTrue(door.isOpen());
    }

    @Test
    public void setLightOffAlarmOffTest() {
        String lightId = "3";

        FindLightByIdAction action = new FindLightByIdAction(lightId);
        smartHome.execute(action);
        Light light = action.payload();

        assertTrue(light.isOn());

        SensorEvent event = new SensorEvent(SensorEventType.LIGHT_OFF, lightId);
        lightSensorEventHandler.handleEvent(event);
        assertFalse(light.isOn());
    }

    @Test
    public void openDoorAlarmOnTest() {
        String doorId = "2";
        alarm.activate("111");

        FindDoorByIdAction action = new FindDoorByIdAction(doorId);
        smartHome.execute(action);
        Door door = action.payload();

        assertFalse(door.isOpen());

        SensorEvent event = new SensorEvent(SensorEventType.DOOR_OPEN, doorId);
        doorSensorEventHandler.handleEvent(event);

        assertFalse(door.isOpen());
        assertTrue(alarm.isOnAlert());
    }

    @Test
    public void setLightOnAlarmOnTest() {
        String lightId = "4";
        alarm.activate("111");

        FindLightByIdAction action = new FindLightByIdAction(lightId);
        smartHome.execute(action);
        Light light = action.payload();

        assertFalse(light.isOn());

        SensorEvent event = new SensorEvent(SensorEventType.LIGHT_OFF, lightId);
        lightSensorEventHandler.handleEvent(event);

        assertFalse(light.isOn());
        assertTrue(alarm.isOnAlert());
    }
}
