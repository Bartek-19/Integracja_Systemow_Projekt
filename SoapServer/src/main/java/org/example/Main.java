package org.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static boolean isDockerPath(){
        Path dockerPath = Paths.get("/app", "DataFiles");
        return (Files.exists(dockerPath) && Files.isDirectory(dockerPath));
    }

    public static void main(String[] args) {
        Publisher publisher = new Publisher();
        publisher.start();
    }
}