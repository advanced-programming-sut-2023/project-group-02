package view;

import controllers.BuildingMenuController;
import models.Building;
import utils.Parser;
import utils.Utils;
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
        if (!parser.getFlag("-t") || !parser.getFlag("-c") || !Utils.isInteger(parser.get("-c"))) {
            System.out.println("Some fields aren't filled or filled wrong!");
            return;
        }
        if (!(selectedBuilding.getName().equals("Barracks") || selectedBuilding.getName().equals("Mercenary Post")
            || selectedBuilding.getName().equals("Engineer Guild"))) {
            System.out.println("You can't create unit from this building!");
            return;
        }
        System.out.println(BuildingMenuController.createUnit(parser.get("-t"), Integer.parseInt(parser.get("-c"))));
    }

    void repair() {
        BuildingMenuMessages message = BuildingMenuController.repair(selectedBuilding);
        System.out.println(message.getMessage());
    }
}
