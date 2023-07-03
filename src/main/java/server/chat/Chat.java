package server.chat;

import java.util.ArrayList;

import models.User;
import server.ChatDatabase;

public class  Chat {
    public final int id;
    public final ChatType type;
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Message> messages = new ArrayList<Message>();
    private int nextMessageId = 1;

    public Chat(ChatType type, ArrayList<User> users) {
        this.id = ChatDatabase.getChats().size();
        this.type = type;
        this.users = users;
        ChatDatabase.getChats().add(this);
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
        Message message = messages.get(messageId);
        message.setText(text);
    }

    public void deleteMessage(int messageId) {
        messages.remove(messageId);
    }

    public int getNextMessageId() {
        return nextMessageId;
    }
}
