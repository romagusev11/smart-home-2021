package ru.sbt.mipt.oop.io;

public class ConsoleLogger implements Logger {
    @Override
    public void log(String str) {
        System.out.println(str);
    }
}
