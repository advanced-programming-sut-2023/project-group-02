package server;

import models.User;
import server.chat.Chat;

import java.util.ArrayList;

public class ChatDatabase {
    //TODO load all the chats from database
    private static ArrayList<Chat> chats = new ArrayList<>();

    public static ArrayList<Chat> getChats() {
        return chats;
    }

    public static ArrayList<Chat> getChatsOfUser(User user) {
        ArrayList<Chat> userChats = new ArrayList<>();
        for (Chat chat : chats) {
            System.out.println("is there any chats? " + chat.getName() + " " + chat.getUsers() + " our user in this is " + user);
            if (chat.getUsers().contains(user)) {
                System.out.println("user in chat is " + user.getUsername());
                userChats.add(chat);
            }
        }
        return userChats;
    }

    public static void addChat(Chat chat) {
        chats.add(chat);
        //TODO update database
    }
}
