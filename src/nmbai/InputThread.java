package nmbai;

import java.io.*;
import java.util.Scanner;

public class InputThread implements Runnable {
    private final BufferedReader input;
    private boolean running;
    private final Scanner scanner;
    public InputThread(InputStream inputStream, Scanner scanner) {
        input = new BufferedReader(new InputStreamReader(inputStream));
        this.scanner = scanner;
        running = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                System.out.println(input.readLine());
            } catch (IOException ex) {
                if (running) {
                    scanner.close();
                    System.out.println("An IOException occurred. Connection to server was closed.");
                    running = false;
                }
            }

        }

    }

    public void stop() {
        running = false;
    }
}
