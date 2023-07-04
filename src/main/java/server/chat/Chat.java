package server.chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import models.User;
import server.ChatDatabase;

public class Chat {
    public final int id;
    public final ChatType type;
    private final String name;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Message> messages = new ArrayList<Message>();
    private int nextMessageId = 1;

    public Chat(String name, ChatType type, ArrayList<User> users) {
        this.name = name;
        this.id = ChatDatabase.getChats().size();
        this.type = type;
        this.users.addAll(users);
        ChatDatabase.addChat(this);
    }

    public Chat(User user1, User user2) {
        this.id = ChatDatabase.getChats().size();
        this.type = ChatType.PRIVATE;
        this.name = user1.getUsername() + " & " + user2.getUsername();
        this.users = new ArrayList<>(List.of(user1,user2));
        ChatDatabase.addChat(this);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public synchronized void addUser(User user) {
        users.add(user);
    }

    public synchronized void sendMessage(User sender, String text) {
        Message message = new Message(text, sender, this);
        messages.add(message);
        nextMessageId++;
    }

    public void editMessage(int messageId, String text) {
        for (Message message : messages) {
            if (message.id == messageId) message.setText(text);
        }
    }

    public void deleteMessage(int messageId) {
        for (int i = messages.size() - 1; i >= 0; i--) {
            if (messages.get(i).id == messageId)
                messages.remove(messages.get(i));
        }
    }

    public int getNextMessageId() {
        return nextMessageId;
    }

    public String getName() {
        return name;
    }
}
