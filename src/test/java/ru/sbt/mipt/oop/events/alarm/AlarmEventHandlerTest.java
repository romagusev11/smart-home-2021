package ru.sbt.mipt.oop.events.alarm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.events.Event;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlarmEventHandlerTest {
    private AlarmEventHandler alarmEventHandler;
    private Alarm alarm;

    @BeforeEach
    public void configureAlarm() {
        alarm = new Alarm();
        alarmEventHandler = new AlarmEventHandler(alarm);
    }

    @Test
    public void defaultAlarmIsDeactivatedTest() {
        assertTrue(alarm.isDeactivated());;
    }

    @Test
    public void wrongEventTypeTest() {
        Event event = new Event() {};
        alarmEventHandler.handleEvent(event);
    }

    @Test
    public void activateAlarmTest() {
        String code = "111";
        AlarmEvent alarmEvent = new AlarmEvent(AlarmEventType.ALARM_ACTIVATE, code);

        alarmEventHandler.handleEvent(alarmEvent);

        assertTrue(alarm.isActivated());
    }

    @Test
    public void deactivateAlarmTest() {
        String code = "111";
        alarm.activate(code);
        assertTrue(alarm.isActivated());

        AlarmEvent alarmEvent = new AlarmEvent(AlarmEventType.ALARM_DEACTIVATE, code);
        alarmEventHandler.handleEvent(alarmEvent);

        assertTrue(alarm.isDeactivated());
    }

    @Test
    public void deactivateAlarmWrongCodeTest() {
        alarm.activate("111");
        assertTrue(alarm.isActivated());

        AlarmEvent alarmEvent = new AlarmEvent(AlarmEventType.ALARM_DEACTIVATE, "121");
        alarmEventHandler.handleEvent(alarmEvent);

        assertTrue(alarm.isOnAlert());
    }

    @Test
    public void deactivateAlarmOnAlert() {
        alarm.activate("111");
        alarm.setOnAlert();
        assertTrue(alarm.isOnAlert());

        AlarmEvent alarmEvent = new AlarmEvent(AlarmEventType.ALARM_DEACTIVATE, "111");
        alarmEventHandler.handleEvent(alarmEvent);

        assertTrue(alarm.isDeactivated());
    }
}
