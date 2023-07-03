package server;

import client.view.enums.SignUpMenuMessages;
import com.google.gson.Gson;
import controllers.LoginMenuController;
import controllers.SignUpMenuController;
import models.SecurityQuestion;
import models.User;
import server.logic.Login;
import server.logic.SignUp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

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
            } else if (packet.packetType == PacketType.SIGNUP) {
                dataOutputStream.writeUTF(new Packet(PacketType.SIGNUP, initSignup(packet.data)).toJson());
            } else if (packet.packetType == PacketType.FINALIZE_SIGNUP) {
                finalizeSignup(packet.data.get(0), packet.data.get(1));
            }
        } catch (IOException e) {
            checkUserAvailability.userDisconnected();
        }
    }

    public void userDisconnected() {
        System.out.println("Player disconnected: " + socket.getInetAddress() + " : " + socket.getPort());
        //TODO do anything necessary when user becomes offline
    }

    private synchronized String initSignup(ArrayList<String> data) {
        // if two users attempt to sign up simultaneously we're screwed
        String message = SignUp.initiateSignup(data.get(0), data.get(1), data.get(1), data.get(2), data.get(3), data.get(4)).getMessage();
        return message;
    }

    private synchronized void finalizeSignup(String securityQuestion, String answer) {
        SignUpMenuController.setSecurityQuestion(SecurityQuestion.getSecurityQuestion(securityQuestion), answer, this);
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
