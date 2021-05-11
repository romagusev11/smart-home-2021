package ru.sbt.mipt.oop.actions.recievers;

import ru.sbt.mipt.oop.actions.Action;
import ru.sbt.mipt.oop.actions.Actionable;
import ru.sbt.mipt.oop.objects.Door;

public class OpenDoorAction implements Action {
    private final String doorId;
    private boolean result = false;

    public OpenDoorAction(String doorId) {
        this.doorId = doorId;
    }

    @Override
    public void execute(Actionable object) {
        if (object instanceof Door door) {
            if (door.getId().equals(doorId)) {
                door.setOpen(true);
                result = true;
            }
        }
    }

    public boolean payload() {
        return result;
    }
}
