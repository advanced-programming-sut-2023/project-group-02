package controllers;

import models.User;
import utils.PasswordProblem;
import utils.Validation;
import view.enums.LoginMenuMessages;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

public class LoginMenuController {
    public static ArrayList<PasswordProblem> passwordProblems;
    static int attempts = 0;
    static Date lastAttempt;

    static LoginMenuMessages loginWithoutRatelimit(String username, String password, boolean stayLoggedIn) {
        if (username == null || password == null)
            return LoginMenuMessages.EMPTY_FIELD;
        if (!UserController.userWithUsernameExists(username))
            return LoginMenuMessages.USERNAME_DOESNT_EXIST;
        if (!UserController.findUserWithUsername(username).passwordEquals(password))
            return LoginMenuMessages.UNMATCHED_USERNAME_PASSWORD;

        UserController.login(UserController.findUserWithUsername(username), stayLoggedIn);
        return LoginMenuMessages.LOGIN_SUCCESSFUL;
    }

    public static LoginMenuMessages login(String username, String password, boolean stayLoggedIn) {
        if (attempts > 0) {
            if (new Date().getTime() - lastAttempt.getTime() > 1000 * attempts * 5) {
                attempts = 0;
                lastAttempt = null;
            } else {
                return LoginMenuMessages.TOO_MANY_REQUESTS;
            }
        }
        LoginMenuMessages message = loginWithoutRatelimit(username, password, stayLoggedIn);
        if (message.equals(LoginMenuMessages.LOGIN_SUCCESSFUL)) {
            attempts = 0;
            lastAttempt = null;
        } else {
            attempts++;
            lastAttempt = new Date();
        }
        return message;
    }

    public static LoginMenuMessages forgotPassword(String username, Scanner scanner) {
        User myUser;
        if ((myUser = UserController.findUserWithUsername(username)) == null)
            return LoginMenuMessages.USERNAME_DOESNT_EXIST;

        System.out.println(myUser.getSecurityQuestion().fullSentence);

        String usersAnswer = scanner.nextLine();
        if (!usersAnswer.equals(myUser.getSecurityAnswer()))
            return LoginMenuMessages.WRONG_SECURITY_ANSWER;

        return LoginMenuMessages.ENTER_NEW_PASSWORD;
    }

    public static LoginMenuMessages setNewPassword(String username, String newPassword) {
        User myUser = UserController.findUserWithUsername(username);
        if (Validation.validatePassword(newPassword).size() > 0) {
            passwordProblems = Validation.validatePassword(newPassword);
            return LoginMenuMessages.NEW_PASSWORD_WEAK;
        }

        myUser.setPassword(newPassword);
        return LoginMenuMessages.PASSWORD_IS_CHANGED;
    }
}
