package client;

import client.view.Main;
import client.view.enums.LoginMenuMessages;
import client.view.lobby.LobbyMenu;
import com.google.gson.Gson;

import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import controllers.database.Database;
import javafx.scene.layout.Pane;
import models.Map;
import models.SecurityQuestion;
import models.User;
import models.UserCredentials;
import server.ChatDatabase;
import server.Packet;
import server.PacketType;
import server.chat.Chat;
import server.logic.Lobby;
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
        try {
            String token = Database.readRaw("token");
            if (token == null) {
                return;
            }
            dataOutputStream.writeUTF(new Packet(PacketType.LOGIN_WITH_TOKEN, token).toJson());
            dataOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        readFromServer(); // no-op
    }

    public String requestLogin(String username, String password) {
        try {
            dataOutputStream.writeUTF(new Packet(PacketType.LOGIN, username, password).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Packet packet = readFromServer();
        String message = packet.data.get(0);
        if (message.equals(LoginMenuMessages.LOGIN_SUCCESSFUL.getMessage())) {
            Database.write("token", UserCredentials.of(getLoggedInUser()), UserCredentials.class);
        }
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
            Database.write("token", UserCredentials.of(getLoggedInUser()), UserCredentials.class);
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
            Database.delete("token");
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
            dataOutputStream.writeUTF(new Packet(PacketType.SEND_FRIEND_REQUEST, userToSearch.getUsername()).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void acceptFriendRequest(String username, String username1) {
        try {
            dataOutputStream.writeUTF(new Packet(PacketType.ACCEPT_FRIEND, username, username1).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Packet packet = readFromServer();
        Graphics.showMessagePopup(packet.data.get(0));
    }

    public void rejectFriendRequest(String username, String username1) {
        try {
            dataOutputStream.writeUTF(new Packet(PacketType.REJECT_FRIEND, username, username1).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Packet packet = readFromServer();
        Graphics.showMessagePopup(packet.data.get(0));
    }

    public User[] getScoreboard() {
        try {
            dataOutputStream.writeUTF(new Packet(PacketType.GET_SCOREBOARD).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Packet packet = readFromServer();
        User[] users = new Gson().fromJson(packet.data.get(0), User[].class);
        return users;
    }

    public Lobby MakeLobby(String mapWidth, String mapHeight, String numberOfPlayers, String numberOfTurns, boolean isPublic) {
        String isPublicString = isPublic ? "public" : "private";
        try {
            dataOutputStream.writeUTF(new Packet(PacketType.MAKE_LOBBY, mapWidth, mapHeight, numberOfTurns, numberOfPlayers, isPublicString).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Packet packet = readFromServer();
        Lobby lobby = new Gson().fromJson(packet.data.get(0), Lobby.class);
        if (lobby == null) {
            return null;
        }
        return lobby;
    }

    public ArrayList<Chat> getChats() {
        try {
            dataOutputStream.writeUTF(new Packet(PacketType.GET_CHATS).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Packet packet = readFromServer();
        Chat[] chats = new Gson().fromJson(packet.data.get(0), Chat[].class);
        ArrayList<Chat> chatsArrayList = new ArrayList<>();
        for (Chat chat : chats) {
            chatsArrayList.add(chat);
        }
        return chatsArrayList;
    }

    public void makePrivateChatWith(User user) {
        try {
            dataOutputStream.writeUTF(new Packet(PacketType.MAKE_PRIVATE_CHAT, user.getUsername()).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeGroupChat(String name, ArrayList<User> users) {
        ArrayList<String> data = new ArrayList<>();
        data.add(name);
        for (User user : users) {
            data.add(user.getUsername());
        }
        try {
            dataOutputStream.writeUTF(new Packet(PacketType.MAKE_GROUP_CHAT, data).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(int chatId, String text) {
        try {
            dataOutputStream.writeUTF(new Packet(PacketType.SEND_MESSAGE, String.valueOf(chatId), text).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Lobby getLobbyWithID(String lobbyID) {
        try {
            dataOutputStream.writeUTF(new Packet(PacketType.GET_LOBBY, lobbyID).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Packet packet = readFromServer();
        Lobby lobby = new Gson().fromJson(packet.data.get(0), Lobby.class);
        return lobby;
    }

    public ArrayList<Lobby> getAvailableLobbies() {
        try {
            dataOutputStream.writeUTF(new Packet(PacketType.GET_AVAILABLE_LOBBIES).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Packet packet = readFromServer();
        ArrayList<Lobby> lobbies = new Gson().fromJson(packet.data.get(0), new TypeToken<ArrayList<Lobby>>() {
        }.getType());
        return lobbies;
    }

    public void joinLobby(String id) {
        try {
            dataOutputStream.writeUTF(new Packet(PacketType.JOIN_LOBBY, id).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Packet packet = readFromServer();
        if (packet.packetType == PacketType.JOIN_LOBBY_FAIL) {
            Graphics.showMessagePopup(packet.data.get(0));
            return;
        }
        Lobby lobby = new Gson().fromJson(packet.data.get(0), Lobby.class);
        Main.setScene(new LobbyMenu(lobby).getPane());
    }

    public void quitLobby(String id) {
        try {
            dataOutputStream.writeUTF(new Packet(PacketType.QUIT_LOBBY, id).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMap(Map map) {
        try {
            dataOutputStream.writeUTF(new Packet(PacketType.SEND_MAP,new Gson().toJson(map)).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Map> getMaps() {
        try {
            dataOutputStream.writeUTF(new Packet(PacketType.GET_MAPS).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Packet packet = readFromServer();
        try {
            Map[] maps = new Gson().fromJson(packet.data.get(0), Map[].class);
            ArrayList<Map> mapsArrayList = new ArrayList<>();
            for (Map map : maps) {
                mapsArrayList.add(map);
            }
            return mapsArrayList;
        } catch (JsonIOException e) {
            return null;
        }
    }
}
