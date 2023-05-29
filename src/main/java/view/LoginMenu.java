package view;

import controllers.LoginMenuController;
import controllers.UserController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.User;
import utils.Captcha;
import utils.Parser;
import utils.PasswordProblem;
import view.enums.LoginMenuMessages;

import java.util.Objects;
import java.util.Scanner;

public class LoginMenu {
    private static User currentUser;
    Text usernameText = new Text("Username: ");
    Text passwordText = new Text("Password: ");
    TextField usernameTextField = new TextField();
    PasswordField passwordField = new PasswordField();
    Button submitButton = new Button("Login");
    Button forgotPasswordButton = new Button("Forgot My Password");
    Text errorText = new Text();

    public Pane getPane() {
        Pane loginMenuPane = new Pane();
        initPane(loginMenuPane);
        return loginMenuPane;
    }

    private void initPane(Pane pane) {
        //TODO add a background
        pane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Menus.css")).toExternalForm());
        pane.setPrefSize(960, 540);
        VBox form = new VBox();
        form.setSpacing(20);
        form.setTranslateX(200);
        form.setTranslateY(150);

        usernameText.getStyleClass().add("title1");
        makeFields(usernameTextField);
        passwordText.getStyleClass().add("title1");
        makeFields(passwordField);

        HBox usernamePart = new HBox(usernameText,usernameTextField);
        usernamePart.setSpacing(10);
        HBox passwordPart = new HBox(passwordText,passwordField);
        passwordPart.setSpacing(17.5);

        handleSubmitButton(submitButton);
        submitButton.requestFocus();
        handleForgotPasswordButton(forgotPasswordButton);
        form.getChildren().addAll(usernamePart,passwordPart,submitButton,forgotPasswordButton,errorText);
        pane.getChildren().add(form);
    }


    private void makeFields(TextField textField) {
        textField.prefHeight(40);
        textField.prefWidth(100);
        textField.getStyleClass().add("text-field1");
    }

    private void handleSubmitButton(Button button) {
        button.getStyleClass().add("button1");
        button.setOnAction(event -> {
            LoginMenuMessages message = LoginMenuController.login(usernameTextField.getText(),passwordField.getText(),true);
            if (message.equals(LoginMenuMessages.LOGIN_SUCCESSFUL)) {
                Main.getStage().setScene(new Scene(new MainMenu().getPane()));
                Main.getStage().show();
            } else {
                usernameTextField.setText("");
                passwordField.setText("");
                errorText.getStyleClass().add("error");
                errorText.setText(message.getMessage());
            }
        });
    }

    private void handleForgotPasswordButton(Button button) {
        button.getStyleClass().add("button1");
        button.setOnAction(event -> {
            //TODO logic of forgot password
        });
    }

    enum State {
        LOGIN_SUCCESSFUL,
        WAIT_FOR_CAPTCHA,
        WAITING,
        WAIT_FOR_SECURITY_ANSWER,
        WAIT_FOR_NEW_PASSWORD,
    }

    private State state = State.WAITING;

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
