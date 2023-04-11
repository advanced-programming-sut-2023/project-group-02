package view;

import models.User;

import java.util.Scanner;

public class MainMenu {
    private static User currentUser;
    public static void run(Scanner scanner) {

    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User cureentUser) {
        MainMenu.currentUser = cureentUser;
    }
}
