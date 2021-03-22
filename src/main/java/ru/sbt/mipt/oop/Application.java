package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.events.EventLoop;

public class Application {
    private final EventLoop eventLoop;

    public Application(EventLoop eventLoop) {
        this.eventLoop = eventLoop;
    }

    public void run() {
        // начинаем цикл обработки событий
        eventLoop.handleEvents();
    }
}
