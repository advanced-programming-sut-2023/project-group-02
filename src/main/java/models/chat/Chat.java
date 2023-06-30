package models.chat;

import java.util.ArrayList;

import models.User;

public class Chat {
    public final int id;
    public final ChatType type;
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Message> messages = new ArrayList<Message>();

    public Chat(int id, ChatType type) {
        this.id = id;
        this.type = type;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void sendMessage(User sender, String text) {
        Message message = new Message(messages.size(), text, sender, this);
        messages.add(message);
    }

    public void editMessage(int messageId, String text) {
        Message message = messages.get(messageId);
        message.setText(text);
    }

    public void deleteMessage(int messageId) {
        messages.remove(messageId);
    }
}
