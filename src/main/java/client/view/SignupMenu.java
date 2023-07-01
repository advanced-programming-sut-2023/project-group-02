package client.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import client.view.enums.SignUpMenuMessages;
import controllers.SignUpMenuController;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import models.SecurityQuestion;
import utils.*;
import utils.Graphics;

public class SignupMenu {
    private final Text usernameText = new Text("Username:");
    private final TextField usernameTextField = new TextField();
    private final Text usernameErrors = new Text();
    private final Text passwordText = new Text("Password:");
    private final PasswordField passwordField = new PasswordField();
    private final Text passwordErrors = new Text();
    private final Text nickname = new Text("Nickname:");
    private final TextField nicknameTextField = new TextField();
    private final Text email = new Text("Email:");
    private final TextField emailTextField = new TextField();
    private final Text emailErrors = new Text();
    private final Text slogan = new Text("Slogan:");
    private final TextField sloganTextField = new TextField();
    private ToggleButton sloganGenerator = new ToggleButton();
    private final Text signupError = new Text();
    private Captcha captcha;
    private final TextField captchaTextField = new TextField();

    public Pane getPane() {
        Pane SignupMenuPane = new Pane();
        initPane(SignupMenuPane);
        SignupMenuPane.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getTarget() == SignupMenuPane)
                SignupMenuPane.requestFocus();
        });
        return SignupMenuPane;
    }

    private void initPane(Pane pane) {
        pane.setBackground(Graphics
            .getBackground(Objects.requireNonNull(getClass().getResource("/images/backgrounds/signup-menu.jpg"))));
        pane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Menus.css")).toExternalForm());
        pane.setPrefSize(960, 540);
        addTitleText(pane);
        addUsernameFields(pane);
        addPasswordFields(pane);
        addNicknameFields(pane);
        addEmailFields(pane);
        addSloganField(pane);
        addBackButton(pane, true);
        addSignupButton(pane);
        addSignupError(pane);
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
        usernameTextField.getStyleClass().add("text-field2");
        usernameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Validation.isValidUsername(newValue) || newValue.equals(""))
                usernameErrors.setText("");
            else
                usernameErrors.setText("forbidden character is used.");
        });

        usernameErrors.getStyleClass().add("error");
        usernameErrors.setLayoutX(460);
        usernameErrors.setLayoutY(115);
        usernameErrors.setText("");
        pane.getChildren().addAll(usernameText, usernameTextField, usernameErrors);
    }

    private void addPasswordFields(Pane pane) {
        passwordText.setLayoutX(100);
        passwordText.setLayoutY(160);
        passwordText.getStyleClass().add("title1");

        passwordField.setPromptText("password");
        passwordField.setLayoutX(240);
        passwordField.setLayoutY(135);
        passwordField.getStyleClass().add("text-field2");
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Validation.validatePassword(newValue).size() == 0 || newValue.equals(""))
                passwordErrors.setText("");
            else
                passwordErrors.setText(PasswordProblem.showErrors(Validation.validatePassword(newValue)));
        });

        ToggleButton showPassword = getShowPassToggle(passwordField, passwordErrors);
        ToggleButton generatePassword = getGeneratePassToggle(passwordField, passwordErrors);

        passwordErrors.getStyleClass().add("error");
        passwordErrors.setLayoutX(460);
        passwordErrors.setLayoutY(145);
        passwordErrors.setText("");
        passwordErrors.setWrappingWidth(500);
        pane.getChildren().addAll(passwordText, passwordField, showPassword, generatePassword, passwordErrors);
    }

    private ToggleButton getShowPassToggle(PasswordField passwordField, Text passwordErrors) {
        AtomicReference<String> password = new AtomicReference<>();
        AtomicReference<String> passwordError = new AtomicReference<>();
        ToggleButton showPassword = new ToggleButton();
        showPassword.setLayoutX(430);
        showPassword.setLayoutY(140);
        showPassword.setBackground(Graphics
            .getBackground(Objects.requireNonNull(getClass().getResource("/images/buttons/show-password.png"))));
        showPassword.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            password.set(passwordField.getText());
            passwordError.set(passwordErrors.getText());
            passwordField.clear();
            passwordField.setPromptText(password.get());
            passwordErrors.setText(passwordError.get());
        });
        showPassword.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            passwordErrors.setText("");
            passwordField.setText(password.get());
            passwordField.setPromptText("password");
        });
        showPassword.setFocusTraversable(false);
        return showPassword;
    }

    private ToggleButton getGeneratePassToggle(PasswordField passwordField, Text passwordErrors) {
        ToggleButton generatePassword = new ToggleButton();
        generatePassword.setLayoutX(70);
        generatePassword.setLayoutY(140);
        generatePassword.setBackground(Graphics
            .getBackground(Objects.requireNonNull(getClass().getResource("/images/buttons/generate-random.png"))));
        generatePassword.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            passwordField.setText(Randoms.getPassword());
            passwordErrors.setText("");
            new Alert(Alert.AlertType.INFORMATION, "Password generated successfully: " + passwordField.getText())
                .showAndWait();
        });
        generatePassword.setFocusTraversable(false);
        return generatePassword;
    }

    private void addNicknameFields(Pane pane) {
        nickname.setLayoutX(100);
        nickname.setLayoutY(200);
        nickname.getStyleClass().add("title1");
        nicknameTextField.setPromptText("nickname");
        nicknameTextField.setLayoutX(240);
        nicknameTextField.setLayoutY(175);
        nicknameTextField.getStyleClass().add("text-field2");
        pane.getChildren().addAll(nickname, nicknameTextField);
    }

    private void addEmailFields(Pane pane) {
        email.setLayoutX(100);
        email.setLayoutY(240);
        email.getStyleClass().add("title1");
        emailTextField.setPromptText("email");
        emailTextField.setLayoutX(240);
        emailTextField.setLayoutY(215);
        emailTextField.getStyleClass().add("text-field2");
        emailTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Validation.isValidEmail(newValue) || newValue.equals(""))
                emailErrors.setText("");
            else
                emailErrors.setText("invalid email format.");
        });

        emailErrors.getStyleClass().add("error");
        emailErrors.setLayoutX(460);
        emailErrors.setLayoutY(235);
        emailErrors.setText("");
        pane.getChildren().addAll(email, emailTextField, emailErrors);
    }

    private void addSloganField(Pane pane) {
        slogan.setLayoutX(100);
        slogan.setLayoutY(280);
        slogan.getStyleClass().add("title1");
        sloganTextField.setPromptText("slogan");
        sloganTextField.setLayoutX(240);
        sloganTextField.setLayoutY(255);
        sloganTextField.setMinWidth(500);
        sloganTextField.getStyleClass().add("text-field2");
        sloganGenerator = getSloganGenerate();
        ToggleButton sloganToggle = getSloganToggle();
        slogan.setVisible(false);
        sloganTextField.clear();
        sloganTextField.setVisible(false);
        sloganGenerator.setVisible(false);
        pane.getChildren().addAll(slogan, sloganTextField, sloganGenerator, sloganToggle);
    }

    private ToggleButton getSloganToggle() {
        ToggleButton sloganToggle = new ToggleButton();
        sloganToggle.setLayoutX(70);
        sloganToggle.setLayoutY(260);
        sloganToggle.setBackground(
            Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/buttons/slogan.png"))));
        sloganToggle.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (slogan.isVisible()) {
                slogan.setVisible(false);
                sloganTextField.clear();
                sloganTextField.setVisible(false);
                sloganGenerator.setVisible(false);
                sloganToggle.setLayoutX(70);
            } else {
                slogan.setVisible(true);
                sloganTextField.setVisible(true);
                sloganGenerator.setVisible(true);
                sloganToggle.setLayoutX(40);
            }
        });
        sloganToggle.setFocusTraversable(false);
        return sloganToggle;
    }

    private ToggleButton getSloganGenerate() {
        ToggleButton toggleButton = new ToggleButton();
        toggleButton.setLayoutX(70);
        toggleButton.setLayoutY(260);
        toggleButton.setBackground(Graphics
            .getBackground(Objects.requireNonNull(getClass().getResource("/images/buttons/generate-random.png"))));
        toggleButton.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            sloganTextField.setText(Randoms.getSlogan());
        });
        toggleButton.setFocusTraversable(false);
        return toggleButton;
    }

    private void addBackButton(Pane pane, boolean backToMain) {
        Text back = new Text("Back");
        back.setLayoutX(100);
        back.setLayoutY(350);
        back.getStyleClass().add("title1-with-hover");
        back.setOnMouseClicked(event -> {
            if (backToMain)
                Main.setScene(Main.getTitlePane());
            else {
                Main.setScene(this.getPane());
                signupError.setText("");
            }
        });
        pane.getChildren().add(back);
    }

    private void addSignupButton(Pane pane) {
        Text signup = new Text("Signup");
        signup.setLayoutX(240);
        signup.setLayoutY(350);
        signup.getStyleClass().add("title1-with-hover");
        signup.setOnMouseClicked(event -> {
            if (!usernameErrors.getText().isEmpty() || !passwordErrors.getText().isEmpty()
                || !emailErrors.getText().isEmpty()) {
                signupError.setText("Please fix the errors.");
            } else if (usernameTextField.getText().isEmpty() || passwordField.getText().isEmpty()
                || nicknameTextField.getText().isEmpty() || emailTextField.getText().isEmpty()
                || (slogan.isVisible() && sloganTextField.getText().isEmpty())) {
                signupError.setText("Please fill all the fields.");
            } else {
                SignUpMenuMessages message = SignUpMenuController.initiateSignup(usernameTextField.getText(),
                    passwordField.getText(), passwordField.getText(), nicknameTextField.getText(),
                    emailTextField.getText(), sloganTextField.getText());
                if (message != null)
                    signupError.setText(message.getMessage());
                else {
                    signupError.setText("");
                    new Alert(Alert.AlertType.INFORMATION, "information filled successfully. " +
                        "now you have to set a security question.").showAndWait();
                    Main.setScene(this.getSecurityQuestionPane());
                }
            }
        });
        pane.getChildren().add(signup);
    }

    private void addSignupError(Pane pane) {
        signupError.setText("");
        signupError.setLayoutX(240);
        signupError.setLayoutY(315);
        signupError.getStyleClass().add("error");
        pane.getChildren().add(signupError);
    }

    private Pane getSecurityQuestionPane() {
        Pane securityQuestionPane = new Pane();
        initSecurityQuestionPane(securityQuestionPane);
        securityQuestionPane.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getTarget() == securityQuestionPane)
                securityQuestionPane.requestFocus();
        });
        return securityQuestionPane;
    }

    private void initSecurityQuestionPane(Pane pane) {
        pane.setBackground(Graphics
            .getBackground(Objects.requireNonNull(getClass().getResource("/images/backgrounds/signup-menu.jpg"))));
        pane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Menus.css")).toExternalForm());
        pane.setPrefSize(960, 540);
        Text title = getSecurityQuestionTitle();
        ChoiceBox<String> questionBox = getChoiceBox();
        TextField answerField = getSecurityQuestionField();
        addBackButton(pane, false);
        addSubmitButton(pane, questionBox, answerField);
        addSignupError(pane);
        addCaptcha(pane);
        pane.getChildren().addAll(title, questionBox, answerField);
    }

    private Text getSecurityQuestionTitle() {
        Text title = new Text("Security Question");
        title.setLayoutX(100);
        title.setLayoutY(65);
        title.getStyleClass().add("text-title2");
        return title;
    }

    private ChoiceBox<String> getChoiceBox() {
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setLayoutX(100);
        choiceBox.setLayoutY(100);
        choiceBox.setMinWidth(350);
        choiceBox.getStyleClass().add("text-content");
        choiceBox.getItems().addAll(SecurityQuestion.getSecurityQuestions());
        choiceBox.setValue(SecurityQuestion.getSecurityQuestions().get(0));
        choiceBox.setFocusTraversable(false);
        return choiceBox;
    }

    private TextField getSecurityQuestionField() {
        TextField textField = new TextField();
        textField.setLayoutX(500);
        textField.setLayoutY(102);
        textField.setMinWidth(150);
        textField.getStyleClass().add("text-field2");
        textField.setFocusTraversable(false);
        textField.setPromptText("answer");
        return textField;
    }

    private void addSubmitButton(Pane pane, ChoiceBox<String> questionBox, TextField answer) {
        Text submit = new Text("Submit");
        submit.setLayoutX(240);
        submit.setLayoutY(350);
        submit.getStyleClass().add("title1-with-hover");
        submit.setOnMouseClicked(event -> {
            if (answer.getText().isEmpty() || captchaTextField.getText().isEmpty()) {
                signupError.setText("Please fill all the field.");
            } else if (!captchaTextField.getText().equals(captcha.getCaptchaAnswer())) {
                try {
                    captcha = Graphics.generateCaptcha(100, 180);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                captchaTextField.setText("");
                pane.getChildren().remove(captcha.getCaptchaImage());
                pane.getChildren().add(captcha.getCaptchaImage());
                signupError.setText("Captcha is wrong.");
            } else {
                SignUpMenuController.setSecurityQuestion(SecurityQuestion.getSecurityQuestion(questionBox.getValue()),
                    answer.getText());
                signupError.setText("");
                new Alert(Alert.AlertType.INFORMATION, "user created successfully").showAndWait();
                Main.setScene(new MainMenu().getPane());
            }
        });
        pane.getChildren().add(submit);
    }

    private void addCaptcha(Pane pane) {
        try {
            captcha = Graphics.generateCaptcha(100, 180);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        captchaTextField.setLayoutX(100);
        captchaTextField.setLayoutY(230);
        captchaTextField.setMaxWidth(100);
        captchaTextField.setPromptText("captcha");
        captchaTextField.setText("");
        captchaTextField.getStyleClass().add("text-field2");
        addRefreshCaptcha(pane);
        pane.getChildren().addAll(captcha.getCaptchaImage(), captchaTextField);
    }

    private void addRefreshCaptcha(Pane pane) {
        ToggleButton refresh = new ToggleButton();
        refresh.setLayoutX(203);
        refresh.setLayoutY(185);
        refresh.setBackground(
            Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/buttons/refresh.png"))));
        refresh.setOnMouseClicked(event -> {
            try {
                captcha = Graphics.generateCaptcha(100, 180);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            captchaTextField.setText("");
            pane.getChildren().remove(captcha.getCaptchaImage());
            pane.getChildren().add(captcha.getCaptchaImage());
        });
        pane.getChildren().add(refresh);
    }

//    enum State {
//        PASSWORD_CONFIRMATION_NEEDED, SECURITY_QUESTION_NEEDED, CAPTCHA_ANSWER_NEEDED, SIGNUP_SUCCESSFUL, WAITING
//    }
//
//    private State state = State.WAITING;

//    private void createUser(Parser parser) {
//        if (!parser.getFlag("u") || !parser.getFlag("p") || !parser.getFlag("email") || !parser.getFlag("s")
//                || !parser.getFlag("n")) {
//            System.out.println("Some fields are empty.");
//            return;
//        }
//        String username = parser.get("u");
//        ArrayList<String> passwords = parser.getAll("p");
//        if (passwords.size() == 0) {
//            System.out.println(SignUpMenuMessages.EMPTY_PASSWORD.getMessage());
//            return;
//        }
//        String password = passwords.get(0);
//        String passwordConfirmation = passwords.size() >= 2 ? passwords.get(1) : null;
//        String email = parser.get("email");
//        String nickname = parser.get("n");
//        String slogan = parser.get("s");
//        SignUpMenuMessages message = SignUpMenuController.initiateSignup(username, password, passwordConfirmation,
//                nickname, email, slogan);
//
//        if (slogan.equals("random")) {
//            System.out.println("Your slogan is: " + SignUpMenuController.getSlogan());
//        }
//
//        if (message == SignUpMenuMessages.PASSWORD_CONFIRMATION_NEEDED) {
//            if (password.equals("random")) {
//                System.out.println("Your password is: " + SignUpMenuController.getPassword());
//            }
//            System.out.println("Please re-enter your password:");
//            state = State.PASSWORD_CONFIRMATION_NEEDED;
//            return;
//        }
//
//        if (message == null) {
//            state = State.SECURITY_QUESTION_NEEDED;
//            printSecurityQuestions();
//            return;
//        }
//
//        if (message.equals(SignUpMenuMessages.WEAK_PASSWORD)) {
//            System.out.println(PasswordProblem.showErrors(SignUpMenuController.passwordProblems));
//        } else
//            System.out.println(message.getMessage());
//    }

//    private void printSecurityQuestions() {
//        System.out.println("Pick a security question:");
//        for (int i = 0; i < SecurityQuestion.values().length; i++) {
//            System.out.println((i + 1) + ". " + SecurityQuestion.values()[i].fullSentence);
//        }
//    }

    // private void pickQuestion(Parser parser) {
    // int questionNumber;
    // SecurityQuestion question;
    // try {
    // questionNumber = Integer.parseInt(parser.get("q"));
    // question = SecurityQuestion.values()[questionNumber - 1];
    // } catch (Exception e) {
    // System.out.println("Invalid question number!");
    // return;
    // }
    //
    // String answer = parser.get("a");
    // String answerConfirmation = parser.get("c");
    // if (!answer.equals(answerConfirmation)) {
    // System.out.println("Answers don't match!");
    // return;
    // }
    //
    // SignUpMenuController.setSecurityQuestion(question, answer);
    // state = State.CAPTCHA_ANSWER_NEEDED;
    // System.out.println(Captcha.showCaptcha());
    // }

//    public void confirmPassword(String password) {
//        if (password.equals(SignUpMenuController.getPassword())) {
//            state = State.SECURITY_QUESTION_NEEDED;
//            printSecurityQuestions();
//        } else {
//            System.out.println("Passwords don't match!");
//        }
//    }

    // public void captcha(Parser parser) {
    // String userInput = parser.getByIndex(0);
    // if (Captcha.inputEqualsCaptcha(userInput)) {
    // state = State.SIGNUP_SUCCESSFUL;
    // System.err.println("Done!");
    // } else {
    // System.out.println("Please enter the numbers correctly!");
    // System.out.println(Captcha.showCaptcha());
    // }
    // }
}
