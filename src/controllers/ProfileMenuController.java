package controllers;

import view.enums.ProfileMenuMessages;

import java.util.regex.*;

public class ProfileMenuController {
    public ProfileMenuMessages changeUsername(String newUsername) {
        return null; //returning the final message
    }

    public ProfileMenuMessages changeNickname(String newNickname) {
        return null;
    }

    public ProfileMenuMessages changePassword(String oldPassword, String newPassword) {
        return null;
    }

    public ProfileMenuMessages changeSlogan(String newSlogan) {
        return null;
    }

    public ProfileMenuMessages removeSlogan() {
        return null;
    }

    public ProfileMenuMessages changeEmail(String newEmail) {
        return null;
    }

    public static int showHighScore() {
        return MainMenuController.getCurrentUser().getHighScore();
    }

    public static int showRank() {
        return 0; //TODO
    }

    public static String displayInformation() {
        return MainMenuController.getCurrentUser().toString();
    }

    public static String showSlogan() {
        String slogan;
        if ((slogan = MainMenuController.getCurrentUser().getSlogan()).equals(null))
            return "Slogan is empty!";
        return slogan;
    }
}
