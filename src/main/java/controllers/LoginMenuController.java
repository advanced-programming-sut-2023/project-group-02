package controllers;

import models.User;
import utils.Validation;
import view.enums.LoginMenuMessages;

import java.util.Scanner;

public class LoginMenuController {
    public static LoginMenuMessages login(String username, String password, boolean isStayLogin) {
        if (username == null || password == null)
            return LoginMenuMessages.EMPTY_FIELD;
        if (!MainController.userWithUsernameExists(username))
            return LoginMenuMessages.USERNAME_DOESNT_EXIST;
        if (!MainController.findUserWithUsername(username).getPassword().equals(password))
            return LoginMenuMessages.UNMATCHED_USERNAME_PASSWORD;

        MainController.setCurrentUser(MainController.findUserWithUsername(username));
        //TODO something for stay logged in
        return LoginMenuMessages.LOGIN_SUCCESSFUL;
    }

    public static LoginMenuMessages forgotPassword(String username , Scanner scanner) {
        User myUser;
        if ((myUser = MainController.findUserWithUsername(username)) == null)
            return LoginMenuMessages.USERNAME_DOESNT_EXIST;

        System.out.println(myUser.getSecurityQuestion().fullSentence);

        String usersAnswer = scanner.nextLine();
        if (!usersAnswer.equals(myUser.getSecurityAnswer()))
            return LoginMenuMessages.WRONG_SECURITY_ANSWER;

        return LoginMenuMessages.ENTER_NEW_PASSWORD;
    }

    public static LoginMenuMessages setNewPassword(String username, String newPassword) {
        User myUser = MainController.findUserWithUsername(username);
        if (Validation.validatePassword(newPassword).size() > 0)
            return LoginMenuMessages.NEW_PASSWORD_WEAK;

        myUser.setPassword(newPassword);
        return LoginMenuMessages.PASSWORD_IS_CHANGED;
    }

    public static LoginMenuMessages logout() {
        if (MainController.getCurrentUser().equals(null))
            return LoginMenuMessages.ALREADY_LOGGED_OUT;
        MainController.setCurrentUser(null);
        return LoginMenuMessages.LOGOUT_SUCCESSFUL;
    }
}
