package main.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.mainwindow.MainWindow;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import static main.api.Commands.*;

public class Server implements Runnable {
    public static int clientCount = 0;
    private static final int PORT = 8080;
    private DataOutputStream streamOut;
    private Socket clientSocket;
    private DataInputStream streamIn;
    private Thread serverThread;
    private ServerSocket serverSocket;
    private ObjectMapper objectMapper;
    private MainWindow mw;

    public Server(MainWindow mw) {
        this.mw = mw;
        objectMapper = new ObjectMapper();
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
