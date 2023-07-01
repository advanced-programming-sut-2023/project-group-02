package server.logic;

import client.view.enums.LoginMenuMessages;
import models.User;
import server.ServerUserController;
import utils.PasswordProblem;
import utils.Validation;

import java.util.ArrayList;
import java.util.Date;

public class Login {
    public static ArrayList<PasswordProblem> passwordProblems;
    static int attempts = 0;
    static Date lastAttempt;

    static LoginMenuMessages loginWithoutRatelimit(String username, String password) {
        if (username == null || password == null)
            return LoginMenuMessages.EMPTY_FIELD;
        if (!ServerUserController.userWithUsernameExists(username))
            return LoginMenuMessages.USERNAME_DOESNT_EXIST;
        if (!ServerUserController.findUserWithUsername(username).passwordEquals(password))
            return LoginMenuMessages.UNMATCHED_USERNAME_PASSWORD;

        ServerUserController.login(ServerUserController.findUserWithUsername(username));
        return LoginMenuMessages.LOGIN_SUCCESSFUL;
    }

    public static LoginMenuMessages login(String username, String password) {
        if (attempts > 0) {
            if (new Date().getTime() - lastAttempt.getTime() > 1000 * attempts * 5) {
                attempts = 0;
                lastAttempt = null;
            } else {
                return LoginMenuMessages.TOO_MANY_REQUESTS;
            }
        }
        LoginMenuMessages message = loginWithoutRatelimit(username, password);
        if (message.equals(LoginMenuMessages.LOGIN_SUCCESSFUL)) {
            attempts = 0;
            lastAttempt = null;
        } else {
            attempts++;
            lastAttempt = new Date();
        }
        return message;
    }

    public static LoginMenuMessages setNewPassword(User user, String newPassword) {
        if (Validation.validatePassword(newPassword).size() > 0) {
            passwordProblems = Validation.validatePassword(newPassword);
            return LoginMenuMessages.NEW_PASSWORD_WEAK;
        }
        user.setPassword(newPassword);
        return LoginMenuMessages.PASSWORD_IS_CHANGED;
    }
}