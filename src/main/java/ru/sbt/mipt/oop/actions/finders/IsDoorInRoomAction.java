package ru.sbt.mipt.oop.actions.finders;

import ru.sbt.mipt.oop.actions.Action;
import ru.sbt.mipt.oop.actions.Actionable;
import ru.sbt.mipt.oop.objects.Door;
import ru.sbt.mipt.oop.objects.Room;

public class IsDoorInRoomAction implements Action {
    private final String doorId;
    private final String roomName;
    private String currentRoom = "";
    private boolean result = false;

    public IsDoorInRoomAction(String doorId, String roomName) {
        this.doorId = doorId;
        this.roomName = roomName;
    }

    @Override
    public void execute(Actionable object) {
        if (object instanceof Room) {
            currentRoom = ((Room) object).getName();
        }
        if (object instanceof Door) {
            Door door = (Door) object;
            if (door.getId().equals(doorId) && currentRoom.equals(roomName)) {
                result = true;
            }
        }
    }

    public boolean payload() {
        return result;
    }
}
