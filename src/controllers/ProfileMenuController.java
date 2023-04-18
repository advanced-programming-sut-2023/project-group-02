package controllers;

import utils.Validation;
import view.enums.ProfileMenuMessages;

public class ProfileMenuController {
    public static ProfileMenuMessages changeUsername(String newUsername) {
        if (Validation.isValidUsername(newUsername)) {
            UserController.getCurrentUser().setUsername(newUsername);
            return null;
        } else {
            return ProfileMenuMessages.INVALID_NEW_USERNAME;
        }
    }

    public static ProfileMenuMessages changeNickname(String newNickname) {
        if (newNickname != null && newNickname.length() > 0) {
            UserController.getCurrentUser().setNickname(newNickname);
            return null;
        } else {
            return ProfileMenuMessages.EMPTY_FIELD;
        }
    }

    public static ProfileMenuMessages changePassword(String oldPassword, String newPassword) {
        return null;
    }

    public static ProfileMenuMessages changeSlogan(String newSlogan) {
        if (newSlogan != null && newSlogan.length() > 0) {
            UserController.getCurrentUser().setSlogan(newSlogan);
            return null;
        } else {
            return ProfileMenuMessages.EMPTY_FIELD;
        }
    }

    public static ProfileMenuMessages removeSlogan() {
        UserController.getCurrentUser().setSlogan(null);
        return null;
    }

    public static ProfileMenuMessages changeEmail(String newEmail) {
        if (Validation.isValidEmail(newEmail)) {
            UserController.getCurrentUser().setEmail(newEmail);
            return null;
        } else {
            return ProfileMenuMessages.INVALID_EMAIL;
        }
    }
}
