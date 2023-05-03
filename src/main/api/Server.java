package main.api;

import main.mainwindow.MainWindow;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    public static int clientCount = 0;
    private static final int PORT = 8080;
    private Thread serverThread;
    private ServerSocket serverSocket;
    private MainWindow mw;

    public Server(MainWindow mw) {
        this.mw = mw;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server created");

        serverThread = new Thread(this);
        serverThread.start();
    }

    public static void incrementClientCount() {
        clientCount++;
        System.out.println("Client count: " + clientCount);
    }

    public static void decrementClientCount() {
        clientCount--;
        System.out.println("Client count: " + clientCount);
    }

    /**
     * On connection create a new ClientManager to handle the client
     */
    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Server listening for client...");
                Socket clientSocket = serverSocket.accept();
                new ClientManager(clientSocket, mw);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
