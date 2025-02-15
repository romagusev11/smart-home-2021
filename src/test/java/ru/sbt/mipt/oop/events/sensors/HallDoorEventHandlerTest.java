package ru.sbt.mipt.oop.events.sensors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.actions.Action;
import ru.sbt.mipt.oop.actions.finders.FindLightByIdAction;
import ru.sbt.mipt.oop.actions.finders.IsDoorInRoomAction;
import ru.sbt.mipt.oop.events.EventHandler;
import ru.sbt.mipt.oop.io.FileSmartHomeReader;
import ru.sbt.mipt.oop.io.SmartHomeReader;
import ru.sbt.mipt.oop.objects.Light;
import ru.sbt.mipt.oop.objects.SmartHome;
import ru.sbt.mipt.oop.sensor_commands.CommandSender;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HallDoorEventHandlerTest {
    private EventHandler hallDoorSensorEventHandler;
    private SmartHome smartHome;

    @BeforeEach
    public void configureSmartHome() {
        String filename = "src/test/resources/smartHomeTesting.js";
        SmartHomeReader reader = new FileSmartHomeReader(filename);
        smartHome = reader.readSmartHome();

        CommandSender sender = str -> {};
        hallDoorSensorEventHandler = new HallDoorEventHandler(smartHome, sender);
    }

    @Test
    public void closeHallDoorTest() {
        String hallDoorId = "4";

        IsDoorInRoomAction action = new IsDoorInRoomAction(hallDoorId, "hall");
        smartHome.execute(action);
        assertTrue(action.payload());

        SensorEvent event = new SensorEvent(SensorEventType.DOOR_CLOSED, hallDoorId);
        hallDoorSensorEventHandler.handleEvent(event);

        Action assertAllLightIsOffAction = object -> {
            if (object instanceof Light light) {
                assertFalse(light.isOn());
            }
        };
        smartHome.execute(assertAllLightIsOffAction);
    }

    @Test
    public void openHallDoorTest() {
        String hallDoorId = "4";
        String lightId = "2";

        FindLightByIdAction findLightByIdAction = new FindLightByIdAction(lightId);
        smartHome.execute(findLightByIdAction);
        Light light = findLightByIdAction.payload();
        assertTrue(light.isOn());

        IsDoorInRoomAction action = new IsDoorInRoomAction(hallDoorId, "hall");
        smartHome.execute(action);
        assertTrue(action.payload());

        SensorEvent event = new SensorEvent(SensorEventType.DOOR_OPEN, hallDoorId);
        hallDoorSensorEventHandler.handleEvent(event);

        assertTrue(light.isOn());
    }
}
