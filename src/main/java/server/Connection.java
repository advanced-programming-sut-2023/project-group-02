package server;

import client.view.enums.SignUpMenuMessages;
import com.google.gson.Gson;
import controllers.LoginMenuController;
import controllers.SignUpMenuController;
import models.SecurityQuestion;
import models.User;
import models.UserCredentials;
import server.logic.Lobby;
import server.chat.Chat;
import server.chat.ChatType;
import server.logic.Login;
import server.logic.SignUp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Connection extends Thread {
    private Socket socket;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    private final CheckUserAvailability checkUserAvailability;
    private User currentLoggedInUser;

    public Connection(Socket socket) throws IOException {
        System.out.println("New connection from: " + socket.getInetAddress() + " : " + socket.getPort());
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.checkUserAvailability = new CheckUserAvailability(dataOutputStream, this);
        checkUserAvailability.start();
    }

    @Override
    public synchronized void run() {
        try {
            while (true) {
                String data = dataInputStream.readUTF();
                Packet packet = new Gson().fromJson(data, Packet.class);

                switch (packet.packetType) {
                    case LOGIN_WITH_TOKEN -> {
                        dataOutputStream.writeUTF(
                            new Packet(PacketType.LOGIN_WITH_TOKEN, userLoginWithToken(packet.data.get(0)))
                                .toJson());
                    }
                    case LOGIN -> {
                        dataOutputStream.writeUTF(
                            new Packet(PacketType.LOGIN, userLogin(packet.data.get(0), packet.data.get(1)))
                                .toJson());
                    }
                    case GET_LOGGED_IN_USER -> {
                        dataOutputStream.writeUTF(
                            new Packet(PacketType.GET_LOGGED_IN_USER, new Gson().toJson(currentLoggedInUser))
                                .toJson());
                    }
                    case SIGNUP -> {
                        dataOutputStream.writeUTF(
                            new Packet(PacketType.SIGNUP, initSignup(packet.data)).toJson());
                    }
                    case FINALIZE_SIGNUP -> {
                        finalizeSignup(packet.data.get(0), packet.data.get(1));
                    }
                    case LOGOUT -> {
                        userLogout();
                    }
                    case FIND_USER -> {
                        dataOutputStream.writeUTF(findUser(packet.data.get(0)).toJson());
                    }
                    case SEND_FRIEND_REQUEST -> {
                        sendFriendRequest(packet.data.get(0));
                    }
                    case ACCEPT_FRIEND -> {
                        dataOutputStream.writeUTF(new Packet(PacketType.ACCEPT_FRIEND,
                            acceptFriendRequest(ServerUserController.findUserWithUsername(packet.data.get(0)),
                                ServerUserController.findUserWithUsername(packet.data.get(1))))
                            .toJson());
                    }
                    case REJECT_FRIEND -> {
                        dataOutputStream.writeUTF(new Packet(PacketType.REJECT_FRIEND,
                            rejectFriendRequest(ServerUserController.findUserWithUsername(packet.data.get(0)),
                                ServerUserController.findUserWithUsername(packet.data.get(1))))
                            .toJson());
                    }
                    case GET_SCOREBOARD -> {
                        dataOutputStream.writeUTF(getScoreboard());
                    }
                    case MAKE_LOBBY -> {
                        dataOutputStream.writeUTF(makeLobbyPacket(packet).toJson());
                    }
                    case GET_CHATS -> {
                        dataOutputStream.writeUTF(getChats());
                    }
                    case MAKE_PRIVATE_CHAT -> {
                        makePrivateChatWith(packet.data.get(0));
                    }
                    case MAKE_GROUP_CHAT -> {
                        makeGroupChat(packet.data.get(0), packet.data.subList(1, packet.data.size() - 1));
                    }
                    case SEND_MESSAGE -> {
                        sendMessage(Integer.parseInt(packet.data.get(0)), packet.data.get(1));
                    }
                    case GET_LOBBY -> {
                        dataOutputStream.writeUTF(getLobby(packet.data.get(0)).toJson());
                    }
                    case GET_AVAILABLE_LOBBIES -> {
                        dataOutputStream.writeUTF(getAvailableLobbies().toJson());
                    }
                    case JOIN_LOBBY -> {
                        dataOutputStream.writeUTF(joinLobby(packet.data.get(0)).toJson());
                    }
                    case QUIT_LOBBY -> {
                        quitLobby(packet.data.get(0));
                    }
                }
            }
        } catch (IOException e) {
            checkUserAvailability.userDisconnected();
        }
    }

    private void quitLobby(String id) {
        Lobby lobby = Lobby.getLobbyWithID(id);
        lobby.getMembers().remove(currentLoggedInUser);
        if (lobby.getMembers().size() == 0)
            Lobby.removeLobby(lobby);
    }

    private synchronized Packet joinLobby(String id) {
        Lobby lobby = Lobby.getLobbyWithID(id);
        if (lobby.getMembers().size() >= lobby.getNumberOfPlayers())
            return new Packet(PacketType.JOIN_LOBBY_FAIL, "lobby is full");
        if (!lobby.isPublic())
            return new Packet(PacketType.JOIN_LOBBY_FAIL, "lobby became private");
        if (lobby.getMembers().contains(currentLoggedInUser))
            return new Packet(PacketType.JOIN_LOBBY_FAIL, "you are already in this lobby");
        lobby.addMember(currentLoggedInUser);
        return new Packet(PacketType.JOIN_LOBBY_SUCCESS, new Gson().toJson(lobby));
    }

    private Packet getAvailableLobbies() {
        ArrayList<Lobby> result = new ArrayList<>();
        for (Lobby lobby : Lobby.getLobbies()) {
            if (lobby.getMembers().size() < lobby.getNumberOfPlayers() && lobby.isPublic()) {
                result.add(lobby);
            }
        }
        return new Packet(PacketType.GET_AVAILABLE_LOBBIES, new Gson().toJson(result));
    }

    private Packet getLobby(String lobbyID) {
        Lobby lobby = Lobby.getLobbyWithID(lobbyID);
        return new Packet(PacketType.GET_LOBBY, new Gson().toJson(lobby));
    }

    private Packet makeLobbyPacket(Packet packet) {
        boolean isPublic = packet.data.get(4).equals("public");
        Lobby lobby = Lobby.makeLobby(currentLoggedInUser, Integer.parseInt(packet.data.get(0)), Integer.parseInt(packet.data.get(1)),
            Integer.parseInt(packet.data.get(2)), Integer.parseInt(packet.data.get(3)), isPublic);
        return new Packet(PacketType.MAKE_LOBBY, new Gson().toJson(lobby));
    }

    private String rejectFriendRequest(User accepter, User requester) {
        accepter.getReceivedFriendRequests().remove(requester);
        requester.getReceivedFriendRequests().remove(accepter);
        return "Friend request rejected successfully";
    }

    private String acceptFriendRequest(User accepter, User requester) {
        if (accepter.getFriends().size() < 100 && requester.getFriends().size() < 100) {
            requester.addFriend(accepter);
            accepter.addFriend(requester);
            accepter.getReceivedFriendRequests().remove(requester);
            requester.getReceivedFriendRequests().remove(accepter);
            return "Friend request accepted successfully";
        }
        return "You have reached the maximum number of friends";
    }

    private void sendFriendRequest(String username) {
        User user = ServerUserController.findUserWithUsername(username);
        if (!user.getReceivedFriendRequests().contains(currentLoggedInUser))
            user.addReceivedFriendRequest(currentLoggedInUser);
    }

    private Packet findUser(String username) {
        User user = ServerUserController.findUserWithUsername(username);
        if (user == null)
            return new Packet(PacketType.FIND_USER, "");
        return new Packet(PacketType.FIND_USER, new Gson().toJson(user));
    }

    private void userLogout() {
        for (Lobby lobby : Lobby.getLobbies()) {
            if (lobby.getMembers().contains(currentLoggedInUser)) {
                lobby.getMembers().remove(currentLoggedInUser);
                if (lobby.getMembers().size() == 0)
                    Lobby.getLobbies().remove(lobby);
            }
        }
        if (currentLoggedInUser != null) {
            currentLoggedInUser.setOnline(false);
            currentLoggedInUser.setLastSeen(new Date());
            currentLoggedInUser = null;
        }
    }

    public void userDisconnected() {
        System.out.println("Player disconnected: " + socket.getInetAddress() + " : " + socket.getPort());
        userLogout();
    }

    private synchronized String initSignup(ArrayList<String> data) {
        // if two users attempt to sign up simultaneously we're screwed
        String message = SignUp.initiateSignup(data.get(0), data.get(1), data.get(1), data.get(2), data.get(3), data.get(4)).getMessage();
        return message;
    }

    private synchronized void finalizeSignup(String securityQuestion, String answer) {
        SignUp.setSecurityQuestion(SecurityQuestion.getSecurityQuestion(securityQuestion), answer, this);
    }

    private synchronized String userLogin(String username, String password) {
        String result = Login.login(username, password, this).getMessage();
        return result;
    }

    private synchronized String userLoginWithToken(String token) {
        UserCredentials userCredentials = new Gson().fromJson(token, UserCredentials.class);
        return Login.loginWithCredentials(userCredentials, this).getMessage();
    }

    public User getCurrentLoggedInUser() {
        return currentLoggedInUser;
    }

    public void setCurrentLoggedInUser(User currentLoggedInUser) {
        this.currentLoggedInUser = currentLoggedInUser;
    }

    public String getScoreboard() {
        ArrayList<User> users = ServerUserController.getUsersSorted();
        Packet packet = new Packet(PacketType.GET_SCOREBOARD, new Gson().toJson(users));
        return packet.toJson();
    }

    public String getChats() {
        ArrayList<Chat> chats = ChatDatabase.getChatsOfUser(currentLoggedInUser);
        Packet packet = new Packet(PacketType.GET_CHATS, new Gson().toJson(chats));
        return packet.toJson();
    }

    private void makePrivateChatWith(String username) {
        if (currentLoggedInUser == null)
            return;
        User user = ServerUserController.findUserWithUsername(username);
        if (user == null)
            return;
        new Chat(ChatDatabase.getNextId(), currentLoggedInUser, user);
    }

    private void makeGroupChat(String chatName, List<String> usernames) {
        ArrayList<User> users = new ArrayList<>();
        users.add(currentLoggedInUser);
        for (String username : usernames) {
            User user = ServerUserController.findUserWithUsername(username);
            if (user != null)
                users.add(user);
        }
        new Chat(ChatDatabase.getNextId(), chatName, ChatType.ROOM, users);
    }

    private void sendMessage(int chatId, String text) {
        Chat chat = ChatDatabase.getChatWithId(chatId);
        if (chat == null)
            return;
        chat.sendMessage(currentLoggedInUser, text);
        ChatDatabase.save();
    }
}
