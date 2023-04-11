package controllers;

import models.User;

import java.util.ArrayList;

public class MainMenuController {
    private static User currentUser;
    private static ArrayList<User> allUsers;
    private static ArrayList<User> allUsersSorted;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        MainMenuController.currentUser = currentUser;
    }
}
