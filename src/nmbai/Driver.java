package nmbai;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class Driver {
    private static final String hostName = "localhost";
    private final int port;
    private final Scanner scanner = new Scanner(System.in);
    private boolean running = false;
    private Socket socket;
    private PrintWriter socketOut;
    private InputThread inputThread;


    public Driver(int port) {
        this.port = port;
    }

    public void start() {
        try {
            socket = new Socket(hostName, this.port);
            socketOut = new PrintWriter(socket.getOutputStream(), true);
            inputThread = new InputThread(socket.getInputStream(), scanner);
            running = true;
        } catch (ConnectException ex) {
            running = false;
            System.out.printf("Could not connect to port %d; server may not be running.%n", this.port);
        } catch (IOException ex) {
            running = false;
            System.out.println("An IOException occurred.");
        }
        if (running) {
            Thread thread = new Thread(inputThread);
            thread.start();
        }
    }

    public void run() {
        while (running) {
            String userInput = scanner.nextLine();
            socketOut.println(userInput);
            if (userInput.equals("exit")) {
                stop();
            }
        }
        System.out.println("Stopping");
    }

    public void stop() {
        running = false;
        if (inputThread != null) {
            inputThread.stop();
        }
        scanner.close();
        try {
            socketOut.flush();
            socket.close();
        } catch (IOException e) {
            if (running) {
                e.printStackTrace();
            }
        } catch (NullPointerException ignored) {
            //NullPointerException thrown when client fails to connect to server
        }
    }
}
