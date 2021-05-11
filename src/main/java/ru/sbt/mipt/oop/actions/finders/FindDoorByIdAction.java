package ru.sbt.mipt.oop.actions.finders;

import ru.sbt.mipt.oop.actions.Action;
import ru.sbt.mipt.oop.actions.Actionable;
import ru.sbt.mipt.oop.objects.Door;

public class FindDoorByIdAction implements Action {
    private final String doorId;
    private Door result = null;

    public FindDoorByIdAction(String doorId) {
        this.doorId = doorId;
    }

    @Override
    public void execute(Actionable object) {
        if (object instanceof Door door) {
            if (door.getId().equals(doorId)) {
                result = door;
            }
        }
    }

    public Door payload() {
        return result;
    }
}
