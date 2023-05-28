package view;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import controllers.SignUpMenuController;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import models.SecurityQuestion;
import utils.*;
import view.enums.SignUpMenuMessages;

public class SignupMenu {
    Text usernameText = new Text("Username:");
    TextField usernameTextField = new TextField();
    Text usernameErrors = new Text();
    Text passwordText = new Text("Password:");
    TextField passwordTextField = new TextField();
    Text passwordErrors = new Text();
    Text nickname = new Text("Nickname:");
    TextField nicknameTextField = new TextField();
    Text nicknameErrors = new Text();
    Text email = new Text("Email:");
    TextField emailTextField = new TextField();
    Text emailErrors = new Text();

    public Pane getPane() {
        Pane SignupMenuPane = new Pane();
        initPane(SignupMenuPane);
        return SignupMenuPane;
    }

    private void initPane(Pane pane) {
        pane.setBackground(Graphics.getBackground(Objects.requireNonNull(getClass().getResource(
            "/images/backgrounds/signup-menu.jpg"))));
        pane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Menus.css")).toExternalForm());
        pane.setPrefSize(960, 540);
        addTitleText(pane);
        addUsernameFields(pane);
        addPasswordFields(pane);
        addNicknameFields(pane);
        addEmailFields(pane);

        addBackButton(pane);
    }

    private void addTitleText(Pane pane) {
        Text text = new Text("Sign Up");
        text.setLayoutX(100);
        text.setLayoutY(65);
        text.getStyleClass().add("text-title2");
        pane.getChildren().add(text);
    }

    private void addUsernameFields(Pane pane) {
        usernameText.setLayoutX(100);
        usernameText.setLayoutY(120);
        usernameText.getStyleClass().add("title1");
        usernameTextField.setPromptText("username");
        usernameTextField.setLayoutX(240);
        usernameTextField.setLayoutY(95);
        usernameTextField.prefWidth(100);
        usernameTextField.prefHeight(40);
        usernameTextField.getStyleClass().add("text-field2");
        usernameTextField.setFocusTraversable(false);
        usernameErrors.getStyleClass().add("error");
        usernameErrors.setLayoutX(440);
        usernameErrors.setLayoutY(115);
        usernameErrors.setText("");
        pane.getChildren().addAll(usernameText, usernameTextField, usernameErrors);
    }

    private void addPasswordFields(Pane pane) {
        passwordText.setLayoutX(100);
        passwordText.setLayoutY(160);
        passwordText.getStyleClass().add("title1");
        passwordTextField.setPromptText("password");
        passwordTextField.setLayoutX(240);
        passwordTextField.setLayoutY(135);
        passwordTextField.prefWidth(100);
        passwordTextField.prefHeight(40);
        passwordTextField.getStyleClass().add("text-field2");
        passwordTextField.setFocusTraversable(false);
        passwordErrors.getStyleClass().add("error");
        passwordErrors.setLayoutX(440);
        passwordErrors.setLayoutY(155);
        passwordErrors.setText("");
        pane.getChildren().addAll(passwordText, passwordTextField, passwordErrors);
    }

    private void addNicknameFields(Pane pane) {
        nickname.setLayoutX(100);
        nickname.setLayoutY(200);
        nickname.getStyleClass().add("title1");
        nicknameTextField.setPromptText("nickname");
        nicknameTextField.setLayoutX(240);
        nicknameTextField.setLayoutY(175);
        nicknameTextField.prefWidth(100);
        nicknameTextField.prefHeight(40);
        nicknameTextField.getStyleClass().add("text-field2");
        nicknameTextField.setFocusTraversable(false);
        nicknameErrors.getStyleClass().add("error");
        nicknameErrors.setLayoutX(440);
        nicknameErrors.setLayoutY(195);
        nicknameErrors.setText("");
        pane.getChildren().addAll(nickname, nicknameTextField, nicknameErrors);
    }

    private void addEmailFields(Pane pane) {
        email.setLayoutX(100);
        email.setLayoutY(240);
        email.getStyleClass().add("title1");
        emailTextField.setPromptText("email");
        emailTextField.setLayoutX(240);
        emailTextField.setLayoutY(215);
        emailTextField.prefWidth(100);
        emailTextField.prefHeight(40);
        emailTextField.getStyleClass().add("text-field2");
        emailTextField.setFocusTraversable(false);
        emailErrors.getStyleClass().add("error");
        emailErrors.setLayoutX(440);
        emailErrors.setLayoutY(235);
        emailErrors.setText("");
        pane.getChildren().addAll(email, emailTextField, emailErrors);
    }

    private void addBackButton(Pane pane) {
        Text back = new Text("Back");
        back.setLayoutX(100);
        back.setLayoutY(300);
        back.getStyleClass().add("title1-with-hover");
        back.setOnMouseClicked(event -> {
            Main.setScene(Main.getTitlePane());
        });
        pane.getChildren().add(back);
    }

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
