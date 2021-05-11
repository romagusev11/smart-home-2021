package ru.sbt.mipt.oop.sensor_commands;

public class ConsoleCommandSender implements CommandSender {
    @Override
    public void sendCommand(SensorCommand command) {
        System.out.println("Pretend we're sending command " + command);
    }
}
