package main.api;

import main.mainwindow.MainWindow;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Connector implements Runnable {
    private MainWindow mw;
    private Thread thread;
    private ServerSocket listeningSocket;
    private int listeningPort = 8080;
    public Connector(MainWindow mw) {
        this.mw = mw;
        try {
            listeningSocket = new ServerSocket(listeningPort, 0, InetAddress.getByName("localhost"));
            System.out.println("Listening on port " + listeningPort);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thread = new Thread(this);
    }

    @Override
    public void run() {

    }
}
