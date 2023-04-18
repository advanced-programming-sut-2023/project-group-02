package controllers;

import java.util.HashMap;

import models.User;

public class UserController {
  private static final HashMap<String, User> users = new HashMap<>();
  private static User currentUser;

  public static User getCurrentUser() {
    return currentUser;
  }

  public static boolean userWithUsernameExists(String username) {
    return users.containsKey(username);
  }

  public static boolean userWithEmailExists(String email) {
    for (User user : users.values()) {
      if (user.getEmail().equals(email)) {
        return true;
      }
    }
    return false;
  }

  public static void signup(User user) {
    users.put(user.getUsername(), user);
    currentUser = user;
  }

  public static int getUserRank(User user) {
    return 0; // TODO
  }
}
