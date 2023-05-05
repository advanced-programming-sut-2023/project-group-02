package controllers;

import models.Directions;
import models.Game;
import models.Map;
import utils.Validation;
import view.enums.MapMenuMessages;

import java.util.ArrayList;

public class MapMenuController {
    private int currentX, currentY;
    private Map currentMap;

    public MapMenuController(int currentX, int currentY) {
        this.currentX = currentX;
        this.currentY = currentY;
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentXAndY(int currentX, int currentY) {
        this.currentX = currentX;
        this.currentY = currentY;
    }

    public MapMenuMessages moveMap(ArrayList<String> directions) {
        for (String direction : directions) {
            if (!direction.matches("up|down|left|right]")) {
                return MapMenuMessages.INVALID_DIRECTION;
            }
        }
        for (String direction : directions) {
            switch (direction) {
                case "up" -> currentY--;
                case "down" -> currentY++;
                case "left" -> currentX--;
                case "right" -> currentX++;
            }
        }
        return MapMenuMessages.MAP_MOVED;
    }

    public MapMenuMessages showDetails(int x, int y) {
        if (!Validation.areCoordinatesValid(x, y))
            return MapMenuMessages.INVALID_PLACE;

        return MapMenuMessages.PRINTED_SUCCESSFULLY;
    }
}
