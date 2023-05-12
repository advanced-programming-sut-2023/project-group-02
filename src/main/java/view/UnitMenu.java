package view;

import models.units.Unit;
import models.units.UnitState;
import models.units.UnitType;
import utils.Parser;
import utils.Utils;
import view.enums.UnitMenuMessages;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

import controllers.UnitMenuController;

public class UnitMenu {
    public void run(Scanner scanner) {
        while (true) {
            Parser parser = new Parser(scanner.nextLine());
            if (parser.beginsWith("move unit to")) {
                moveUnit(parser);
            } else if (parser.beginsWith("patrol unit")) {
                patrolUnit(parser);
            } else if (parser.beginsWith("set")) {
                setState(parser);
            } else if (parser.beginsWith("attack")) {
                attack(parser);
            } else if (parser.beginsWith("pouroil")) {
                pourOil(parser);
            } else if (parser.beginsWith("dig tunnel")) {
                digTunnel(parser);
            } else if (parser.beginsWith("build")) {
                build(parser);
            } else if (parser.beginsWith("disband unit")) {
                disbandUnit();
            } else if (parser.beginsWith("exit")) {
                System.out.println("You came back to the game!");
                break;
            } else if (parser.beginsWith("show current menu")) {
                System.out.println("You are at Unit Menu");
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    void moveUnit(Parser parser) {
        if (!Utils.isInteger(parser.get("x")) || !Utils.isInteger(parser.get("y"))) {
            System.out.println("Invalid numbers for x and y");
            return;
        }
        UnitMenuMessages message = UnitMenuController.moveUnit(Integer.parseInt(parser.get("x")),
                Integer.parseInt(parser.get("y")));
        System.out.println(message.getMessage());
    }

    void patrolUnit(Parser parser) {

    }

    void setState(Parser parser) {
        String stateName = parser.get("s");
        UnitState state = switch (stateName) {
            case "standing" -> UnitState.STANDING;
            case "offensive" -> UnitState.OFFENSIVE;
            case "defensive" -> UnitState.DEFENSIVE;
            default -> null;
        };

        if (state == null) {
            System.out.println("Invalid state");
            return;
        }

        UnitMenuController.setState(state);
        System.out.println("State set successfully");
    }

    void attack(Parser parser) {

    }

    void pourOil(Parser parser) {

    }

    void digTunnel(Parser parser) {
        if (!Utils.isInteger(parser.get("x")) || !Utils.isInteger(parser.get("y"))) {
            System.out.println("Please import valid numbers in x and y field!");
            return;
        }
        if (!UnitMenuController.selectedUnitsType().equals(UnitType.TUNNELER)) {
            System.out.println("Selected unit cannot dig tunnels!");
            return;
        }
        int x = Integer.parseInt(parser.get("x"));
        int y = Integer.parseInt(parser.get("y"));
        UnitMenuMessages message = UnitMenuController.digTunnel(x,y);
        System.out.println(message.getMessage());
    }

    void build(Parser parser) {

    }

    void disbandUnit() {

    }
}
