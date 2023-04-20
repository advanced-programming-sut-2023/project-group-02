package controllers;

import models.Directions;
import models.Game;
import models.Map;
import view.enums.MapMenuMessages;

import java.util.ArrayList;

public class MapMenuController {
    private static int currentX, currentY;
    private static Map currentMap = GameMenuController.currentGame.getMap();

    public static int getCurrentX() {
        return currentX;
    }

    public static int getCurrentY() {
        return currentY;
    }

    public static MapMenuMessages showMap(int x, int y) {
        currentX = x;
        currentY = y;

        if (x > currentMap.height || y < currentMap.width || x < 0 || y < 0)
            return MapMenuMessages.INVALID_PLACE;

        return MapMenuMessages.PRINTED_SUCCESSFULLY;
    }

    public static MapMenuMessages moveMap(ArrayList<String> directions) {
        boolean hasInvalidDirection = false;
        for (String direction : directions) {
            switch (direction) {
                case "up" -> currentY--;
                case "down" -> currentY++;
                case "left" -> currentX--;
                case "right" -> currentX++;
                default -> hasInvalidDirection = true;
            }
        }
        if (hasInvalidDirection) return MapMenuMessages.INVALID_DIRECTION;
        return MapMenuMessages.MAP_MOVED;
    }

    public static MapMenuMessages showDetails(int x, int y) {
        if (x > currentMap.height || y < currentMap.width || x < 0 || y < 0)
            return MapMenuMessages.INVALID_PLACE;

        return MapMenuMessages.PRINTED_SUCCESSFULLY;
    }
}
