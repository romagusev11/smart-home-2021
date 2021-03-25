package ru.sbt.mipt.oop.alarm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.alarm.state.ActivatedState;
import ru.sbt.mipt.oop.alarm.state.DeactivatedState;
import ru.sbt.mipt.oop.alarm.state.OnAlertState;
import ru.sbt.mipt.oop.events.alarm.AlarmEvent;
import ru.sbt.mipt.oop.events.alarm.AlarmEventHandler;
import ru.sbt.mipt.oop.events.alarm.AlarmEventType;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertEquals(DeactivatedState.class, alarm.getState().getClass());
    }

    @Test
    public void activateAlarmTest() {
        String code = "111";
        AlarmEvent alarmEvent = new AlarmEvent(AlarmEventType.ALARM_ACTIVATE, code);

        alarmEventHandler.handleEvent(alarmEvent);

        assertEquals(ActivatedState.class, alarm.getState().getClass());
        assertTrue(alarm.isCorrectCode(code));
    }

    @Test
    public void deactivateAlarmTest() {
        String code = "111";
        alarm.activate(code);
        assertEquals(ActivatedState.class, alarm.getState().getClass());

        AlarmEvent alarmEvent = new AlarmEvent(AlarmEventType.ALARM_DEACTIVATE, code);
        alarmEventHandler.handleEvent(alarmEvent);

        assertEquals(DeactivatedState.class, alarm.getState().getClass());
    }

    @Test
    public void deactivateAlarmWrongCodeTest() {
        alarm.activate("111");
        assertEquals(ActivatedState.class, alarm.getState().getClass());

        AlarmEvent alarmEvent = new AlarmEvent(AlarmEventType.ALARM_DEACTIVATE, "121");
        alarmEventHandler.handleEvent(alarmEvent);

        assertEquals(OnAlertState.class, alarm.getState().getClass());
    }

    @Test
    public void deactivateAlarmOnAlert() {
        alarm.activate("111");
        alarm.setOnAlert();
        assertEquals(OnAlertState.class, alarm.getState().getClass());

        AlarmEvent alarmEvent = new AlarmEvent(AlarmEventType.ALARM_DEACTIVATE, "111");
        alarmEventHandler.handleEvent(alarmEvent);

        assertEquals(DeactivatedState.class, alarm.getState().getClass());
    }
}
