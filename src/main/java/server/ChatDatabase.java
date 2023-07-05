package server;

import models.User;
import server.chat.Chat;
import server.chat.ChatType;
import server.chat.Message;

import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ChatDatabase {
    private static ArrayList<Chat> chats = new ArrayList<>();

    public static void init() throws SQLException {
        Connection connection = SqliteDatabase.getConnection();
        // create the table if it doesn't exist
        Statement statement = connection.createStatement();
        // the table should contain the following columns:
        // id, name, type, users, messages
        // each message should contain the following columns:
        // id, text, senderId, chatId
        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS messages (id INTEGER PRIMARY KEY, text TEXT, senderUsername INTEGER, chatId INTEGER, date INTEGER)");
        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS chats (id INTEGER PRIMARY KEY, name TEXT, type TEXT, users TEXT, messages TEXT, nextMessageId INTEGER)");

        // load the chats from the database
        ResultSet rs = statement.executeQuery("SELECT * FROM chats");
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String typeName = rs.getString("type");
            ChatType type = ChatType.valueOf(typeName);
            String usersRaw = rs.getString("users");
            String messagesRaw = rs.getString("messages");
            int nextMessageId = rs.getInt("nextMessageId");

            ArrayList<User> users = new ArrayList<>();
            String[] usernames = usersRaw.split(",");
            for (String username : usernames) {
                users.add(ServerUserController.findUserWithUsername(username));
            }

            Chat chat = new Chat(id, name, type, users);
            chat.setNextMessageId(nextMessageId);

            ArrayList<Integer> messageIds = new ArrayList<>();
            String[] messageIdsRaw = messagesRaw.split(",");
            for (String messageId : messageIdsRaw) {
                messageIds.add(Integer.parseInt(messageId));
            }
            ArrayList<Message> messages = new ArrayList<>();
            for (int messageId : messageIds) {
                ResultSet messageRs = statement.executeQuery("SELECT * FROM messages WHERE id = " + messageId);

                String text = messageRs.getString("text");
                String senderUsername = messageRs.getString("senderUsername");
                int date = messageRs.getInt("date");
                System.out.println(date);

                User sender = ServerUserController.findUserWithUsername(senderUsername);
                messages.add(new Message(messageId, text, sender, chat, new Date()));
            }
        }
    }

    public static ArrayList<Chat> getChats() {
        return chats;
    }

    public static ArrayList<Chat> getChatsOfUser(User user) {
        ArrayList<Chat> userChats = new ArrayList<>();
        for (Chat chat : chats) {
            if (chat.getUsers().contains(user)) {
                userChats.add(chat);
            }
        }
        return userChats;
    }

    public static void addChat(Chat chat) {
        chats.add(chat);
        save();
    }

    public static int getNextId() {
        return chats.size() + 1;
    }

    public static void save() {
        Connection connection = SqliteDatabase.getConnection();
        try {
            Statement statement = connection.createStatement();
            for (Chat chat : chats) {
                String users = "";
                for (User user : chat.getUsers()) {
                    users += user.getUsername() + ",";
                }
                users = users.substring(0, users.length() - 1);

                String messages = "";
                for (Message message : chat.getMessages()) {
                    messages += message.id + ",";
                }
                messages = messages.substring(0, messages.length() - 1);

                statement.executeUpdate("INSERT INTO chats (id, name, type, users, messages, nextMessageId) VALUES ("
                        + chat.id + ", '" + chat.getName() + "', '" + chat.type.toString() + "', '" + users + "', '"
                        + messages + "', " + chat.getNextMessageId() + ")");

                for (Message message : chat.getMessages()) {
                    statement.executeUpdate("INSERT INTO messages (id, text, senderUsername, chatId, date) VALUES ("
                            + message.id + ", '" + message.getText() + "', '" + message.sender.getUsername()
                            + "', " + message.chat.id + ", " + message.getDate() + ")");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
