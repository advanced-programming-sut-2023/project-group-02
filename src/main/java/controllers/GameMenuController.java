package controllers;

import models.*;
import models.units.Unit;
import utils.Validation;
import view.BuildingMenu;
import view.MapMenu;
import view.UnitMenu;
import view.enums.GameMenuMessages;

import java.util.ArrayList;
import java.util.Scanner;

public class GameMenuController {
    public static Game currentGame;

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static ArrayList<User> getAllUsersInGame() {
        return currentGame.getPlayers();
    }

    public static GameMenuMessages showMap(int x, int y) {
        if (!Validation.areCoordinatesValid(x, y))
            return GameMenuMessages.INVALID_PLACE;

        return GameMenuMessages.DONE_SUCCESSFULLY;
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

    public static GameMenuMessages selectBuilding(int x, int y, Scanner scanner) {
        Building selectedBuilding;
        if (!Validation.areCoordinatesValid(x, y))
            return GameMenuMessages.INVALID_PLACE;
        if ((selectedBuilding = currentGame.getMap().findBuildingWithXAndY(x, y)) == null)
            return GameMenuMessages.NO_BUILDINGS;
        if (!selectedBuilding.getOwner().equals(currentGame.getCurrentPlayer()))
            return GameMenuMessages.NOT_YOURS;

        new BuildingMenu(selectedBuilding).run(scanner);
        return GameMenuMessages.DONE_SUCCESSFULLY;
    }

    public static GameMenuMessages dropUnit(int x, int y, String unitName) {
        return null;
    }

    public static GameMenuMessages selectUnit(int x, int y, Scanner scanner) {
        ArrayList<Unit> selectedUnits = new ArrayList<>();
        if (!Validation.areCoordinatesValid(x, y))
            return GameMenuMessages.INVALID_PLACE;
        if ((selectedUnits = currentGame.getMap().findUnitsWithXAndY(x, y)).size() == 0)
            return GameMenuMessages.NO_UNITS;
        // TODO do we have not yours here too?

        new UnitMenu(selectedUnits).run(scanner);
        return GameMenuMessages.DONE_SUCCESSFULLY;
    }

    public static GameMenuMessages setTexture(int x, int y, String textureType) {
        Cell cell;
        Texture texture;

        if (Validation.areCoordinatesValid(x,y))
            return GameMenuMessages.INVALID_PLACE;
        if ((texture = Texture.findTextureWithName(textureType)) == null)
            return GameMenuMessages.INVALID_TEXTURE;
        if ((cell = currentGame.getMap().findCellWithXAndY(x,y)).isOccupied())
            return GameMenuMessages.FULL_CELL;

        cell.setTexture(texture);
        return GameMenuMessages.DONE_SUCCESSFULLY;
    }

    public static GameMenuMessages setTexture(int x1, int y1, int x2, int y2, String textureType) {
        Texture texture;
        if (!Validation.areCoordinatesValid(x1,y1,x2,y2))
            return GameMenuMessages.INVALID_PLACE;
        if ((texture = Texture.findTextureWithName(textureType)) == null)
            return GameMenuMessages.INVALID_TEXTURE;

        ArrayList<Cell> allCells = currentGame.getMap().findMoreThanOneCell(x1,y1,x2,y2);
        if (Cell.isABlockOccupied(allCells)) return GameMenuMessages.FULL_CELL;
        Cell.setBlocksTexture(allCells,texture);
        return GameMenuMessages.DONE_SUCCESSFULLY;
    }

    public static GameMenuMessages clearBlock(int x, int y) {
        if (!Validation.areCoordinatesValid(x,y))
            return GameMenuMessages.INVALID_PLACE;

        currentGame.getMap().findCellWithXAndY(x,y).clear();
        return GameMenuMessages.DONE_SUCCESSFULLY;
    }

    public static GameMenuMessages dropRock(int x, int y, String direction) {
        if (!Validation.areCoordinatesValid(x, y))
            return GameMenuMessages.INVALID_PLACE;
        if (!direction.matches("north|south|east|west|random"))
            return GameMenuMessages.INVALID_DIRECTION;
        if (!GameMenuController.getCurrentGame().getMap().findCellWithXAndY(x, y).isOccupied())
            return GameMenuMessages.FULL_CELL;

        // TODO handle the textures in which we cant drop rock
        currentGame.addObject(new Rock(Directions.getDirectionWithName(direction)), x, y);
        return GameMenuMessages.DONE_SUCCESSFULLY;
    }

    public static GameMenuMessages dropTree(int x, int y, String treeName) {
        if (!Validation.areCoordinatesValid(x, y))
            return GameMenuMessages.INVALID_PLACE;
        if (!treeName.matches("desert shrub|cherry palm|olive tree|cocunut palm|dates palm"))
            return GameMenuMessages.INVALID_TREE_NAME;
        if (!GameMenuController.getCurrentGame().getMap().findCellWithXAndY(x, y).isOccupied())
            return GameMenuMessages.FULL_CELL;

        // TODO handle the textures in which we cant drop tree

        currentGame.addObject(new Tree(TreeType.getTreeTypeWithName(treeName)), x, y);
        return GameMenuMessages.DONE_SUCCESSFULLY;
    }
}
