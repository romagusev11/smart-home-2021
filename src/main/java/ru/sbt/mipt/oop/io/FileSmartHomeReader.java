package ru.sbt.mipt.oop.io;

import com.google.gson.Gson;
import ru.sbt.mipt.oop.objects.SmartHome;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileSmartHomeReader implements SmartHomeReader {
    private final String filename;

    public FileSmartHomeReader(String filename) {
        this.filename = filename;
    }

    @Override
    public SmartHome readSmartHome() {
        Gson gson = new Gson();
        try {
            String json = new String(Files.readAllBytes(Paths.get(filename)));
            return gson.fromJson(json, SmartHome.class);
        } catch (IOException e) {
            throw new CantLoadSmartHomeException("Can't load SmartHome from " + filename + "\n" + e.getMessage());
        }

    }
}
