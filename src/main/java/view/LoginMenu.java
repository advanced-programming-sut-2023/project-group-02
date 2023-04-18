package view;

import controllers.LoginMenuController;
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

        switch (message) {
            case WRONG_SECURITY_ANSWER -> System.out.println("Your answer is wrong!");
            case USERNAME_DOESNT_EXIST -> System.out.println("This username doesn't exist!");
            case PASSWORD_IS_CHANGED -> System.out.println("Password is changed successfully!");
        }
    }

    void logout() {
        LoginMenuMessages message = LoginMenuController.logout();
        if (message.equals(LoginMenuMessages.ALREADY_LOGGED_OUT))
            System.out.println("You are not in any accounts now");
        if (message.equals(LoginMenuMessages.LOGOUT_SUCCESSFUL))
            System.out.println("You are logged out successfully!");
    }
}
