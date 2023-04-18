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

    int showHighScore() {
        return MainController.getCurrentUser().getHighScore();
    }

    int showRank() {
        return 0; // TODO
    }

    String displayInformation() {
        return MainController.getCurrentUser().toString();
    }

    String showSlogan() {
        String slogan;
        if ((slogan = MainController.getCurrentUser().getSlogan()).equals(null))
            return "Slogan is empty!";
        return slogan;
    }
}
