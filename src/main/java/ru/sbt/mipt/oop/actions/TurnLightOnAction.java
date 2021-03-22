package ru.sbt.mipt.oop.actions;

import ru.sbt.mipt.oop.objects.Light;

public class TurnLightOnAction implements Action {
    private final String lightId;
    private boolean result = false;

    public TurnLightOnAction(String lightId) {
        this.lightId = lightId;
    }

    @Override
    public void execute(Actionable object) {
        if (object instanceof Light) {
            Light light = (Light) object;
            if (light.getId().equals(lightId)) {
                light.setOn(true);
                result = true;
            }
        }
    }

    public boolean payload() {
        return result;
    }
}
