package server.chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    public Chat(int id, String name, ChatType type, ArrayList<User> users) {
        if (type.equals(ChatType.PUBLIC)) System.out.println("public room is been made");
        this.name = name;
        this.id = id;
        this.type = type;
        this.users.addAll(users);
        ChatDatabase.addChat(this);
    }

    public Chat(int id, User user1, User user2) {
        this.id = id;
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
        Message message = new Message(nextMessageId, text, sender, this, new Date());
        messages.add(message);
        nextMessageId++;
    }

    public void editMessage(int messageId, String text) {
        for (Message message : messages) {
            if (message.id == messageId) {
                message.setText(text);
                return;
            }
        }
    }

    public void deleteMessage(int messageId) {
        for (int i = messages.size() - 1; i >= 0; i--) {
            if (messages.get(i).id == messageId) {
                messages.remove(messages.get(i));
                return;
            }
        }
    }

    public int getNextMessageId() {
        return nextMessageId;
    }

    public void setNextMessageId(int nextMessageId) {
        this.nextMessageId = nextMessageId;
    }

    public String getName() {
        return name;
    }
}
