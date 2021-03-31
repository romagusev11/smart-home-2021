package ru.sbt.mipt.oop.events.alarm;

import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.alarm.AlarmReactor;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.EventHandler;

public class AlarmConnectedHandler implements EventHandler, AlarmReactor {
    private final EventHandler handler;
    private final Alarm alarm;
    private Event event;

    public AlarmConnectedHandler(Alarm alarm, EventHandler handler) {
        this.alarm = alarm;
        this.handler = handler;
    }

    @Override
    public void handleEvent(Event event) {
        this.event = event;
        alarm.react(this);
    }

    @Override
    public void onAlarmActivatedState() {
        if (event instanceof AlarmEvent) {
            return;
        }

        alarm.setOnAlert();
        alarm.sendMessage(event.toString());
    }

    @Override
    public void onAlarmDeactivatedState() {
        handler.handleEvent(event);
    }

    @Override
    public void onAlarmOnAlertState() {
        // Do nothing
    }
}
