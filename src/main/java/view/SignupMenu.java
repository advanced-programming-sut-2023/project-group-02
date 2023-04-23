package view;

import java.util.ArrayList;
import java.util.Scanner;

import controllers.SignUpMenuController;
import models.SecurityQuestion;
import utils.Parser;
import view.enums.SignUpMenuMessages;

public class SignupMenu {
    enum State {
        PASSWORD_CONFIRMATION_NEEDED,
        SECURITY_QUESTION_NEEDED,
        SIGNUP_SUCCESSFUL,
        WAITING
    }

    private State state = State.WAITING;

    public void run(Scanner scanner) {
        while (true) {
            Parser parser = new Parser(scanner.nextLine());
            if (state == State.SIGNUP_SUCCESSFUL) {
                state = State.WAITING;
                new MainMenu().run(scanner);
            } else if (parser.beginsWith("create user")) {
                createUser(parser);
            } else if (parser.beginsWith("enter login menu")) {
                new LoginMenu().run(scanner);
            } else if (state == State.SECURITY_QUESTION_NEEDED && parser.beginsWith("question pick")) {
                pickQuestion(parser);
            } else if (state == State.PASSWORD_CONFIRMATION_NEEDED) {
                confirmPassword(parser.input);
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    private void createUser(Parser parser) {
        String username = parser.get("u");
        ArrayList<String> passwords = parser.getAll("p");
        String password = passwords.get(0);
        String passwordConfirmation = passwords.size() >= 2 ? passwords.get(1) : null;
        String email = parser.get("email");
        String nickname = parser.get("n");
        String slogan = parser.get("s");
        SignUpMenuMessages error = SignUpMenuController.initiateSignup(username, password, passwordConfirmation,
            nickname, email, slogan);

        if (error == null) {
            state = State.SECURITY_QUESTION_NEEDED;
            printSecurityQuestions();
            return;
        }
        if (error == SignUpMenuMessages.PASSWORD_CONFIRMATION_NEEDED) {
            if (password.equals("random")) {
                System.out.println("Your password is: " + SignUpMenuController.getPassword());
            }
            System.out.println("Please re-enter your password:");
            state = State.PASSWORD_CONFIRMATION_NEEDED;
            return;
        }

        System.out.println(error.getMessage());
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
        state = State.SIGNUP_SUCCESSFUL;
        System.err.println("Done!");
    }

    public void confirmPassword(String password) {
        if (password.equals(SignUpMenuController.getPassword())) {
            state = State.SECURITY_QUESTION_NEEDED;
            printSecurityQuestions();
        } else {
            System.out.println("Passwords don't match!");
        }
    }
}
