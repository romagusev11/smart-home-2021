package ru.sbt.mipt.oop.alarm;

import ru.sbt.mipt.oop.alarm.state.ActivatedState;
import ru.sbt.mipt.oop.alarm.state.OnAlertState;
import ru.sbt.mipt.oop.io.Logger;
import ru.sbt.mipt.oop.sensors.SensorEvent;
import ru.sbt.mipt.oop.sensors.SensorEventHandler;

public class AlarmConnectedSensorHandler implements SensorEventHandler {
    private final SensorEventHandler handler;
    private final Alarm alarm;
    private final Logger logger;

    public AlarmConnectedSensorHandler(Alarm alarm, SensorEventHandler handler, Logger logger) {
        this.alarm = alarm;
        this.handler = handler;
        this.logger = logger;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        if (alarm.getState() instanceof ActivatedState) {
            alarm.setOnAlert();
            logger.log("Sending sms... Alarm: " + event.toString());
        } else if (alarm.getState() instanceof OnAlertState){
            // Do nothing
        } else {
            handler.handleEvent(event);
        }
    }
}
