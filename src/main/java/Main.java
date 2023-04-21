import view.ProfileMenu;
import view.SignupMenu;

import java.io.IOException;
import java.util.Scanner;

import controllers.UserController;
import controllers.database.Database;

public class Main {
    public static void main(String[] args) {
        try {
            Database.init();
        } catch (IOException e) {
        }

        UserController.loadUsersFromFile();
        UserController.loadCurrentUserFromFile();

        Scanner scanner = new Scanner(System.in);
        if (UserController.isAuthorized()) {
            new ProfileMenu().run(scanner);
        } else {
            new SignupMenu().run(scanner);
        }
        scanner.close();
    }
}
