package view;

import controllers.LoginMenuController;
import utils.Captcha;
import utils.Parser;
import utils.PasswordProblem;
import view.enums.LoginMenuMessages;

import java.util.Scanner;

public class LoginMenu {
    enum State {
        LOGIN_SUCCESSFUL,
        WAIT_FOR_CAPTCHA,
        WAITING,
    }

    private State state = State.WAITING;

    public void run(Scanner scanner) {
        while (true) {
            if (state.equals(State.LOGIN_SUCCESSFUL)) {
                new MainMenu().run(scanner);
                break;
            }
            Parser parser = new Parser(scanner.nextLine());
            if (state.equals(State.WAIT_FOR_CAPTCHA)) {
                captcha(parser);
            } else if (parser.beginsWith("user login")) {
                login(parser);
            } else if (parser.beginsWith("forgot my password")) {
                forgotPassword(parser, scanner);
            } else if (parser.beginsWith("back")) {
                System.out.println("You're back at the signin menu");
                break;
            } else if (parser.beginsWith("show current menu")) {
                System.out.println("You are at LoginMenu");
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    void login(Parser parser) {
        LoginMenuMessages message = LoginMenuController.login(parser.get("u"), parser.get("p"),
                parser.getFlag("stay-logged-in"));

        if (message.equals(LoginMenuMessages.LOGIN_SUCCESSFUL)) {
            state = State.WAIT_FOR_CAPTCHA;
            System.out.println(Captcha.showCaptcha());
            captcha(parser);
        } else {
            System.out.println(message.getMessage());
        }
    }

    void captcha(Parser parser) {
        String userInput = parser.getByIndex(0);
        if (Captcha.inputEqualsCaptcha(userInput)) {
            state = State.LOGIN_SUCCESSFUL;
            System.out.println("You Logged in successfully!");
        } else {
            System.out.println("Please enter the numbers more carefully!");
            System.out.println(Captcha.showCaptcha());
        }
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
            System.out.println(PasswordProblem.showErrors(LoginMenuController.passwordProblems));
            message = LoginMenuController.setNewPassword(parser.get("u"), scanner.nextLine());
        }

        if (message.equals(LoginMenuMessages.PASSWORD_IS_CHANGED))
            System.out.println("Your password is changed successfully!");
    }

}
