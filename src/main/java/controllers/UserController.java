package controllers;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import models.User;

public class UserController {
  private static final ArrayList<User> users = new ArrayList<>();
  private static User currentUser;

  public static User getCurrentUser() {
    return currentUser;
  }

  public static User findUserWithUsername(String username) {
    for (User user : users) {
      if (user.getUsername().equals(username))
        return user;
    }
    return null;
  }

  public static boolean userWithUsernameExists(String username) {
    if (findUserWithUsername(username) != null) return true;
    return false;
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
    return 0; // TODO
  }
}