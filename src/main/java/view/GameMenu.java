package view;

import controllers.GameMenuController;
import controllers.MapMenuController;
import controllers.UserController;
import jdk.jshell.execution.Util;
import models.*;
import utils.Parser;
import utils.Utils;
import view.enums.GameMenuMessages;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameMenu {
    public void run(Scanner scanner) {
        while (true) {
            System.out.print("Do you want to start a new game? (yes or no): ");
            Parser parser = new Parser(scanner.nextLine());
            if (parser.beginsWith("yes")) {
                runPreGameMenu(scanner);
                break;
            } else if (parser.beginsWith("no")) {
                loadGame(scanner);
                break;
            } else if (parser.beginsWith("show current menu")) {
                System.out.println("You are at GameMenu");
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    public void runPreGameMenu(Scanner scanner) {
        int numberOfTurns = setNumberOfTurns(scanner);
        int mapWidth = setMapWidth(scanner);
        int mapHeight = setMapHeight(scanner);
        GameMenuController.setCurrentGame(new Game(new ArrayList<>(), numberOfTurns, new Map(mapWidth, mapHeight)));
        System.out.println("Now you can initialize map. type next to continue.");
        while (true) {
            Parser parser = new Parser(scanner.nextLine());
            if (parser.beginsWith("droprock")) {
                dropRock(parser);
            } else if (parser.beginsWith("droptree")) {
                dropTree(parser);
            } else if (parser.beginsWith("settexture")) {
                setTexture(parser);
            } else if (parser.beginsWith("drop building")) {
                dropBuilding(parser, false);
            } else if (parser.beginsWith("drop unit")) {
                dropUnit(parser);
            } else if (parser.beginsWith("clear")) {
                clearBlock(parser);
            } else if (parser.beginsWith("exit")) {
                System.out.println("You came back to the main menu!");
                break;
            } else if (parser.beginsWith("next")) {
                initGovernments(scanner);
                GameMenuController.saveGame();
                runGameMenu(scanner);
                break;
            } else if (parser.beginsWith("show current menu")) {
                System.out.println("You are at GameMenu");
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    public void runGameMenu(Scanner scanner) {
        while (true) {
            Parser parser = new Parser(scanner.nextLine());
            if (parser.beginsWith("drop building")) {
                dropBuilding(parser, true);
            } else if (parser.beginsWith("select building")) {
                selectBuilding(parser, scanner);
            } else if (parser.beginsWith("show map")) {
                showMap(parser, scanner);
            } else if (parser.beginsWith("select unit")) {
                selectUnit(parser, scanner);
            } else if (parser.beginsWith("enter trade menu")) {
                new TradeMenu().run(scanner);
            } else if (parser.beginsWith("show popularity factors")) {
                showPopularityFactors();
            } else if (parser.beginsWith("show popularity")) {
                showPopularity();
            } else if (parser.beginsWith("show food list")) {
                showFoodList();
            } else if (parser.beginsWith("food rate show")) {
                showFoodRate();
            } else if (parser.beginsWith("food rate")) {
                setFoodRate(parser);
            } else if (parser.beginsWith("tax rate show")) {
                showTaxRate();
            } else if (parser.beginsWith("tax rate")) {
                setTaxRate(parser);
            } else if (parser.beginsWith("fear rate")) {
                setFearRate(parser);
            } else if (parser.beginsWith("save")) {
                GameMenuController.saveGame();
                System.out.println("Game saved successfully!");
            } else if (parser.beginsWith("exit")) {
                System.out.println("You came back to the main menu!");
                break;
            } else if (parser.beginsWith("show current menu")) {
                System.out.println("You are at GameMenu");
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    private void initGovernments(Scanner scanner) {
        System.out.print("Enter the number of governments: ");
        int numberOfGovernments;
        while (true) {
            numberOfGovernments = Utils.getValidInt(scanner);
            if (numberOfGovernments <= 8)
                break;
            System.out.println("The number of governments should be at most 8!");
        }
        for (int i = 0; i < numberOfGovernments; i++) {
            while (true) {
                System.out.print("Enter the username of player you want to add: ");
                String username = scanner.nextLine().trim();
                if (!UserController.userWithUsernameExists(username))
                    System.out.println("user with this username doesn't exist!");
                else if (GameMenuController.getCurrentGame().getPlayerByUsername(username) != null)
                    System.out.println("player already in game!");
                else {
                    User player = UserController.findUserWithUsername(username);
                    int[] colors = new int[8];
                    Colors color = pickColor(colors, scanner);
                    dropSmallStoneGate(player, scanner);
                    GameMenuController.addPlayerToGame(player, color);
                    break;
                }
            }
        }
    }

    private void dropSmallStoneGate(User player, Scanner scanner) {
        System.out.println("select coordinates for this user's small stone gate\nthe format should be \"-x <x> -y <y>\"");
        while (true) {
            Parser parser = new Parser(scanner.nextLine());
            GameMenuMessages message = GameMenuController.dropSmallStoneGate(parser);
            System.out.println(message.getMessage());
            if (message == GameMenuMessages.DONE_SUCCESSFULLY)
                break;
        }
    }

    private Colors pickColor(int[] colors, Scanner scanner) {
        System.out.println("Pick a color for this player: ");
        System.out.println("Blue: 1 | Red: 2 | Yellow: 3 | Green: 4 | Black: 5 | White: 6 | Purple: 7 | Pink: 8");
        while (true) {
            int input = Utils.getValidInt(scanner);
            if (input > 8)
                System.out.println("Invalid input! number should be at most 8!");
            else if (colors[input - 1] != 0)
                System.out.println("This color is already picked!");
            else {
                colors[input - 1] = 1;
                return Colors.values()[input - 1];
            }
        }
    }

    private void loadGame(Scanner scanner) {
        if (GameMenuController.loadGame()) {
            System.out.println("game loaded. you can play now.");
            runGameMenu(scanner);
        } else {
            System.out.println("no game is saved! a new game is started.");
            runPreGameMenu(scanner);
        }
    }

    private int setMapHeight(Scanner scanner) {
        System.out.print("Enter the height of the map: ");
        return Utils.getValidInt(scanner);
    }

    private int setMapWidth(Scanner scanner) {
        System.out.print("Enter the width of the map: ");
        return Utils.getValidInt(scanner);
    }

    private int setNumberOfTurns(Scanner scanner) {
        System.out.print("Enter the number of turns: ");
        return Utils.getValidInt(scanner);
    }

    void showMap(Parser parser, Scanner scanner) {
        if (!Utils.isInteger(parser.get("x")) || !Utils.isInteger(parser.get("y"))) {
            System.out.println("Please import numbers!");
            return;
        }
        int x = Integer.parseInt(parser.get("x"));
        int y = Integer.parseInt(parser.get("y"));

        GameMenuMessages message = GameMenuController.showMap(x, y);
        if (message.equals(GameMenuMessages.INVALID_PLACE))
            System.out.println("The numbers are invalid!");
        if (message.equals(GameMenuMessages.DONE_SUCCESSFULLY)) {
            System.out.println(GameMenuController.getCurrentGame().getMap().printMiniMap(x, y));
            new MapMenu(x, y).run(scanner);
        }
    }

    void showPopularityFactors() {
        Government gov = GameMenuController.getCurrentGame().getCurrentPlayersGovernment();
        showFoodRate();
        System.out.println("Food type count: " + gov.getFoodStock().size());
        System.out.println(
            "Fear rate: " + gov.getFearRate());
        showTaxRate();
    }

    void showPopularity() {
        System.out.println(
            "Popularity: " + GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getPopularity());
    }

    void showFoodList() {
        System.out.println("Foods:");
        for (Food food : GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getFoodStock()) {
            System.out.println(food.name()); // TODO: use a more human-friendly name
        }
    }

    void setFoodRate(Parser parser) {
        String r = parser.get("r");
        if (!Utils.isInteger(r)) {
            System.out.println("Invalid number!");
            return;
        }
        GameMenuMessages message = GameMenuController.setFoodRate(Integer.parseInt(r));
        System.out.println(message.getMessage());
    }

    void showFoodRate() {
        System.out.println(
            "Food rate: " + GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getFoodRate());
    }

    void setTaxRate(Parser parser) {
        String r = parser.get("r");
        if (!Utils.isInteger(r)) {
            System.out.println("Invalid number!");
            return;
        }
        GameMenuMessages message = GameMenuController.setTaxRate(Integer.parseInt(r));
        System.out.println(message.getMessage());
    }

    void showTaxRate() {
        System.out
            .println("Tax rate: " + GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getTaxRate());
    }

    void setFearRate(Parser parser) {
        String r = parser.get("r");
        if (!Utils.isInteger(r)) {
            System.out.println("Invalid number!");
            return;
        }
        GameMenuMessages message = GameMenuController.setFearRate(Integer.parseInt(r));
        System.out.println(message.getMessage());
    }

    void dropBuilding(Parser parser, boolean useMaterials) {
        int x = Integer.parseInt(parser.get("x"));
        int y = Integer.parseInt(parser.get("y"));
        String type = parser.get("type");
        GameMenuMessages message = GameMenuController.dropBuilding(x, y, type, useMaterials);
        System.out.println(message.getMessage());
    }

    void selectBuilding(Parser parser, Scanner scanner) {
        int x = Integer.parseInt(parser.get("x"));
        int y = Integer.parseInt(parser.get("y"));
        GameMenuMessages message = GameMenuController.selectBuilding(x, y, scanner);
        System.out.println(message.getMessage());
    }

    void dropUnit(Parser parser) {

    }

    void selectUnit(Parser parser, Scanner scanner) {
        int x = Integer.parseInt(parser.get("x"));
        int y = Integer.parseInt(parser.get("y"));
        GameMenuMessages message = GameMenuController.selectUnit(x, y, scanner);
        System.out.println(message.getMessage());
    }

    void setTexture(Parser parser) {
        GameMenuMessages message;
        if (parser.get("x1") != null) {
            String[] strings = {parser.get("x1"), parser.get("x2"), parser.get("y1"), parser.get("y2")};
            if (!Utils.areIntegers(strings)) {
                System.out.println("Please import the numbers!");
                return;
            }
            message = GameMenuController.setTexture
                (Integer.parseInt(parser.get("x1")), Integer.parseInt(parser.get("y1"))
                    , Integer.parseInt(parser.get("x2")), Integer.parseInt(parser.get("y2"))
                    , parser.get("t"));
        } else {
            String[] strings = {parser.get("x"), parser.get("y")};
            if (!Utils.areIntegers(strings)) {
                System.out.println("Please import the numbers!");
                return;
            }
            message = GameMenuController.setTexture
                (Integer.parseInt(parser.get("x")), Integer.parseInt(parser.get("y")), parser.get("t"));
        }
        System.out.println(message.getMessage());
    }

    void clearBlock(Parser parser) {
        GameMenuMessages messages = GameMenuController.clearBlock(Integer.parseInt(parser.get("x")), Integer.parseInt(parser.get("y")));
        System.out.println(messages.getMessage());
    }

    void dropRock(Parser parser) {
        String[] strings = {parser.get("x"), parser.get("y")};
        if (!Utils.areIntegers(strings)) {
            System.out.println("Please import numbers!");
            return;
        }
        int x = Integer.parseInt(parser.get("x"));
        int y = Integer.parseInt(parser.get("y"));
        GameMenuMessages message = GameMenuController.dropRock(x, y, parser.get("d"));
        System.out.println(message.getMessage());
    }

    void dropTree(Parser parser) {
        String[] strings = {parser.get("x"), parser.get("y")};
        if (!Utils.areIntegers(strings)) {
            System.out.println("Please import numbers!");
            return;
        }
        int x = Integer.parseInt(parser.get("x"));
        int y = Integer.parseInt(parser.get("y"));
        GameMenuMessages message = GameMenuController.dropTree(x, y, parser.get("t"));
        System.out.println(message.getMessage());
    }

    void nextTurn() {

    }
}
