package view;

import controllers.GameMenuController;
import controllers.MapMenuController;
import models.Game;
import models.Map;
import utils.Parser;
import view.enums.GameMenuMessages;
import view.enums.MainMenuMessages;
import view.enums.MapMenuMessages;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {
    public void run(Scanner scanner) {
        while (true) {
            System.out.print("Do you want to start a new game? (yes or no): ");
            Parser parser = new Parser(scanner.nextLine());
            if (parser.beginsWith("yes")) {
                runPreGameMenu(scanner);
                break;
            } else if (parser.beginsWith("no")) {
                // TODO : option to load a saved game
                loadGame();
            } else if (parser.beginsWith("show current menu")) {
                System.out.println("You are at GamaMenu");
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    public void runPreGameMenu(Scanner scanner) {
        int numberOfTurns = setNumberOfTurns();
        int mapWidth = setMapWidth();
        int mapHeight = setMapHeight();
        GameMenuController.setCurrentGame(new Game(new ArrayList<>(), numberOfTurns, new Map(mapWidth, mapHeight)));
        while (true) {
            Parser parser = new Parser(scanner.nextLine());
            if (parser.beginsWith("droprock")) {
                dropRock(parser);
            } else if (parser.beginsWith("droptree")) {
                dropTree(parser);
            } else if (parser.beginsWith("settexture")) {
                setTexture(parser);
            } else if (parser.beginsWith("clear")) {
                clearBlock(parser);
            } else if (parser.beginsWith("exit")) {
                System.out.println("You came back to the main menu!");
                break;
            } else if (parser.beginsWith("show current menu")) {
                System.out.println("You are at GamaMenu");
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    public void runGameMenu(Scanner scanner) {
        initGovernments();
        while (true) {
            Parser parser = new Parser(scanner.nextLine());
            if (parser.beginsWith("select building")) {
                selectBuilding(parser, scanner);
            } else if (parser.beginsWith("select unit")) {
                selectUnit(parser, scanner);
            } else if (parser.beginsWith("enter trade menu")) {
                TradeMenu.run(scanner);
            } else if (parser.beginsWith("show current menu")) {
                System.out.println("You are at GamaMenu");
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    private void initGovernments() {
    }

    private void loadGame() {
    }

    private int setMapHeight() {
        return 0; // TODO compelte this
    }

    private int setMapWidth() {
        return 0; // TODO compelte this
    }

    private int setNumberOfTurns() {
        return 0; // TODO compelte this
    }

    void showMap(Parser parser, Scanner scanner) {
        int x = Integer.parseInt(parser.get("x"));
        int y = Integer.parseInt(parser.get("y"));

        GameMenuMessages message = GameMenuController.showMap(x, y);
        if (message.equals(GameMenuMessages.INVALID_PLACE))
            System.out.println("The numbers are invalid!");
        if (message.equals(GameMenuMessages.DONE_SUCCESSFULLY)) {
            System.out.println(GameMenuController.getCurrentGame().getMap().printMiniMap(x, y));
            MapMenuController.setCurrentXAndY(x, y);
            new MapMenu().run(scanner);
        }
    }

    void showPopularity() {

    }

    void showFoodList() {

    }

    void setFoodRate(Parser parser) {

    }

    void showFoodRate() {

    }

    void setTaxRate(Parser parser) {

    }

    void showTaxRate() {

    }

    void setFearRate(Parser parser) {

    }

    void dropBuilding(Parser parser) {

    }

    void selectBuilding(Parser parser, Scanner scanner) {
        int x = Integer.parseInt(parser.get("x"));
        int y = Integer.parseInt(parser.get("y"));
        GameMenuMessages message = GameMenuController.selectBuilding(x, y, scanner);

        switch (message) {
            case INVALID_PLACE -> System.out.println("The numbers are invalid!");
            case NO_BUILDINGS -> System.out.println("There is no buildings in this place!");
            case NOT_YOURS -> System.out.println("The building in this place doesn't belong to you!");
            case DONE_SUCCESSFULLY -> System.out.println("Out of building menu!"); // TODO this may make bugs
        }
    }

    void dropUnit(Parser parser) {

    }

    void selectUnit(Parser parser, Scanner scanner) {
        int x = Integer.parseInt(parser.get("x"));
        int y = Integer.parseInt(parser.get("y"));
        GameMenuMessages message = GameMenuController.selectUnit(x, y, scanner);

        switch (message) {
            case INVALID_PLACE -> System.out.println("The numbers are invalid!");
            case NO_UNITS -> System.out.println("There is no units here!");
            case DONE_SUCCESSFULLY -> System.out.println("Out of unit menu!"); // this may have bugs too
        }
    }

    void setTexture(Parser parser) {
        GameMenuMessages message;
        if (parser.get("x1") != null) {
            message = GameMenuController.setTexture
                (Integer.parseInt(parser.get("x1")), Integer.parseInt(parser.get("y1"))
                    , Integer.parseInt(parser.get("x2")), Integer.parseInt(parser.get("y2"))
                    , parser.get("t"));
        } else {
            message = GameMenuController.setTexture
                (Integer.parseInt(parser.get("x")), Integer.parseInt(parser.get("y")), parser.get("t"));
        }

        switch (message) {
            case INVALID_PLACE -> System.out.println("Your numbers are invalid!");
            case INVALID_TEXTURE -> System.out.println("There is no texture with this name!");
            case FULL_CELL -> System.out.println("This place is already full!");
            case DONE_SUCCESSFULLY -> System.out.println("Texture is changed successfully!");
        }
    }

    void clearBlock(Parser parser) {
        GameMenuMessages messages = GameMenuController.clearBlock(Integer.parseInt(parser.get("x")), Integer.parseInt(parser.get("y")));

        if (messages.equals(GameMenuMessages.INVALID_PLACE)) System.out.println("Numbers are invalid!");
        if (messages.equals(GameMenuMessages.DONE_SUCCESSFULLY)) System.out.println("Block is cleared successfully!");
    }

    void dropRock(Parser parser) {
        int x = Integer.parseInt(parser.get("x"));
        int y = Integer.parseInt(parser.get("y"));
        GameMenuMessages message = GameMenuController.dropRock(x, y, parser.get("d"));

        switch (message) {
            case INVALID_PLACE -> System.out.println("Numbers are invalid!");
            case INVALID_DIRECTION -> System.out.println("Direction is invalid!");
            case FULL_CELL -> System.out.println("This cell is already full!");
            case DONE_SUCCESSFULLY -> System.out.println("The rock is dropped successfully!");
        }
    }

    void dropTree(Parser parser) {
        int x = Integer.parseInt(parser.get("x"));
        int y = Integer.parseInt(parser.get("y"));
        GameMenuMessages message = GameMenuController.dropTree(x, y, parser.get("t"));

        switch (message) {
            case INVALID_PLACE -> System.out.println("Numbers are invalid!");
            case INVALID_TREE_NAME -> System.out.println("There is no trees with this name!");
            case FULL_CELL -> System.out.println("This cell is already full!");
            case DONE_SUCCESSFULLY -> System.out.println("The tree is dropped successfully!");
        }
    }

    void nextTurn() {

    }
}
