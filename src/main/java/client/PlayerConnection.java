package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PlayerConnection {
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;

    public PlayerConnection(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public void tryToAuthenticate() {
        //TODO wait for server to check if this ip has made an account and auto login.
    }
}
