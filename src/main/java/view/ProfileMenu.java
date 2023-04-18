package view;

import controllers.ProfileMenuController;
import controllers.MainController;
import utils.Parser;
import utils.Validation;
import view.enums.ProfileMenuMessages;

import java.util.Scanner;

public class ProfileMenu {
    public void run(Scanner scanner) {
        while (true) {
            Parser parser = new Parser(scanner.nextLine());
            if (parser.beginsWith("profile change slogan")) {
                changeSlogan(parser);
            } else if (parser.beginsWith("profile remove slogan")) {
                removeSlogan();
            } else if (parser.beginsWith("profile change password")) {
                changePassword(parser);
            } else if (parser.beginsWith("profile change")) {
                changeInfo(parser);
            } else if (parser.beginsWith("profile display highscore")) {
                showHighScore();
            } else if (parser.beginsWith("profile display rank")) {
                showRank();
            } else if (parser.beginsWith("profile display slogan")) {
                showSlogan();
            } else if (parser.beginsWith("profile display")) {
                showProfile();
            }
        }
    }

    void removeSlogan() {
        ProfileMenuMessages message = ProfileMenuController.removeSlogan();
        if (message.equals(ProfileMenuMessages.SUCCESSFUL)) {
            System.out.println("Slogan is removed successfully!");
        } else if (message.equals(ProfileMenuMessages.EMPTY_FIELD)) {
            System.out.println("Slogan field is already empty!");
        }
    }

    void changeSlogan(Parser parser) {
        ProfileMenuMessages message = ProfileMenuController.changeSlogan(parser.get("s"));

        if (message.equals(ProfileMenuMessages.SUCCESSFUL)) {
            System.out.println("Slogan is changed successfully!");
        } else {
            System.out.println("Invalid command!");
        }
    }

    void changeInfo(Parser parser) {
        ProfileMenuMessages message;
        if (parser.get("u") != null) {
            message = ProfileMenuController.changeUsername(parser.get("u"));
            if (message.equals(ProfileMenuMessages.SUCCESSFUL)) {
                System.out.println("Changing username was successful!");
            } else {
                System.out.println("Invalid username format!");
            }

        } else if (parser.get("n") != null) {
            message = ProfileMenuController.changeNickname(parser.get("n"));
            if (message.equals(ProfileMenuMessages.SUCCESSFUL)) {
                System.out.println("Changing nickname was successful!");
            } else {
                System.out.println("Invalid nickname!");
            }
        } else if (parser.get("e") != null) {
            message = ProfileMenuController.changeEmail(parser.get("e"));
            if (message.equals(ProfileMenuMessages.SUCCESSFUL)) {
                System.out.println("Email is changed successfully!");
            } else {
                System.out.println("Invalid email format!");
            }
        } else {
            System.out.println("Invalid command!");
        }
    }

    void showHighScore() {
        System.out.println("Highscore: " + MainController.getCurrentUser().getHighScore());
    }

    void showRank() {
        System.out.println("Rank: " + MainController.getUserRank(MainController.getCurrentUser()));
    }

    void showSlogan() {
        String slogan = MainController.getCurrentUser().getSlogan();
        if (slogan != null) {
            System.out.println("Slogan: " + slogan);
        } else {
            System.out.println("No slogan");
        }
    }

    void showProfile() {
        showRank();
        showHighScore();
        showSlogan();
    }

    void changePassword(Parser parser) {
        ProfileMenuMessages message = ProfileMenuController.changePassword(parser.get("o"),parser.get("n"));

        switch (message) {
            case SAME_THING -> System.out.println("Please enter a new password!");
            case WEAK_NEW_PASSWORD -> System.out.println("");
            case INCORRECT_OLD_PASSWORD -> System.out.println("Current password is incorrect!");
            case SUCCESSFUL -> System.out.println("Password is changed successfully");
        }
    }
}
