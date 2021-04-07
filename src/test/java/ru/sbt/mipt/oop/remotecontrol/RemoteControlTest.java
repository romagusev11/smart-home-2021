package ru.sbt.mipt.oop.remotecontrol;

import en.supercompany.remotecontrol.RemoteControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.actions.Action;
import ru.sbt.mipt.oop.actions.finders.FindDoorByIdAction;
import ru.sbt.mipt.oop.actions.finders.FindLightByIdAction;
import ru.sbt.mipt.oop.actions.finders.IsDoorInRoomAction;
import ru.sbt.mipt.oop.actions.finders.IsLightInRoomAction;
import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.commands.CommandSender;
import ru.sbt.mipt.oop.io.FileSmartHomeReader;
import ru.sbt.mipt.oop.io.SmartHomeReader;
import ru.sbt.mipt.oop.objects.Door;
import ru.sbt.mipt.oop.objects.Light;
import ru.sbt.mipt.oop.objects.SmartHome;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RemoteControlTest {
    private RemoteControl remoteControl;
    private SmartHome smartHome;
    private Alarm alarm;
    private final String code = "111";
    private final String rcId = "1";

    @BeforeEach
    public void configureSmartHomeAndRemoteControl() {
        String filename = "src/test/resources/smartHomeTesting.js";
        SmartHomeReader reader = new FileSmartHomeReader(filename);
        smartHome = reader.readSmartHome();
        alarm = new Alarm();
        CommandSender sender = str -> {};
        remoteControl = new RemoteControlImpl()
                .addCommand("A", new ActivateAlarmCommand(alarm, code))
                .addCommand("B", new SetAlarmOnAlertCommand(alarm))
                .addCommand("C", new CloseFrontDoorCommand(smartHome, sender))
                .addCommand("D", new CloseFrontDoorCommand(smartHome, sender))
                .addCommand("1", new TurnLightInRoomOnCommand(smartHome, sender, "hall"))
                .addCommand("2", new TurnLightOffCommand(smartHome, sender))
                .addCommand("3", new TurnLightOnCommand(smartHome, sender));
    }

    @Test
    public void closeFrontDoorButtonTest() {
        // In this realization closing front door with remote controller will not automatically
        // turn off lights in home, because we will only send the command to close the door,
        // but do not receive the feedback that door is closed aka SensorEvent.

        String hallDoorId = "4";
        IsDoorInRoomAction action = new IsDoorInRoomAction(hallDoorId, "hall");
        smartHome.execute(action);
        assertTrue(action.payload());

        FindDoorByIdAction findDoorByIdAction = new FindDoorByIdAction(hallDoorId);
        smartHome.execute(findDoorByIdAction);
        Door hallDoor = findDoorByIdAction.payload();
        assertTrue(hallDoor.isOpen());

        remoteControl.onButtonPressed("C", rcId);

        assertFalse(hallDoor.isOpen());
    }

    @Test
    public void activateAlarmTest() {
        assertTrue(alarm.isDeactivated());

        remoteControl.onButtonPressed("A", rcId);

        assertTrue(alarm.isActivated());
    }

    @Test
    public void setAlarmOnAlertTest() {
        assertTrue(alarm.isDeactivated());

        remoteControl.onButtonPressed("B", rcId);

        assertTrue(alarm.isOnAlert());
    }

    @Test
    public void turnLightInHallOnTest() {
        String hallLightId = "7";

        FindLightByIdAction findLightByIdAction = new FindLightByIdAction(hallLightId);
        smartHome.execute(findLightByIdAction);
        Light light = findLightByIdAction.payload();
        assertFalse(light.isOn());

        IsLightInRoomAction action = new IsLightInRoomAction(hallLightId, "hall");
        smartHome.execute(action);
        assertTrue(action.payload());

        remoteControl.onButtonPressed("1", rcId);

        assertTrue(light.isOn());
    }

    @Test
    public void turnLightOffTest() {
        remoteControl.onButtonPressed("2", rcId);

        Action assertAllLightIsOffAction = object -> {
            if (object instanceof Light light) {
                assertFalse(light.isOn());
            }
        };
        smartHome.execute(assertAllLightIsOffAction);
    }

    @Test
    public void turnLightOnTest() {
        remoteControl.onButtonPressed("3", rcId);

        Action assertAllLightIsOnAction = object -> {
            if (object instanceof Light light) {
                assertTrue(light.isOn());
            }
        };
        smartHome.execute(assertAllLightIsOnAction);
    }

    @Test
    public void unbindButtonTest() {
        // Nothing should happen
        remoteControl.onButtonPressed("4", rcId);
    }
}
