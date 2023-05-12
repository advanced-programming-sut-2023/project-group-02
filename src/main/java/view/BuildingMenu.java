package view;

import controllers.BuildingMenuController;
import models.Building;
import utils.Parser;
import view.enums.BuildingMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class BuildingMenu {
    private final Building selectedBuilding;

    public BuildingMenu(Building selectedBuilding) {
        this.selectedBuilding = selectedBuilding;
    }

    public void run(Scanner scanner) {
        while (true) {
            Parser parser = new Parser(scanner.nextLine());
            if (parser.beginsWith("create unit")) {
                createUnit(parser);
            } else if (parser.beginsWith("repair")) {
                repair();
            } else if (parser.beginsWith("enter shop menu")) {
                if (!selectedBuilding.getName().equals("Shop")) {
                    System.out.println("You can't enter shop menu from other buildings!");
                } else {
                    System.out.println("You entered shop menu!");
                    new ShopMenu().run(scanner);
                }
            } else if (parser.beginsWith("exit")) {
                System.out.println("You came back to Game!");
                break;
            } else if (parser.beginsWith("show current menu")) {
                System.out.println("You are at Building Menu");
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    void createUnit(Parser parser) {

    }

    void repair() {
        BuildingMenuMessages message = BuildingMenuController.repair(selectedBuilding);
        System.out.println(message.getMessage());
    }
}
