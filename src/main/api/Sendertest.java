package main.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Sendertest {
    public Sendertest(String address, int port) {
        Socket socket = null;
        try {
            socket = new Socket(address, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            socket.getOutputStream().write("{\"command:\" \"get_deck_names\"}".getBytes());
            System.out.println(socket.getInputStream().read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
