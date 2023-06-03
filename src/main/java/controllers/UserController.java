package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.text.DefaultEditorKit.DefaultKeyTypedAction;

import controllers.database.Database;
import models.User;
import models.UserCredentials;

public class UserController {
    private static final ArrayList<User> users = new ArrayList<>();

    public static ArrayList<User> getUsers() {
        return users;
    }

    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isAuthorized() {
        return currentUser != null;
    }

    public static User findUserWithId(int id) {
        for (User user : users) {
            if (user.id == id)
                return user;
        }
        return null;
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
            User user = findUserWithId(credentials.id());
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

    public static void changeUsername(User user, String username) {
        user.setUsername(username);
        saveUsers();
        if (user == currentUser) {
            deleteCredentials();
        }
    }

    public static void changePassword(User user, String password) {
        user.setPassword(password);
        saveUsers();
        if (user == currentUser) {
            deleteCredentials();
        }
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
}
