package ru.sbt.mipt.oop.actions;

import ru.sbt.mipt.oop.objects.Light;

public class FindLightByIdAction implements Action{
    private final String lightId;
    private Light result = null;

    public FindLightByIdAction(String lightId) {
        this.lightId = lightId;
    }

    @Override
    public void execute(Actionable object) {
        if (object instanceof Light) {
            Light light = (Light) object;
            if (light.getId().equals(lightId)) {
                result = light;
            }
        }
    }

    public Light payload() {
        return result;
    }
}
