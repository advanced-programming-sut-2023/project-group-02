package controllers;

import java.util.ArrayList;

import models.User;

public class UserController {
    private static final ArrayList<User> users = new ArrayList<>();
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        UserController.currentUser = currentUser;
    }

    public static User findUserWithUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public static boolean userWithUsernameExists(String username) {
        return findUserWithUsername(username) != null;
    }

    public static boolean userWithEmailExists(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public static void signup(User user) {
        users.add(user);
        currentUser = user;
    }

    public static int getUserRank(User user) {
        int rank = 1;
        for (User otherUser : users) {
            if (otherUser.getHighScore() > user.getHighScore()) {
                rank++;
            }
        }
        return rank;
    }
}
