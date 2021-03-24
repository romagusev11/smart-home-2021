package ru.sbt.mipt.oop.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.actions.Action;
import ru.sbt.mipt.oop.actions.FindLightByIdAction;
import ru.sbt.mipt.oop.actions.IsDoorInRoomAction;
import ru.sbt.mipt.oop.commands.CommandSender;
import ru.sbt.mipt.oop.io.FileSmartHomeReader;
import ru.sbt.mipt.oop.io.SmartHomeReader;
import ru.sbt.mipt.oop.objects.Light;
import ru.sbt.mipt.oop.objects.SmartHome;
import ru.sbt.mipt.oop.sensors.HallDoorEventHandler;
import ru.sbt.mipt.oop.sensors.SensorEvent;
import ru.sbt.mipt.oop.sensors.SensorEventHandler;
import ru.sbt.mipt.oop.sensors.SensorEventType;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HallDoorEventHandlerTest {
    private SensorEventHandler hallDoorSensorEventHandler;
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
            if (object instanceof Light) {
                assertFalse(((Light) object).isOn());
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
