package view;

import controllers.GameMenuController;
import controllers.MapMenuController;
import models.Map;
import utils.Parser;
import view.enums.MapMenuMessages;

import java.util.Scanner;

public class MapMenu {
    Map currentMap = GameMenuController.getCurrentGame().getMap();
    public void run(Scanner scanner) {
        while (true) {
            Parser parser = new Parser(scanner.nextLine());
            if (parser.beginsWith("show map")) {
                showMap(parser);
            }
        }
    }

    void showMap(Parser parser) {
        int x = Integer.parseInt(parser.get("x"));
        int y = Integer.parseInt(parser.get("y"));

        MapMenuMessages message = MapMenuController.showMap(x,y,currentMap);
        if (message.equals(MapMenuMessages.INVALID_PLACE))
            System.out.println("Please enter invalid numbers!");
        if (message.equals(MapMenuMessages.PRINTED_SUCCESSFULLY))
            currentMap.printMiniMap(x,y);
    }

    void moveMap(Parser parser) {

    }

    void showMapDetails(Parser parser) {

    }
}
