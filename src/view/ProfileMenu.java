package view;

import controllers.MainController;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu {
    public static void run(Scanner scanner) {

    }

    public static void changeUsername(Matcher matcher) {

    }

    public static void changeNickname(Matcher matcher) {

    }

    public static void changePassword(Matcher matcher) {

    }

    public static void changeEmail(Matcher matcher) {

    }

    public static void changeSlogan(Matcher matcher) {

    }

    public static void removeSlogan(Matcher matcher) {

    }

    public static int showHighScore() {
        return MainController.getCurrentUser().getHighScore();
    }

    public static int showRank() {
        return 0; //TODO
    }

    public static String displayInformation() {
        return MainController.getCurrentUser().toString();
    }

    public static String showSlogan() {
        String slogan;
        if ((slogan = MainController.getCurrentUser().getSlogan()).equals(null))
            return "Slogan is empty!";
        return slogan;
    }
}
