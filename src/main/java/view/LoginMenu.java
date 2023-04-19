package view;

import controllers.LoginMenuController;
import controllers.MainController;
import utils.Parser;
import view.enums.LoginMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu {
    void run(Scanner scanner) {
        while (true) {
            Parser parser = new Parser(scanner.nextLine());
            if (parser.beginsWith("user login")) {
                login(parser);
            } else if (parser.beginsWith("forgot my password")) {
                forgotPassword(parser , scanner);
            } else if (parser.beginsWith("user logout")) {
                logout();
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    void login(Parser parser) {
        LoginMenuMessages message = LoginMenuController.login(parser.get("u"), parser.get("p"),parser.get("stay-logged-in") != null);
        switch (message) {
            case UNMATCHED_USERNAME_PASSWORD -> System.out.println("Incorrect password!");
            case USERNAME_DOESNT_EXIST -> System.out.println("This username doesn't exist!");
            case EMPTY_FIELD -> System.out.println("Please fill the empty fields!");
            case LOGIN_SUCCESSFUL -> System.out.println("You are logged in!");
        }
    }

    void forgotPassword(Parser parser , Scanner scanner) {
        LoginMenuMessages message = LoginMenuController.forgotPassword(parser.get("u"), scanner);

        if (message.equals(LoginMenuMessages.WRONG_SECURITY_ANSWER)) {
            System.out.println("Your answer is wrong!");
        } else if (message.equals(LoginMenuMessages.USERNAME_DOESNT_EXIST)) {
            System.out.println("This username doesn't exist!");
        } else if (message.equals(LoginMenuMessages.ENTER_NEW_PASSWORD)) {
            System.out.println("Please enter a new password");
            newPassword(parser , scanner);
        }
    }

    void newPassword(Parser parser , Scanner scanner) {
        LoginMenuMessages message = LoginMenuController.setNewPassword(parser.get("u"), scanner.nextLine());

        while (message.equals(LoginMenuMessages.NEW_PASSWORD_WEAK)) {
            System.out.println("Your password is weak. Try another one"); //TODO lists of errors
            message = LoginMenuController.setNewPassword(parser.get("u"), scanner.nextLine());
        }

        if (message.equals(LoginMenuMessages.PASSWORD_IS_CHANGED))
            System.out.println("Your password is changed successfully!");
    }

    void logout() {
        LoginMenuMessages message = LoginMenuController.logout();
        if (message.equals(LoginMenuMessages.ALREADY_LOGGED_OUT))
            System.out.println("You are not in any accounts now");
        if (message.equals(LoginMenuMessages.LOGOUT_SUCCESSFUL))
            System.out.println("You are logged out successfully!");
    }
}
