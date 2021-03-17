package ru.sbt.mipt.oop.actions;

import ru.sbt.mipt.oop.objects.Door;

public class OpenDoorAction implements Action {
    private final String doorId;

    public OpenDoorAction(String doorId) {
        this.doorId = doorId;
    }

    @Override
    public void execute(Actionable object) {
        if (object instanceof Door) {
            Door door = (Door) object;
            if (door.getId().equals(doorId)) {
                door.setOpen(true);
            }
        }
    }
}
