package controllers;

import java.util.regex.*;

public class ProfileMenuController {
    public static int showHighScore() {
        return MainMenuController.getCurrentUser().getHighScore();
    }

    public static int showRank() {
        return 0; //TODO
    }

    public static String displayInformation() {
        return ""; //TODO
    }

    public static String showSlogan() {
        String slogan;
        if ((slogan = MainMenuController.getCurrentUser().getSlogan()).equals(null))
            return "Slogan is empty!";
        return slogan;
    }
}
