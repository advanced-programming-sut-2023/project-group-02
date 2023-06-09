package server.logic;

import client.view.enums.ProfileMenuMessages;
import client.view.enums.SignUpMenuMessages;
import controllers.UserController;
import models.SecurityQuestion;
import models.User;
import server.ChatDatabase;
import server.Connection;
import server.ServerUserController;
import server.chat.Chat;
import server.chat.ChatType;
import utils.PasswordProblem;
import utils.Randoms;
import utils.Validation;

import java.util.ArrayList;

public class SignUp {
    public static ArrayList<PasswordProblem> passwordProblems;

    public static ProfileMenuMessages register(String username, String password,
                                               String nickname, String slogan, String email, SecurityQuestion question,
                                               String securityAnswer) {
        return null;
    }

    private static User toBeSignedIn;
    private static String randomPassword;
    private static String randomSlogan;

    public static SignUpMenuMessages initiateSignup(String username, String password, String passwordConfirmation,
                                                    String nickname, String email, String slogan) {
        reset();
        if (username == null || username.length() == 0) {
            return SignUpMenuMessages.EMPTY_USERNAME;
        }
        if (password == null || password.length() == 0) {
            return SignUpMenuMessages.EMPTY_PASSWORD;
        }
        if (nickname == null || nickname.length() == 0) {
            return SignUpMenuMessages.EMPTY_NICKNAME;
        }
        if (email == null || email.length() == 0) {
            return SignUpMenuMessages.EMPTY_EMAIL;
        }
        if (!Validation.isValidUsername(username)) {
            return SignUpMenuMessages.INVALID_USERNAME;
        }
        if (UserController.userWithUsernameExists(username)) {
            return SignUpMenuMessages.USERNAME_EXISTS;
        }
        if (!password.equals("random") && !password.equals(passwordConfirmation)) {
            return SignUpMenuMessages.PASSWORD_CONFIRMATION_WRONG;
        }
        if (!password.equals("random") && Validation.validatePassword(password).size() != 0) {
            passwordProblems = Validation.validatePassword(password);
            return SignUpMenuMessages.WEAK_PASSWORD;
        }
        if (!Validation.isValidEmail(email)) {
            return SignUpMenuMessages.INVALID_EMAIL;
        }
        if (UserController.userWithEmailExists(email)) {
            return SignUpMenuMessages.EMAIL_EXISTS;
        }
        if (slogan.equals("random")) {
            randomSlogan = Randoms.getSlogan();
            slogan = randomSlogan;
        }
        if (password.equals("random")) {
            randomPassword = Randoms.getPassword();
            password = randomPassword;
        }

        toBeSignedIn = new User(username, password, nickname, email, slogan, null, slogan);

        if (randomPassword != null)
            return SignUpMenuMessages.PASSWORD_CONFIRMATION_NEEDED;
        return SignUpMenuMessages.SIGN_UP_SUCCESSFUL;
    }

    public static String getPassword() {
        return randomPassword;
    }

    public static String getSlogan() {
        return randomSlogan;
    }

    public static void setSecurityQuestion(SecurityQuestion question, String answer, Connection connection) {
        toBeSignedIn.setSecurityQuestion(question);
        toBeSignedIn.setSecurityAnswer(answer);
        done(connection);
    }

    private static void done(Connection connection) {
        for (Chat chat : ChatDatabase.getChats()) {
            if (chat.type.equals(ChatType.PUBLIC)) chat.addUser(toBeSignedIn);
        }
        ServerUserController.signup(toBeSignedIn, connection);
        reset();
    }

    public static void reset() {
        toBeSignedIn = null;
        randomPassword = null;
    }
}
