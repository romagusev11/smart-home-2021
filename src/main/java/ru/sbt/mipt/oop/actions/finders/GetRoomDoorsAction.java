package ru.sbt.mipt.oop.actions.finders;

import ru.sbt.mipt.oop.actions.Action;
import ru.sbt.mipt.oop.actions.Actionable;
import ru.sbt.mipt.oop.objects.Door;
import ru.sbt.mipt.oop.objects.Room;

import java.util.ArrayList;
import java.util.List;

public class GetRoomDoorsAction implements Action {
    private final List<String> result = new ArrayList<>();
    private final String roomName;
    private String currentRoom = "";

    public GetRoomDoorsAction(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public void execute(Actionable object) {
        if (object instanceof Room) {
            currentRoom = ((Room) object).getName();
        }
        if (object instanceof Door) {
            Door door = (Door) object;
            if (currentRoom.equals(roomName)) {
                result.add(door.getId());
            }
        }
    }

    public List<String> payload() {
        return result;
    }
}
