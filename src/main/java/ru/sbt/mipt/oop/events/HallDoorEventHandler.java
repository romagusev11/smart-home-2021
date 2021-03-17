package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.actions.IsDoorInRoomAction;
import ru.sbt.mipt.oop.actions.TurnOffLightInHomeAction;
import ru.sbt.mipt.oop.commands.CommandSender;
import ru.sbt.mipt.oop.objects.SmartHome;

import static ru.sbt.mipt.oop.events.SensorEventType.DOOR_CLOSED;

public class HallDoorEventHandler implements EventHandler{
    private final CommandSender sender;
    private final SmartHome smartHome;

    public HallDoorEventHandler(SmartHome smartHome, CommandSender sender) {
        this.smartHome = smartHome;
        this.sender = sender;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        // если мы получили событие о закрытие двери в холле - это значит, что была закрыта входная дверь.
        // в этом случае мы хотим автоматически выключить свет во всем доме (это же умный дом!)
        if (event.getType() == DOOR_CLOSED) {
            IsDoorInRoomAction action = new IsDoorInRoomAction(event.getObjectId(), "hall");
            smartHome.execute(action);
            if (action.payload()) {
                smartHome.execute(new TurnOffLightInHomeAction(sender));
            }
        }
    }
}
