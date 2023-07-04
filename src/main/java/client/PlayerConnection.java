package client;

import client.view.enums.LoginMenuMessages;
import com.google.gson.Gson;
import controllers.LoginMenuController;
import controllers.SignUpMenuController;
import javafx.scene.layout.Pane;
import models.SecurityQuestion;
import models.User;
import server.ChatDatabase;
import server.Database;
import server.Packet;
import server.PacketType;
import server.chat.Chat;
import utils.Graphics;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

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
        Packet packet = readFromServer();
        String message = packet.data.get(0);
        return message;
    }

    public String initSignUp(String username, String password, String nickname, String email, String slogan) {
        try {
            dataOutputStream.writeUTF(new Packet(PacketType.SIGNUP, username, password, nickname, email, slogan).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Packet packet = readFromServer();
        String message = packet.data.get(0);
        return message;
    }

    public void setSecurityQuestion(SecurityQuestion securityQuestion, String answer) {
        try {
            dataOutputStream.writeUTF(new Packet(PacketType.FINALIZE_SIGNUP, securityQuestion.fullSentence, answer).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Packet readFromServer() {
        try {
            Packet packet;
            while (true) {
                String input = dataInputStream.readUTF();
                Packet tempPacket = new Gson().fromJson(input, Packet.class);
                if (tempPacket.packetType != PacketType.CHECK_ONLINE) {
                    packet = tempPacket;
                    break;
                }
            }
            return packet;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized User getLoggedInUser() {
        try {
            dataOutputStream.writeUTF(new Packet(PacketType.GET_LOGGED_IN_USER, new ArrayList<>()).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Packet packet = readFromServer();
        User user = new Gson().fromJson(packet.data.get(0), User.class);
        return user;
    }

    public ArrayList<Chat> getPlayerChats() {
        //TODO complete this method
        return ChatDatabase.getChatsOfUser(getLoggedInUser());
    }

    public void logout() {
        try {
            dataOutputStream.writeUTF(new Packet(PacketType.LOGOUT, new ArrayList<>()).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void searchPlayer(Pane currentPane, String username) {
        User currentUser = getLoggedInUser();
        User userToSearch = getUserWithUsername(username);
        if (userToSearch == null) {
            Graphics.showMessagePopup("Such user does not exits");
            return;
        }
        Graphics.showProfile(currentPane, currentUser, userToSearch);
    }

    private User getUserWithUsername(String username) {
        try {
            dataOutputStream.writeUTF(new Packet(PacketType.FIND_USER, username).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Packet packet = readFromServer();
        if (packet.data.get(0).equals(""))
            return null;
        User user = new Gson().fromJson(packet.data.get(0), User.class);
        return user;
    }

    public void sendFriendRequest(User userToSearch) {
        try {
            dataOutputStream.writeUTF(new Packet(PacketType.SEND_FRIEND_REQUEST, new Gson().toJson(userToSearch)).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
