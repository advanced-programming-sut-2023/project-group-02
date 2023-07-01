package client;

import client.view.enums.LoginMenuMessages;
import com.google.gson.Gson;
import controllers.LoginMenuController;
import server.Packet;
import server.PacketType;

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

    public String requestLogin(String username, String password) {
        try {
            dataOutputStream.writeUTF(new Packet(PacketType.LOGIN, username, password).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            String input = dataInputStream.readUTF();
            Packet packet = new Gson().fromJson(input, Packet.class);
            String message = packet.data.get(0);
            return message;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
