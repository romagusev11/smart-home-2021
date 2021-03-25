package ru.sbt.mipt.oop.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.actions.FindDoorByIdAction;
import ru.sbt.mipt.oop.events.sensors.DoorEventHandler;
import ru.sbt.mipt.oop.events.sensors.SensorEvent;
import ru.sbt.mipt.oop.events.sensors.SensorEventHandler;
import ru.sbt.mipt.oop.events.sensors.SensorEventType;
import ru.sbt.mipt.oop.io.FileSmartHomeReader;
import ru.sbt.mipt.oop.io.Logger;
import ru.sbt.mipt.oop.io.SmartHomeReader;
import ru.sbt.mipt.oop.objects.Door;
import ru.sbt.mipt.oop.objects.SmartHome;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoorEventHandlerTest {
    private SensorEventHandler doorSensorEventHandler;
    private SmartHome smartHome;

    @BeforeEach
    public void configureSmartHome() {
        String filename = "src/test/resources/smartHomeTesting.js";
        SmartHomeReader reader = new FileSmartHomeReader(filename);
        smartHome = reader.readSmartHome();

        Logger logger = str -> {};
        doorSensorEventHandler = new DoorEventHandler(smartHome, logger);
    }

    @Test
    public void openDoorTest() {
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
    public void closeDoorTest() {
        String doorId = "3";

        FindDoorByIdAction action = new FindDoorByIdAction(doorId);
        smartHome.execute(action);
        Door door = action.payload();

        assertTrue(door.isOpen());

        SensorEvent event = new SensorEvent(SensorEventType.DOOR_CLOSED, doorId);
        doorSensorEventHandler.handleEvent(event);
        assertFalse(door.isOpen());
    }

    @Test
    public void crazyMonkeyFoundTheSwitchTest() {
        String doorId = "1";

        FindDoorByIdAction action = new FindDoorByIdAction(doorId);
        smartHome.execute(action);
        Door door = action.payload();

        // Let the monkey randomly open/close our door
        Random random = new Random();
        for (int i = 0; i < 100; ++i) {
            boolean monkeyChoice = random.nextBoolean();
            if (monkeyChoice) {
                doorSensorEventHandler.handleEvent(new SensorEvent(SensorEventType.DOOR_OPEN, doorId));
                assertTrue(door.isOpen());
            } else {
                doorSensorEventHandler.handleEvent(new SensorEvent(SensorEventType.DOOR_CLOSED, doorId));
                assertFalse(door.isOpen());
            }
        }
    }
}
