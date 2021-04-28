package ru.sbt.mipt.oop.events.sensors;

import ru.sbt.mipt.oop.actions.finders.IsDoorInRoomAction;
import ru.sbt.mipt.oop.actions.senders.TurnOffLightInHomeAction;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.EventHandler;
import ru.sbt.mipt.oop.objects.SmartHome;
import ru.sbt.mipt.oop.sensor_commands.CommandSender;

import static ru.sbt.mipt.oop.events.sensors.SensorEventType.DOOR_CLOSED;

public class HallDoorEventHandler implements EventHandler {
    private final CommandSender sender;
    private final SmartHome smartHome;

    public HallDoorEventHandler(SmartHome smartHome, CommandSender sender) {
        this.smartHome = smartHome;
        this.sender = sender;
    }

    @Override
    public void handleEvent(Event event) {
        // если мы получили событие о закрытие двери в холле - это значит, что была закрыта входная дверь.
        // в этом случае мы хотим автоматически выключить свет во всем доме (это же умный дом!)
        if (event instanceof SensorEvent sensorEvent) {
            if (sensorEvent.getType() == DOOR_CLOSED) {
                IsDoorInRoomAction action = new IsDoorInRoomAction(sensorEvent.getObjectId(), "hall");
                smartHome.execute(action);
                if (action.payload()) {
                    smartHome.execute(new TurnOffLightInHomeAction(sender));
                }
            }
        }
    }
}
