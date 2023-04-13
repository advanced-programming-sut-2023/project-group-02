package controllers;

import models.User;
import view.enums.GameMenuMessages;

import java.util.ArrayList;

public class GameMenuController {
    private static ArrayList<User> allUsersInGame = new ArrayList<>();

    public static ArrayList<User> getAllUsersInGame() {
        return allUsersInGame;
    }

    public static GameMenuMessages showMap(int x, int y) {
        return null;
    }

    public static GameMenuMessages setFoodRate(int rate) {
        return null;
    }

    public static GameMenuMessages setTaxRate(int rate) {
        return null;
    }

    public static GameMenuMessages setFearRate(int rate) {
        return null;
    }

    public static GameMenuMessages dropBuilding(int x, int y, String buildingName) {
        return null;
    }

    public static GameMenuMessages selectBuilding(int x, int y) {
        return null;
    }

    public static GameMenuMessages dropUnit(int x, int y, String unitName) {
        return null;
    }

    public static GameMenuMessages selectUnit(int x, int y) {
        return null;
    }

    public static GameMenuMessages setTexture(int x, int y, String textureType) {
        return null;
    }

    public static GameMenuMessages setTexture(int x1, int y1, int x2, int y2, String textureType) {
        return null;
    }

    public static GameMenuMessages clearBlock(int x, int y) {
        return null;
    }

    public static GameMenuMessages dropBlock(int x, int y, String direction) {
        return null;
    }

    public static GameMenuMessages dropTree(int x, int y, String treeName) {
        return null;
    }


}
