package server;

import com.google.gson.Gson;
import controllers.LoginMenuController;
import models.User;
import server.logic.Login;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection extends Thread {
    private Socket socket;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    private final CheckUserAvailability checkUserAvailability;
    private User currentLoggedInUser;

    public Connection(Socket socket) throws IOException {
        System.out.println("New connection form: " + socket.getInetAddress() + " : " + socket.getPort());
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.checkUserAvailability = new CheckUserAvailability(dataOutputStream, this);
        checkUserAvailability.start();
    }

    @Override
    public synchronized void run() {
        try {
            //TODO check if this ip has made an account and auto login.
            String data = dataInputStream.readUTF();
            Packet packet = new Gson().fromJson(data, Packet.class);
            if (packet.packetType == PacketType.LOGIN) {
                dataOutputStream.writeUTF(new Packet
                    (PacketType.LOGIN, userLogin(packet.data.get(0), packet.data.get(1))).toJson());
            } else if (packet.packetType == PacketType.GET_LOGGED_IN_USER) {
                dataOutputStream.writeUTF(new Packet(PacketType.GET_LOGGED_IN_USER, new Gson().toJson(currentLoggedInUser)).toJson());
            }
        } catch (IOException e) {
            checkUserAvailability.userDisconnected();
        }
    }

    public void userDisconnected() {
        System.out.println("Player disconnected: " + socket.getInetAddress() + " : " + socket.getPort());
        //TODO do anything necessary when user becomes offline
    }

    private synchronized String userLogin(String username, String password) {
        String result = Login.login(username, password, this).getMessage();
        return result;
    }

    public User getCurrentLoggedInUser() {
        return currentLoggedInUser;
    }

    public void setCurrentLoggedInUser(User currentLoggedInUser) {
        this.currentLoggedInUser = currentLoggedInUser;
    }
}
