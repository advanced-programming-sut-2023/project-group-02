package controllers;

import java.util.ArrayList;

import controllers.database.Database;
import models.User;
import models.UserCredentials;

public class UserController {
    private static final ArrayList<User> users = new ArrayList<>();
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isAuthorized() {
        return currentUser != null;
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

    public static void login(User user, boolean stayLoggedIn) {
        currentUser = user;
        if (stayLoggedIn) {
            saveCredentials();
        }
    }

    public static void signup(User user) {
        users.add(user);
        login(user, false);
        saveUsers();
    }

    public static void logout() {
        currentUser = null;
        deleteCredentials();
    }

    private static void saveUsers() {
        User[] usersArray = new User[users.size()];
        for (int i = 0; i < users.size(); i++) {
            usersArray[i] = users.get(i);
        }
        Database.write("users", usersArray, User[].class);
    }

    private static void saveCredentials() {
        Database.write("currentUser", UserCredentials.of(currentUser), UserCredentials.class);
    }

    private static void deleteCredentials() {
        Database.delete("currentUser");
    }

    public static void loadUsersFromFile() {
        User[] fileUsers = Database.read("users", User[].class);
        if (fileUsers != null) {
            for (User user : fileUsers)
                users.add(user);
        }
    }

    public static void loadCurrentUserFromFile() {
        UserCredentials credentials = Database.read("currentUser", UserCredentials.class);
        if (credentials != null) {
            User user = findUserWithUsername(credentials.username());
            if (user != null) {
                if (user.getPasswordHash().equals(credentials.password())) {
                    currentUser = user;
                }
            }
        }
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
