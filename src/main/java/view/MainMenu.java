package view;

import controllers.UserController;
import javafx.scene.layout.Pane;
import utils.Parser;
import view.enums.MainMenuMessages;

import java.util.Scanner;

public class MainMenu {
    public Pane getPane() {
        Pane MainMenuPane = new Pane();

        return MainMenuPane;
    }

    public void run(Scanner scanner) {
        while (true) {
            Parser parser = new Parser(scanner.nextLine());
            if (parser.beginsWith("enter profile menu")) {
                System.out.println("You successfully entered ProfileMenu!");
                new ProfileMenu().run(scanner);
            } else if (parser.beginsWith("enter game menu")) {
                System.out.println("You successfully entered GameMenu!");
                new GameMenu().run(scanner);
            } else if (parser.beginsWith("user logout")) {
                MainMenuMessages output = logout();
                System.out.println(output.getMessage());
                if (output == MainMenuMessages.LOGOUT_SUCCESSFUL) {
                    new SignupMenu().run(scanner);
                }
            } else if (parser.beginsWith("show current menu")) {
                System.out.println("You are at MainMenu");
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
