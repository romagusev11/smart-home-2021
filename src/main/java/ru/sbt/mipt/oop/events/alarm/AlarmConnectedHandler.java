package ru.sbt.mipt.oop.events.alarm;

import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.alarm.AlarmReactor;
import ru.sbt.mipt.oop.alarm.AlertMessageSender;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.EventHandler;

public class AlarmConnectedHandler implements EventHandler, AlarmReactor {
    private final EventHandler handler;
    private final Alarm alarm;
    private final AlertMessageSender sender;
    private Event event;

    public AlarmConnectedHandler(Alarm alarm, EventHandler handler, AlertMessageSender sender) {
        this.alarm = alarm;
        this.handler = handler;
        this.sender = sender;
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
        sender.sendMessage(event.toString());
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
