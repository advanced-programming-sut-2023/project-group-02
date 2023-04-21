import view.ProfileMenu;
import view.SignupMenu;

import java.util.Scanner;

import controllers.UserController;

public class Main {
    public static void main(String[] args) {
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
