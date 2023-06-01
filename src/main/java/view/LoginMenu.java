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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.User;
import utils.*;
import view.enums.LoginMenuMessages;

import java.util.Objects;

public class LoginMenu {
    private static User currentUser;
    Text usernameText = new Text("Username: ");
    Text passwordText = new Text("Password: ");
    TextField usernameTextField = new TextField();
    PasswordField passwordField = new PasswordField();
    Button submitButton = new Button("Login");
    Button forgotPasswordButton = new Button("Forgot My Password");
    Text errorText = new Text();
    TextField usernameTextField2 = new TextField();
    Text noUserError = new Text();
    TextField securityAnswerField = new TextField();
    PasswordField newPasswordField = new PasswordField();
    Text securityAnswerError = new Text();
    int countOfWrongAnswers = 0;

    public Pane getPane() {
        Pane loginMenuPane = new Pane();
        initPane(loginMenuPane);
        return loginMenuPane;
    }

    private Pane getForgotPasswordPane() {
        Pane forgotPasswordPane = new Pane();
        initForgotPasswordPane(forgotPasswordPane);
        return forgotPasswordPane;
    }

    private void initPane(Pane pane) {
        pane.setBackground(Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/backgrounds/signup-menu.jpg"))));
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

        HBox usernamePart = new HBox(usernameText, usernameTextField);
        usernamePart.setSpacing(10);
        HBox passwordPart = new HBox(passwordText, passwordField);
        passwordPart.setSpacing(17.5);

        handleSubmitButton(submitButton);
        submitButton.requestFocus();
        handleForgotPasswordButton(forgotPasswordButton);
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button2");
        backButton.setOnAction(event -> Main.setScene(Main.getTitlePane()));

        form.getChildren().addAll(usernamePart, passwordPart, submitButton, forgotPasswordButton, backButton, errorText);
        pane.getChildren().add(form);
    }

    private void initForgotPasswordPane(Pane pane) {
        pane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Menus.css")).toExternalForm());
        pane.setBackground(Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/backgrounds/signup-menu.jpg"))));
        pane.setPrefSize(960, 540);
        VBox form = new VBox();
        form.setTranslateX(200);
        form.setTranslateY(150);
        form.setSpacing(20);

        makeFields(usernameTextField2);
        usernameTextField2.setPromptText("Username...");
        Button checkUsernameExistButton = checkUsernameExistsButton(pane);
        form.getChildren().addAll(usernameTextField2,checkUsernameExistButton,makeBackButton(),noUserError);
        pane.getChildren().add(form);
    }

    private Button makeBackButton() {
        Button button = new Button("Back");
        button.getStyleClass().add("button2");
        button.setOnAction(event -> Main.setScene(getPane()));
        return button;
    }

    private void makeFields(TextField textField) {
        textField.prefHeight(40);
        textField.prefWidth(100);
        textField.getStyleClass().add("text-field1");
    }

    private void handleSubmitButton(Button button) {
        button.getStyleClass().add("button2");
        button.setOnAction(event -> login());
    }

    private void handleForgotPasswordButton(Button button) {
        button.getStyleClass().add("button2");
        button.setOnAction(event -> {
            Main.setScene(getForgotPasswordPane());
            //TODO logic of forgot password
        });
    }

    private Button checkUsernameExistsButton(Pane pane) {
        Button button = new Button("Check Username");
        button.getStyleClass().add("button2");
        button.setOnAction(event -> {
            if (UserController.findUserWithUsername(usernameTextField2.getText()) == null) {
                noUserError.setText("No user with this username exists!!");
            } else {
                resetForSecurityAnswer(pane);
            }
        });
        return button;
    }

    private void resetForSecurityAnswer(Pane pane) {
        VBox myVbox = new VBox();
        if (pane.getChildren().get(0) instanceof VBox) myVbox = (VBox) pane.getChildren().get(0);
        currentUser = UserController.findUserWithUsername(usernameTextField2.getText());
        Text questionText = new Text(currentUser.getSecurityQuestion().fullSentence);
        questionText.getStyleClass().add("text-title2");
        makeFields(securityAnswerField);
        securityAnswerField.setPromptText("Write Your Answer...");
        securityAnswerField.setMaxWidth(300);
        Button checkSecurityAnswerButton = checkSecurityAnswerButton(pane);
        myVbox.getChildren().clear();
        myVbox.getChildren().addAll(questionText,securityAnswerField,checkSecurityAnswerButton,makeBackButton(),securityAnswerError);
    }

    private Button checkSecurityAnswerButton(Pane pane) {
        Button button = new Button("Check");
        button.getStyleClass().add("button2");
        button.setOnAction(event -> {
            if (!securityAnswerField.getText().equals(currentUser.getSecurityAnswer())) {
                securityAnswerError.setText("Wrong Security Answer!!");
                securityAnswerField.setText("");
                countOfWrongAnswers++;
                if (countOfWrongAnswers == 5) {
                    Graphics.showMessagePopup("You tried too much!!");
                    Main.setScene(getPane());
                }
            } else {
                resetForNewPassword(pane);
            }
        });
        return button;
    }

    private void resetForNewPassword(Pane pane) {
        VBox myVbox = new VBox();
        if (pane.getChildren().get(0) instanceof VBox) myVbox = (VBox) pane.getChildren().get(0);

        Text informText = new Text("Import Your New Password");
        informText.getStyleClass().add("text-title2");
        makeFields(newPasswordField);
        newPasswordField.setMaxWidth(300);
        Text passwordErrors = new Text();
        newPasswordField.setPromptText("New Password...");
        newPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Validation.validatePassword(newValue).size() == 0 || newValue.equals("")) passwordErrors.setText("");
            else passwordErrors.setText(PasswordProblem.showErrors(Validation.validatePassword(newValue)));
        });

        Button finalButton = new Button("Submit Password");
        finalButton.getStyleClass().add("button2");
        finalButton.setOnAction(event -> newPassword());
        passwordErrors.setFont(new Font("Arial",20));
        passwordErrors.setFill(Color.WHITE);

        myVbox.getChildren().clear();
        myVbox.getChildren().addAll(informText,newPasswordField,finalButton,makeBackButton(),passwordErrors);
    }

    void login() {
        LoginMenuMessages message = LoginMenuController.login(usernameTextField.getText(), passwordField.getText(), true);
        if (message.equals(LoginMenuMessages.LOGIN_SUCCESSFUL)) {
            Main.getStage().setScene(new Scene(new MainMenu().getPane()));
            Main.getStage().show();
        } else {
            usernameTextField.setText("");
            passwordField.setText("");
            errorText.getStyleClass().add("error");
            errorText.setText(message.getMessage());
        }
    }

    void captcha(Parser parser) {
        String userInput = parser.getInput();
        if (Captcha.inputEqualsCaptcha(userInput)) {
            System.out.println("You Logged in successfully!");
        } else {
            System.out.println("Please enter the numbers more carefully!");
            System.out.println(Captcha.showCaptcha());
        }
    }

    void newPassword() {
        LoginMenuMessages message = LoginMenuController.setNewPassword(currentUser,newPasswordField.getText());
        if (message.equals(LoginMenuMessages.NEW_PASSWORD_WEAK)) {
            newPasswordField.setText("");
            return;
        }
        if (message.equals(LoginMenuMessages.PASSWORD_IS_CHANGED)) {
            Graphics.showMessagePopup("Password Changed Successfully!");
            Main.setScene(getPane());
        }
    }
}
