package com.lg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        runPythonScript();

        SpringApplication.run(Main.class, args);
    }

    public static void runPythonScript() {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("python", "main.py"); // lub "python3" jeśli wymagane

        // Ustaw katalog roboczy na główny folder projektu (RestServer)
        builder.directory(new File(System.getProperty("user.dir")));

        try {
            Process process = builder.start();

            // Wyświetlanie wyjścia i błędów z Pythona w konsoli
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println("[PYTHON] " + line);
            }
            while ((line = errReader.readLine()) != null) {
                System.err.println("[PYTHON ERROR] " + line);
            }

            int exitCode = process.waitFor();
            System.out.println("Python script exited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
