package controllers;

import models.SecurityQuestion;
import models.User;
import utils.Randoms;
import utils.Validation;
import view.enums.ProfileMenuMessages;
import view.enums.SignUpMenuMessages;

public class SignUpMenuController {
    public static ProfileMenuMessages register(String username, String password,
            String nickname, String slogan, String email, SecurityQuestion question,
            String securityAnswer) {
        return null;
    }

    private static User toBeSignedIn;

    public static SignUpMenuMessages initiateSignup(String username, String password, String passwordConfirmation,
            String nickname, String email, String slogan) {
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
            // TODO: return detailed errors on why password is weak
            return SignUpMenuMessages.WEAK_PASSWORD;
        }
        if (!Validation.isValidEmail(email)) {
            return SignUpMenuMessages.INVALID_EMAIL;
        }
        if (UserController.userWithEmailExists(email)) {
            return SignUpMenuMessages.EMAIL_EXISTS;
        }
        if (password.equals("random")) {
            toBeSignedIn = new User(username, Randoms.getPassword(), nickname, email, slogan, null, slogan);
            return SignUpMenuMessages.PASSWORD_CONFIRMATION_NEEDED;
        }
        toBeSignedIn = new User(username, password, nickname, email, slogan, null, slogan);
        return null;
    }

    public static String getPassword() {
        return toBeSignedIn.getPassword();
    }

    public static void setSecurityQuestion(SecurityQuestion question, String answer) {
        toBeSignedIn.setSecurityQuestion(question);
        toBeSignedIn.setSecurityAnswer(answer);
        done();
    }

    private static void done() {
        UserController.signup(toBeSignedIn);
        toBeSignedIn = null;
    }
}