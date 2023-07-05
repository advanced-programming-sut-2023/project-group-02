package server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import controllers.database.Database;
import models.User;

public class ServerUserController {
    private static final ArrayList<User> users = new ArrayList<>();

    public static ArrayList<User> getUsers() {
        return users;
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

    public static void signup(User user, Connection connection) {
        users.add(user);
        saveUsers();
        connection.setCurrentLoggedInUser(user);
    }

    public static void logout() {
        // TODO
    }

    private static void saveUsers() {
        User[] usersArray = new User[users.size()];
        for (int i = 0; i < users.size(); i++) {
            usersArray[i] = users.get(i);
        }
        Database.write("users", usersArray, User[].class);
    }

    public static void loadUsersFromDatabase() {
        User[] fileUsers = Database.read("users", User[].class);
        if (fileUsers != null) {
            for (User user : fileUsers)
                users.add(user);
        }
    }

    public static void changeUsername(User user, String username) {
        user.setUsername(username);
        saveUsers();
    }

    public static void changePassword(User user, String password) {
        user.setPassword(password);
        saveUsers();
    }

    public static void changeEmail(User user, String email) {
        user.setEmail(email);
        saveUsers();
    }

    public static void changeNickname(User user, String nickname) {
        user.setNickname(nickname);
        saveUsers();
    }

    public static void changeHighScore(User user, int highScore) {
        user.setHighScore(highScore);
        saveUsers();
    }

    public static void changeSlogan(User user, String slogan) {
        user.setSlogan(slogan);
        saveUsers();
    }

    public static void changeAvatar(User user, String avatarPath) {
        user.setAvatarPath(avatarPath);
        saveUsers();
    }

    public static int getNextId() {
        return users.size() + 1;
    }

    public static ArrayList<User> getUsersSorted() {
        ArrayList<User> usersSorted = (ArrayList<User>) users.clone();
        Collections.sort(usersSorted, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                int highScoreCompare = u2.getHighScore() - u1.getHighScore();
                if (highScoreCompare != 0) {
                    return highScoreCompare;
                } else {
                    return u1.getUsername().compareToIgnoreCase(u2.getUsername());
                }
            }
        });
        return usersSorted;
    }

    public static void login(User userWithUsername, Connection connection) {
        connection.setCurrentLoggedInUser(userWithUsername);
        userWithUsername.setOnline(true);
    }
}
