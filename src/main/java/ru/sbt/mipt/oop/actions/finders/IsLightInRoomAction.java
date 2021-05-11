package ru.sbt.mipt.oop.actions.finders;

import ru.sbt.mipt.oop.actions.Action;
import ru.sbt.mipt.oop.actions.Actionable;
import ru.sbt.mipt.oop.objects.Light;
import ru.sbt.mipt.oop.objects.Room;

public class IsLightInRoomAction implements Action {
    private final String lightId;
    private final String roomName;
    private String currentRoom = "";
    private boolean result = false;

    public IsLightInRoomAction(String lightId, String roomName) {
        this.lightId = lightId;
        this.roomName = roomName;
    }

    @Override
    public void execute(Actionable object) {
        if (object instanceof Room) {
            currentRoom = ((Room) object).getName();
        }
        if (object instanceof Light light) {
            if (light.getId().equals(lightId) && currentRoom.equals(roomName)) {
                result = true;
            }
        }
    }

    public boolean payload() {
        return result;
    }
}
