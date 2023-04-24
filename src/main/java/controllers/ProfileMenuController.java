package controllers;

import utils.Validation;
import view.enums.LoginMenuMessages;
import view.enums.ProfileMenuMessages;

public class ProfileMenuController {
    public static ProfileMenuMessages changeUsername(String newUsername) {
        if (Validation.isValidUsername(newUsername)) {
            UserController.getCurrentUser().setUsername(newUsername);
            return ProfileMenuMessages.SUCCESSFUL;
        } else {
            return ProfileMenuMessages.INVALID_NEW_USERNAME;
        }
    }

    public static ProfileMenuMessages changeNickname(String newNickname) {
        if (newNickname != null && newNickname.length() > 0) {
            UserController.getCurrentUser().setNickname(newNickname);
            return ProfileMenuMessages.SUCCESSFUL;
        } else {
            return ProfileMenuMessages.EMPTY_FIELD;
        }
    }

    public static ProfileMenuMessages changePassword(String oldPassword, String newPassword) {
        if (!UserController.getCurrentUser().passwordEquals(oldPassword)) {
            return ProfileMenuMessages.INCORRECT_OLD_PASSWORD;
        }
        if (oldPassword.equals(newPassword)) {
            return ProfileMenuMessages.SAME_THING;
        }
        if (Validation.validatePassword(newPassword).size() == 0) {
            return ProfileMenuMessages.WEAK_NEW_PASSWORD;
        }

        UserController.getCurrentUser().setPassword(newPassword);
        return ProfileMenuMessages.SUCCESSFUL;
    }

    public static ProfileMenuMessages changeSlogan(String newSlogan) {
        if (newSlogan != null && newSlogan.length() > 0) {
            UserController.getCurrentUser().setSlogan(newSlogan);
            return ProfileMenuMessages.SUCCESSFUL;
        } else {
            return ProfileMenuMessages.EMPTY_FIELD;
        }
    }

    public static ProfileMenuMessages removeSlogan() {
        if (UserController.getCurrentUser().getSlogan().equals(null))
            return ProfileMenuMessages.EMPTY_FIELD;
        UserController.getCurrentUser().setSlogan(null);
        return ProfileMenuMessages.SUCCESSFUL;
    }

    public static ProfileMenuMessages changeEmail(String newEmail) {
        if (Validation.isValidEmail(newEmail)) {
            UserController.getCurrentUser().setEmail(newEmail);
            return ProfileMenuMessages.SUCCESSFUL;
        } else {
            return ProfileMenuMessages.INVALID_EMAIL;
        }
    }
}
