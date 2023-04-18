package view;

import controllers.ProfileMenuController;
import controllers.UserController;
import utils.Parser;
import utils.Validation;

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
                // TODO: change password
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
        ProfileMenuController.removeSlogan();
    }

    void changeSlogan(Parser parser) {
        ProfileMenuController.changeSlogan(parser.get("s"));
    }

    void changeInfo(Parser parser) {
        if (parser.get("u") != null) {
            if (Validation.isValidUsername(parser.get("u"))) {
                UserController.getCurrentUser().setUsername(parser.get("u"));
            } else {
                System.out.println("Invalid username format!");
            }
        } else if (parser.get("n") != null) {
            UserController.getCurrentUser().setNickname(parser.get("n"));
        } else if (parser.get("e") != null) {
            if (Validation.isValidEmail(parser.get("e"))) {
                UserController.getCurrentUser().setEmail(parser.get("e"));
            } else {
                System.out.println("Invalid email format!");
            }
        } else {
            System.out.println("Invalid command!");
        }
    }

    void showHighScore() {
        System.out.println("Highscore: " + UserController.getCurrentUser().getHighScore());
    }

    void showRank() {
        System.out.println("Rank: " + UserController.getUserRank(UserController.getCurrentUser()));
    }

    void showSlogan() {
        String slogan = UserController.getCurrentUser().getSlogan();
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
}
