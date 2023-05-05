package view;

import controllers.GameMenuController;
import controllers.MapMenuController;
import models.Map;
import utils.Parser;
import utils.Utils;
import view.enums.MapMenuMessages;

import java.util.ArrayList;
import java.util.Scanner;

public class MapMenu {
    MapMenuController mapMenuController;
    Map currentMap = GameMenuController.getCurrentGame().getMap();

    public MapMenu(int currentX, int currentY) {
        mapMenuController = new MapMenuController(currentX, currentY);
    }

    public void run(Scanner scanner) {
        while (true) {
            Parser parser = new Parser(scanner.nextLine());
            if (parser.beginsWith("map")) {
                getDirections(parser);
            } else if (parser.beginsWith("show details")) {
                showMapDetails(parser);
            } else if (parser.beginsWith("back")) {
                break;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    void getDirections(Parser parser) {
        ArrayList<String> directions = new ArrayList<>();
        for (int i = 1; i < parser.getTokens().size(); i++) {
            if (parser.getByIndex(i).matches("\\d+")) {
                for (int j = 2; j < Integer.parseInt(parser.getByIndex(j)); j++) {
                    directions.add(parser.getByIndex(j - 1));
                }
            } else {
                directions.add(parser.getByIndex(i));
            }
        }
        moveMap(directions);
    }

    void moveMap(ArrayList<String> directions) {
        MapMenuMessages message = mapMenuController.moveMap(directions);
        if (message.equals(MapMenuMessages.INVALID_DIRECTION))
            System.out.println("Invalid direction!");
        if (message.equals(MapMenuMessages.MAP_MOVED))
            System.out.println(currentMap.printMiniMap(mapMenuController.getCurrentX(), mapMenuController.getCurrentY()));
    }

    void showMapDetails(Parser parser) {
        String[] strings = {parser.get("x"), parser.get("y")};
        if (!Utils.areIntegers(strings)) {
            System.out.println("Please import number!");
            return;
        }
        int x = Integer.parseInt(parser.get("x"));
        int y = Integer.parseInt(parser.get("y"));
        MapMenuMessages message = mapMenuController.showDetails(x, y);
        if (message.equals(MapMenuMessages.INVALID_DIRECTION))
            System.out.println("Invalid direction!");
        if (message.equals(MapMenuMessages.PRINTED_SUCCESSFULLY))
            System.out.println(currentMap.findCellWithXAndY(x, y).cellInfo());
    }
}
