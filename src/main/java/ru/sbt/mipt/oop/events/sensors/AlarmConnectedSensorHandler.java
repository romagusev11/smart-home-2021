package ru.sbt.mipt.oop.events.sensors;

import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.alarm.AlarmReactor;
import ru.sbt.mipt.oop.io.Logger;

public class AlarmConnectedSensorHandler implements SensorEventHandler, AlarmReactor {
    private final SensorEventHandler handler;
    private final Alarm alarm;
    private final Logger logger;
    private SensorEvent event;

    public AlarmConnectedSensorHandler(Alarm alarm, SensorEventHandler handler, Logger logger) {
        this.alarm = alarm;
        this.handler = handler;
        this.logger = logger;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        this.event = event;
        alarm.react(this);
    }


    @Override
    public void onAlarmActivatedState() {
        alarm.setOnAlert();
        logger.log("Sending sms... Alarm: " + event.toString());
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
