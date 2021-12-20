package ru.sbt.mipt.oop.actions.finders;

import ru.sbt.mipt.oop.actions.Action;
import ru.sbt.mipt.oop.actions.Actionable;
import ru.sbt.mipt.oop.objects.Light;
import ru.sbt.mipt.oop.objects.Room;

import java.util.ArrayList;
import java.util.List;

public class GetRoomLightsAction implements Action {
    private final List<String> result = new ArrayList<>();
    private final String roomName;
    private String currentRoom = "";

    public GetRoomLightsAction(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public void execute(Actionable object) {
        if (object instanceof Room) {
            currentRoom = ((Room) object).getName();
        }
        if (object instanceof Light) {
            Light light = (Light) object;
            if (currentRoom.equals(roomName)) {
                result.add(light.getId());
            }
        }
    }

    public List<String> payload() {
        return result;
    }
}
