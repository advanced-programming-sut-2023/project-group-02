package view;

import models.units.Unit;
import utils.Parser;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class UnitMenu {
    private final ArrayList<Unit> selectedUnits;

    public UnitMenu(ArrayList<Unit> selectedUnits) {
        this.selectedUnits = selectedUnits;
    }

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

    }

    void patrolUnit(Parser parser) {

    }

    void setState(Parser parser) {

    }

    void attack(Parser parser) {

    }

    void pourOil(Parser parser) {

    }

    void digTunnel(Parser parser) {

    }

    void build(Parser parser) {

    }

    void disbandUnit() {

    }
}
