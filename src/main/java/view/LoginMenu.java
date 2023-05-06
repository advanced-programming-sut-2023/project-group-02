package view;

import controllers.LoginMenuController;
import controllers.UserController;
import models.User;
import utils.Captcha;
import utils.Parser;
import utils.PasswordProblem;
import view.enums.LoginMenuMessages;

import java.util.Scanner;

public class LoginMenu {
    private static User currentUser;
    enum State {
        LOGIN_SUCCESSFUL,
        WAIT_FOR_CAPTCHA,
        WAITING,
        WAIT_FOR_SECURITY_ANSWER,
        WAIT_FOR_NEW_PASSWORD,
    }

    private State state = State.WAITING;

    public void run(Scanner scanner) {
        while (true) {
            if (state.equals(State.LOGIN_SUCCESSFUL)) {
                currentUser = null;
                new MainMenu().run(scanner);
                break;
            }
            Parser parser = new Parser(scanner.nextLine());
            if (state.equals(State.WAIT_FOR_CAPTCHA)) {
                captcha(parser);
            } else if (state.equals(State.WAIT_FOR_SECURITY_ANSWER)) {
                securityAnswer(parser);
            } else if (state.equals(State.WAIT_FOR_NEW_PASSWORD)) {
                newPassword(parser);
            } else if (parser.beginsWith("user login")) {
                login(parser);
            } else if (parser.beginsWith("forgot my password")) {
                forgotPassword(parser);
            } else if (parser.beginsWith("back")) {
                System.out.println("You're back at the sign up menu");
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
        } else {
            System.out.println(message.getMessage());
        }
    }

    void captcha(Parser parser) {
        String userInput = parser.getInput();
        if (Captcha.inputEqualsCaptcha(userInput)) {
            state = State.LOGIN_SUCCESSFUL;
            System.out.println("You Logged in successfully!");
        } else {
            System.out.println("Please enter the numbers more carefully!");
            System.out.println(Captcha.showCaptcha());
        }
    }

    void forgotPassword(Parser parser) {
        if ((currentUser = UserController.findUserWithUsername(parser.get("u"))) == null) {
            System.out.println("Username doesn't exist!");
            return;
        }
        System.out.println(currentUser.getSecurityQuestion().fullSentence);
        state = State.WAIT_FOR_SECURITY_ANSWER;
    }

    void securityAnswer(Parser parser) {
        String answer = parser.getInput();
        if (!answer.equals(currentUser.getSecurityAnswer())) {
            System.out.println("Incorrect Security Answer!");
            return;
        }
        System.out.println("Print enter your new password!");
        state = State.WAIT_FOR_NEW_PASSWORD;
    }

    void newPassword(Parser parser) {
        LoginMenuMessages message = LoginMenuController.setNewPassword(currentUser, parser.getInput());
        if (message.equals(LoginMenuMessages.NEW_PASSWORD_WEAK)) {
            System.out.println(PasswordProblem.showErrors(LoginMenuController.passwordProblems));
            return;
        }
        if (message.equals(LoginMenuMessages.PASSWORD_IS_CHANGED))
            System.out.println(message.getMessage());
        state = State.WAITING;
    }

}
