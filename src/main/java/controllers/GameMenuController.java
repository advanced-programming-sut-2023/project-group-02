package controllers;

import models.*;
import models.buildings.PlainBuilding;
import models.units.MakeUnitInstances;
import models.units.Unit;
import models.units.UnitType;
import utils.Parser;
import utils.Utils;
import utils.Validation;
import view.BuildingMenu;
import view.UnitMenu;
import view.enums.GameMenuMessages;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Scanner;

public class GameMenuController {
    private static Game currentGame;
    private static HashMap<User, Game> savedGames = new HashMap<>();

    public static void saveGame() {
        for (User user : currentGame.getPlayers()) {
            savedGames.put(user, currentGame);
        }
    }

    public static boolean loadGame() {
        Game savedGame = savedGames.get(UserController.getCurrentUser());
        if (savedGame == null) {
            return false;
        }
        currentGame = savedGame;
        return true;
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        GameMenuController.currentGame = currentGame;
        saveGame();
    }

    public static void addPlayerToGame(User player, Colors color) {
        currentGame.addGovernment(new Government(player, color));
    }

    public static GameMenuMessages showMap(int x, int y) {
        if (!Validation.areCoordinatesValid(x, y))
            return GameMenuMessages.INVALID_PLACE;

        return GameMenuMessages.DONE_SUCCESSFULLY;
    }

    public static GameMenuMessages setFoodRate(int rate) {
        if (getCurrentGame().getCurrentPlayersGovernment().setFoodRate(rate)) {
            return GameMenuMessages.DONE_SUCCESSFULLY;
        } else {
            return GameMenuMessages.OUT_OF_BOUNDS;
        }
    }

    public static GameMenuMessages setTaxRate(int rate) {
        if (getCurrentGame().getCurrentPlayersGovernment().setTaxRate(rate)) {
            return GameMenuMessages.DONE_SUCCESSFULLY;
        } else {
            return GameMenuMessages.OUT_OF_BOUNDS;
        }
    }

    public static GameMenuMessages setFearRate(int rate) {
        if (getCurrentGame().getCurrentPlayersGovernment().setFearRate(rate)) {
            return GameMenuMessages.DONE_SUCCESSFULLY;
        } else {
            return GameMenuMessages.OUT_OF_BOUNDS;
        }
    }

    public static GameMenuMessages dropBuilding(int x, int y, String buildingName, boolean useMaterials) {
        Building building;
        if (buildingName == null || ((building = BuildingFactory.makeBuilding(buildingName)) == null)) {
            return GameMenuMessages.INVALID_BUILDING_NAME;
        }
        if (!Validation.areCoordinatesValid(x, y)) {
            return GameMenuMessages.INVALID_PLACE;
        }
        if (currentGame.getMap().findCellWithXAndY(x, y).isOccupied()) {
            return GameMenuMessages.FULL_CELL;
        }
        if (useMaterials && !currentGame.getCurrentPlayersGovernment().hasEnoughMaterialsForBuilding(building)) {
            return GameMenuMessages.NOT_ENOUGH_MATERIALS;
        }
        currentGame.addObject(building, x, y);
        if (useMaterials) {
            currentGame.getCurrentPlayersGovernment().reduceMaterialsForBuilding(building);
            initBuilding(building);
        }
        return GameMenuMessages.DONE_SUCCESSFULLY;
    }

    private static void initBuilding(Building building) {
        //TODO complete this (workers...)
        if (building instanceof PlainBuilding)
            ((PlainBuilding) building).addPeople(((PlainBuilding) building).getMaxPeople());
    }

    public static GameMenuMessages selectBuilding(int x, int y) {
        Building selectedBuilding;
        if (!Validation.areCoordinatesValid(x, y))
            return GameMenuMessages.INVALID_PLACE;
        if ((selectedBuilding = currentGame.getMap().findBuildingWithXAndY(x, y)) == null)
            return GameMenuMessages.NO_BUILDINGS;
        if (!selectedBuilding.getOwner().equals(currentGame.getCurrentPlayer()))
            return GameMenuMessages.NOT_YOURS;

        BuildingMenuController.setSelectedBuilding(selectedBuilding);
        return GameMenuMessages.DONE_SUCCESSFULLY;
    }

    public static GameMenuMessages selectUnit(int x, int y, String type) {
        if (!Validation.areCoordinatesValid(x, y))
            return GameMenuMessages.INVALID_PLACE;

        ArrayList<Unit> selectedUnits = new ArrayList<>(currentGame.getMap().findUnitsWithXAndY(x, y).stream().filter(
                unit -> unit.getOwner().equals(currentGame.getCurrentPlayer()))
                .filter(unit -> unit.getName().equalsIgnoreCase(type))
            .toList());
        if (selectedUnits.size() == 0) {
            return GameMenuMessages.NO_UNITS;
        }
        UnitMenuController.setSelectedUnits(selectedUnits);
        return GameMenuMessages.DONE_SUCCESSFULLY;
    }

    public static GameMenuMessages setTexture(int x, int y, String textureType) {
        Cell cell;
        Texture texture;

        if (!Validation.areCoordinatesValid(x, y))
            return GameMenuMessages.INVALID_PLACE;
        if ((texture = Texture.findTextureWithName(textureType)) == null)
            return GameMenuMessages.INVALID_TEXTURE;
        if ((cell = currentGame.getMap().findCellWithXAndY(x, y)).isOccupied())
            return GameMenuMessages.FULL_CELL;
        if (cell.getTexture().equals(texture))
            return GameMenuMessages.SAME_CELL;

        cell.setTexture(texture);
        return GameMenuMessages.DONE_SUCCESSFULLY;
    }

    public static GameMenuMessages setTexture(int x1, int y1, int x2, int y2, String textureType) {
        Texture texture;
        if (!Validation.areCoordinatesValid(x1, y1, x2, y2))
            return GameMenuMessages.INVALID_PLACE;
        if ((texture = Texture.findTextureWithName(textureType)) == null)
            return GameMenuMessages.INVALID_TEXTURE;
        ArrayList<Cell> allCells = currentGame.getMap().findMoreThanOneCell(x1, y1, x2, y2);
        if (Cell.blocksTexture(allCells) != null && texture.equals(Cell.blocksTexture(allCells)))
            return GameMenuMessages.SAME_BLOCK;
        if (Cell.isABlockOccupied(allCells)) return GameMenuMessages.FULL_CELL;
        Cell.setBlocksTexture(allCells, texture);
        return GameMenuMessages.DONE_SUCCESSFULLY;
    }

    public static GameMenuMessages clearBlock(int x, int y) {
        if (!Validation.areCoordinatesValid(x, y))
            return GameMenuMessages.INVALID_PLACE;
        currentGame.getMap().findCellWithXAndY(x, y).clear();
        return GameMenuMessages.DONE_SUCCESSFULLY;
    }

    public static GameMenuMessages dropRock(int x, int y, String direction) {
        if (!Validation.areCoordinatesValid(x, y))
            return GameMenuMessages.INVALID_PLACE;
        if (direction == null || !direction.matches("north|south|east|west|random"))
            return GameMenuMessages.INVALID_DIRECTION;
        if (GameMenuController.getCurrentGame().getMap().findCellWithXAndY(x, y).isOccupied())
            return GameMenuMessages.FULL_CELL;

        //TODO handle the textures in which we cant drop rock
        currentGame.addObject(new Rock(Directions.getDirectionWithName(direction)), x, y);
        return GameMenuMessages.DONE_SUCCESSFULLY;
    }

    public static GameMenuMessages dropTree(int x, int y, String treeName) {
        if (!Validation.areCoordinatesValid(x, y))
            return GameMenuMessages.INVALID_PLACE;
        if (treeName == null || TreeType.getTreeTypeWithName(treeName) == null)
            return GameMenuMessages.INVALID_TREE_NAME;
        if (GameMenuController.getCurrentGame().getMap().findCellWithXAndY(x, y).isOccupied())
            return GameMenuMessages.FULL_CELL;

        // TODO handle the textures in which we cant drop tree
        currentGame.addObject(new Tree(TreeType.getTreeTypeWithName(treeName)), x, y);
        return GameMenuMessages.DONE_SUCCESSFULLY;
    }

    public static GameMenuMessages dropSmallStoneGate(Parser parser) {
        if ((!parser.getFlag("x") || !parser.getFlag("y")) ||
            (!Utils.isInteger(parser.get("x")) || !Utils.isInteger(parser.get("y")))) {
            return GameMenuMessages.INVALID_INPUT_FORMAT;
        }
        int x = Integer.parseInt(parser.get("x"));
        int y = Integer.parseInt(parser.get("y"));
        if (!Validation.areCoordinatesValid(x, y)) {
            return GameMenuMessages.INVALID_PLACE;
        }
        if (currentGame.getMap().findCellWithXAndY(x, y).isOccupied() ||
            currentGame.getMap().findCellWithXAndY(x + 1, y).isOccupied()) {
            return GameMenuMessages.FULL_CELL;
        }
        currentGame.addObject(BuildingFactory.makeBuilding("small stone gate"), x, y);
        currentGame.addObject(BuildingFactory.makeBuilding("stockpile"), x + 1, y);
        currentGame.getCurrentPlayersGovernment().getSmallStone().addPeople(8);
        return GameMenuMessages.DONE_SUCCESSFULLY;
    }

    public static GameMenuMessages dropUnit(int x, int y, String type, int count, boolean useEquipments) {
        Unit unit = MakeUnitInstances.createUnitInstance(type);
        if (!Validation.areCoordinatesValid(x, y))
            return GameMenuMessages.INVALID_PLACE;
        if (count <= 0)
            return GameMenuMessages.INVALID_NUMBER;
        if (unit == null)
            return GameMenuMessages.INVALID_UNIT_NAME;
        Government currentPlayersGovernment = currentGame.getCurrentPlayersGovernment();
        if (currentPlayersGovernment.numberOfUnemployed() < count)
            return GameMenuMessages.NOT_ENOUGH_PEOPLE;
        if (useEquipments && !currentPlayersGovernment.hasEnoughEquipmentsForUnit(type, count)) {
            return GameMenuMessages.NOT_ENOUGH_EQUIPMENTS;
        }
        currentPlayersGovernment.recruitPeople(count, currentPlayersGovernment.getSmallStone());
        // we assume that units working place is small stone gate.
        for (int i = 0; i < count; i++) {
            unit = MakeUnitInstances.createUnitInstance(type);
            currentGame.addUnit(unit, x, y);
        }
        if (useEquipments)
            reduceEquipmentForUnit(type, currentPlayersGovernment, count);
        return GameMenuMessages.DONE_SUCCESSFULLY;
    }

    private static void reduceEquipmentForUnit(String type, Government government, int numberOfUnits) {
        for (UnitType unitType : EnumSet.allOf(UnitType.class)) {
            if (unitType.getName().equals(type)) {
                for (MartialEquipment martialEquipment : unitType.getMartialEquipmentsNeeded())
                    government.reduceItem(martialEquipment, numberOfUnits);
                return;
            }
        }
    }

    public static void nextTurn() {
        currentGame.nextTurn();
    }
}
