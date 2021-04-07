package ru.sbt.mipt.oop.events.sensors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.actions.finders.FindLightByIdAction;
import ru.sbt.mipt.oop.events.EventHandler;
import ru.sbt.mipt.oop.io.FileSmartHomeReader;
import ru.sbt.mipt.oop.io.Logger;
import ru.sbt.mipt.oop.io.SmartHomeReader;
import ru.sbt.mipt.oop.objects.Light;
import ru.sbt.mipt.oop.objects.SmartHome;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LightEventHandlerTest {
    private EventHandler lightSensorEventHandler;
    private SmartHome smartHome;

    @BeforeEach
    public void configureSmartHome() {
        String filename = "src/test/resources/smartHomeTesting.js";
        SmartHomeReader reader = new FileSmartHomeReader(filename);
        smartHome = reader.readSmartHome();

        Logger logger = str -> {};
        lightSensorEventHandler = new LightEventHandler(smartHome, logger);
    }

    @Test
    public void setLightOnTest() {
        String lightId = "4";

        FindLightByIdAction action = new FindLightByIdAction(lightId);
        smartHome.execute(action);
        Light light = action.payload();

        assertFalse(light.isOn());

        SensorEvent event = new SensorEvent(SensorEventType.LIGHT_ON, lightId);
        lightSensorEventHandler.handleEvent(event);
        assertTrue(light.isOn());
    }

    @Test
    public void setLightOffTest() {
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
    public void crazyMonkeyFoundTheSwitchTest() {
        String lightId = "1";

        FindLightByIdAction action = new FindLightByIdAction(lightId);
        smartHome.execute(action);
        Light light = action.payload();

        // Let the monkey randomly turn on/off our light
        Random random = new Random();
        for (int i = 0; i < 100; ++i) {
            boolean monkeyChoice = random.nextBoolean();
            if (monkeyChoice) {
                lightSensorEventHandler.handleEvent(new SensorEvent(SensorEventType.LIGHT_ON, lightId));
                assertTrue(light.isOn());
            } else {
                lightSensorEventHandler.handleEvent(new SensorEvent(SensorEventType.LIGHT_OFF, lightId));
                assertFalse(light.isOn());
            }
        }
    }
}
