package view;

import java.util.ArrayList;
import java.util.Scanner;

import controllers.SignUpMenuController;
import models.SecurityQuestion;
import utils.Captcha;
import utils.Parser;
import utils.PasswordProblem;
import utils.Validation;
import view.enums.SignUpMenuMessages;

public class SignupMenu {
    enum State {
        PASSWORD_CONFIRMATION_NEEDED,
        SECURITY_QUESTION_NEEDED,
        CAPTCHA_ANSWER_NEEDED,
        SIGNUP_SUCCESSFUL,
        WAITING
    }

    private State state = State.WAITING;

    public void run(Scanner scanner) {
        while (true) {
            if (state == State.SIGNUP_SUCCESSFUL) {
                state = State.WAITING;
                new MainMenu().run(scanner);
            }
            Parser parser = new Parser(scanner.nextLine());
            if (parser.beginsWith("user create")) {
                createUser(parser);
            } else if (parser.beginsWith("enter login menu")) {
                System.out.println("You successfully entered LoginMenu!");
                new LoginMenu().run(scanner);
            } else if (state == State.SECURITY_QUESTION_NEEDED && parser.beginsWith("question pick")) {
                pickQuestion(parser);
            } else if (state == State.PASSWORD_CONFIRMATION_NEEDED) {
                confirmPassword(parser.input);
            } else if (state == State.CAPTCHA_ANSWER_NEEDED) {
                captcha(parser);
            } else if (parser.beginsWith("show current menu")) {
                System.out.println("You are at SignupMenu");
            } else if (parser.beginsWith("exit")) {
                break;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    private void createUser(Parser parser) {
        if (!parser.getFlag("u") || !parser.getFlag("p") || !parser.getFlag("email") ||
            !parser.getFlag("s") || !parser.getFlag("n")) {
            System.out.println("Some fields are empty.");
            return;
        }
        String username = parser.get("u");
        ArrayList<String> passwords = parser.getAll("p");
        if (passwords.size() == 0) {
            System.out.println(SignUpMenuMessages.EMPTY_PASSWORD.getMessage());
            return;
        }
        String password = passwords.get(0);
        String passwordConfirmation = passwords.size() >= 2 ? passwords.get(1) : null;
        String email = parser.get("email");
        String nickname = parser.get("n");
        String slogan = parser.get("s");
        SignUpMenuMessages message = SignUpMenuController.initiateSignup(username, password, passwordConfirmation,
            nickname, email, slogan);

        if (slogan.equals("random")) {
            System.out.println("Your slogan is: " + SignUpMenuController.getSlogan());
        }

        if (message == SignUpMenuMessages.PASSWORD_CONFIRMATION_NEEDED) {
            if (password.equals("random")) {
                System.out.println("Your password is: " + SignUpMenuController.getPassword());
            }
            System.out.println("Please re-enter your password:");
            state = State.PASSWORD_CONFIRMATION_NEEDED;
            return;
        }

        if (message == null) {
            state = State.SECURITY_QUESTION_NEEDED;
            printSecurityQuestions();
            return;
        }

        if (message.equals(SignUpMenuMessages.WEAK_PASSWORD)) {
            System.out.println(PasswordProblem.showErrors(SignUpMenuController.passwordProblems));
        } else
            System.out.println(message.getMessage());
    }

    private void printSecurityQuestions() {
        System.out.println("Pick a security question:");
        for (int i = 0; i < SecurityQuestion.values().length; i++) {
            System.out.println((i + 1) + ". " + SecurityQuestion.values()[i].fullSentence);
        }
    }

    private void pickQuestion(Parser parser) {
        int questionNumber;
        SecurityQuestion question;
        try {
            questionNumber = Integer.parseInt(parser.get("q"));
            question = SecurityQuestion.values()[questionNumber - 1];
        } catch (Exception e) {
            System.out.println("Invalid question number!");
            return;
        }

        String answer = parser.get("a");
        String answerConfirmation = parser.get("c");
        if (!answer.equals(answerConfirmation)) {
            System.out.println("Answers don't match!");
            return;
        }

        SignUpMenuController.setSecurityQuestion(question, answer);
        state = State.CAPTCHA_ANSWER_NEEDED;
        System.out.println(Captcha.showCaptcha());
    }

    public void confirmPassword(String password) {
        if (password.equals(SignUpMenuController.getPassword())) {
            state = State.SECURITY_QUESTION_NEEDED;
            printSecurityQuestions();
        } else {
            System.out.println("Passwords don't match!");
        }
    }

    public void captcha(Parser parser) {
        String userInput = parser.getByIndex(0);
        if (Captcha.inputEqualsCaptcha(userInput)) {
            state = State.SIGNUP_SUCCESSFUL;
            System.err.println("Done!");
        } else {
            System.out.println("Please enter the numbers correctly!");
            System.out.println(Captcha.showCaptcha());
        }
    }
}
