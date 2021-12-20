package ru.sbt.mipt.oop.commands;

public class CantFindFrontDoorException extends RuntimeException {
    public CantFindFrontDoorException(String message) {
        super(message);
    }
}
