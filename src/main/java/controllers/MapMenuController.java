package controllers;

import models.Directions;
import models.Game;
import models.Map;
import view.enums.MapMenuMessages;

import java.util.ArrayList;

public class MapMenuController {
    private static int currentX , currentY;

    public static MapMenuMessages showMap(int x, int y, Map currentMap) {
        currentX = x;
        currentY = y;

        if (x > currentMap.height || y < currentMap.width || x < 0 || y < 0)
            return MapMenuMessages.INVALID_PLACE;

        return MapMenuMessages.PRINTED_SUCCESSFULLY;
    }

    public static MapMenuMessages moveMap(ArrayList<Directions> directions) {
        return null;
    }

    public static MapMenuMessages showDetails(int x, int y) {
        return null;
    }
}
