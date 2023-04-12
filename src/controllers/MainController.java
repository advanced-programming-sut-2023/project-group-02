package controllers;

import models.User;

import java.util.ArrayList;

public class MainController {
    private static User currentUser;
    private static ArrayList<User> allUsers;
    private static ArrayList<User> allUsersSorted;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        MainController.currentUser = currentUser;
    }

    public static void addUser(User user) {

    }
    public static void addToSortedList(User user) {

    }

    public static void updateSortedList() {

    }

    public static User findUserByUsername(String username) {
        return null;
    }

    public static boolean emailExists(String email) {
        return true;
    }


}
