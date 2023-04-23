package view;

import controllers.LoginMenuController;
import utils.Parser;
import view.enums.LoginMenuMessages;

import java.util.Scanner;

public class LoginMenu {
    public void run(Scanner scanner) {
        while (true) {
            Parser parser = new Parser(scanner.nextLine());
            if (parser.beginsWith("user login")) {
                LoginMenuMessages output = login(parser);
                System.out.println(output.getMessage());
                if (output == LoginMenuMessages.LOGIN_SUCCESSFUL) {
                    new MainMenu().run(scanner);
                    break;
                }
            } else if (parser.beginsWith("forgot my password")) {
                forgotPassword(parser, scanner);
            } else if (parser.beginsWith("show current menu")) {
                System.out.println("You are at LoginMenu");
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    LoginMenuMessages login(Parser parser) {
        LoginMenuMessages message = LoginMenuController.login(parser.get("u"), parser.get("p"),
            parser.get("stay-logged-in") != null);

        return message;
    }

    void forgotPassword(Parser parser, Scanner scanner) {
        LoginMenuMessages message = LoginMenuController.forgotPassword(parser.get("u"), scanner);

        System.out.println(message.getMessage());
        if (message.equals(LoginMenuMessages.ENTER_NEW_PASSWORD)) {
            newPassword(parser, scanner);
        }
    }

    void newPassword(Parser parser, Scanner scanner) {
        LoginMenuMessages message = LoginMenuController.setNewPassword(parser.get("u"), scanner.nextLine());

        while (message.equals(LoginMenuMessages.NEW_PASSWORD_WEAK)) {
            System.out.println("Your password is weak. Try another one"); // TODO lists of errors
            message = LoginMenuController.setNewPassword(parser.get("u"), scanner.nextLine());
        }

        if (message.equals(LoginMenuMessages.PASSWORD_IS_CHANGED))
            System.out.println("Your password is changed successfully!");
    }

}
