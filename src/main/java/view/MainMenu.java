package view;

import controllers.UserController;
import utils.Parser;
import view.enums.MainMenuMessages;

import java.util.Scanner;

public class MainMenu {
    public void run(Scanner scanner) {
        while (true) {
            Parser parser = new Parser(scanner.nextLine());
            if (parser.beginsWith("enter profile menu")) {
                new ProfileMenu().run(scanner);
                System.out.println("You successfully entered profile menu!");
            } else if (parser.beginsWith("user logout")) {
                MainMenuMessages output = logout();
                System.out.println(output.getMessage());
                if (output == MainMenuMessages.LOGOUT_SUCCESSFUL)
                    break;
            } else if (parser.beginsWith("enter game menu")) {
                new GameMenu().run(scanner);
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    public static MainMenuMessages logout() {
        if (UserController.getCurrentUser().equals(null))
            return MainMenuMessages.ALREADY_LOGGED_OUT;
        UserController.logout();
        return MainMenuMessages.LOGOUT_SUCCESSFUL;
    }
}
