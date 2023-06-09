package controllers;

import models.*;
import models.buildings.PlainBuilding;
import models.units.MakeUnitInstances;
import models.units.Unit;
import models.units.UnitType;
import utils.Parser;
import utils.Utils;
import utils.Validation;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Scanner;

import client.view.BuildingMenu;
import client.view.GameMenu;
import client.view.UnitMenu;
import client.view.enums.GameMenuMessages;

public class GameMenuController {
    private static Game currentGame;
    private static HashMap<Game, ArrayList<CellWrapper>> allMadeCellWrappers = new HashMap<>();
    private static HashMap<User, Game> savedGames = new HashMap<>();

    public static CellWrapper findCellWrapperWithXAndYCurrentGame(int x, int y) {
        ArrayList<CellWrapper> cellWrappersOfGame = allMadeCellWrappers.get(currentGame);
        for (CellWrapper cellWrapper : cellWrappersOfGame) {
            if (cellWrapper.getSquareX() == x && cellWrapper.getSquareY() == y)
                return cellWrapper;
        }
        return new CellWrapper(currentGame.getMap().findCellWithXAndY(x,y));
    }

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

    public static HashMap<Game, ArrayList<CellWrapper>> getAllMadeCellWrappers() {
        return allMadeCellWrappers;
    }

    public static ArrayList<CellWrapper> getCurrentGameCellWrappers() {
        return allMadeCellWrappers.get(currentGame);
    }

    public static void updateCellWrappers(ArrayList<CellWrapper> cellWrappersToBeUpdated) {
        ArrayList<CellWrapper> cellWrappers = allMadeCellWrappers.get(currentGame);
        for (int i = cellWrappers.size() - 1; i >= 0; i--) {
            if (!cellWrappersToBeUpdated.contains(cellWrappers.get(i)))
                continue;
            CellWrapper cellWrapper = cellWrappers.get(i);
            cellWrappers.remove(i);
            cellWrappers.add(new CellWrapper(currentGame.getMap().findCellWithXAndY(cellWrapper.getSquareX(), cellWrapper.getSquareY())));
        }
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

    public static GameMenuMessages dropBuilding(int x, int y, User player, String buildingName, boolean useMaterials) {
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
        Government government = currentGame.getPlayersGovernment(player);
        if (useMaterials && !government.hasEnoughMaterialsForBuilding(building)) {
            return GameMenuMessages.NOT_ENOUGH_MATERIALS;
        }
        if (building instanceof PlainBuilding && !government.hasEnoughWorkerForBuilding(building)) {
            return GameMenuMessages.NOT_ENOUGH_PEOPLE;
        }
        currentGame.addObject(building, x, y, player);
        //TODO fix this after map initialization
        government.recruitPeople(building.getWorkerCount(), building);
        if (useMaterials)
            government.reduceMaterialsForBuilding(building);
        else if (building instanceof PlainBuilding)
            ((PlainBuilding) building).addPeople(((PlainBuilding) building).getMaxPeople());

        return GameMenuMessages.DONE_SUCCESSFULLY;
    }

    public static GameMenuMessages dropBuilding(int x, int y, Building building, boolean useMaterials) {
        if (!Validation.areCoordinatesValid(x, y)) {
            return GameMenuMessages.INVALID_PLACE;
        }
        if (currentGame.getMap().findCellWithXAndY(x, y).isOccupied()) {
            return GameMenuMessages.FULL_CELL;
        }
        Government government = currentGame.getPlayersGovernment(getCurrentGame().getCurrentPlayer());
        if (useMaterials && !government.hasEnoughMaterialsForBuilding(building)) {
            return GameMenuMessages.NOT_ENOUGH_MATERIALS;
        }
        if (building instanceof PlainBuilding && !government.hasEnoughWorkerForBuilding(building)) {
            return GameMenuMessages.NOT_ENOUGH_PEOPLE;
        }
        currentGame.addObject(building, x, y, getCurrentGame().getCurrentPlayer());
        //TODO fix this after map initialization
        government.recruitPeople(building.getWorkerCount(), building);
        if (useMaterials)
            government.reduceMaterialsForBuilding(building);
        else if (building instanceof PlainBuilding)
            ((PlainBuilding) building).addPeople(((PlainBuilding) building).getMaxPeople());

        return GameMenuMessages.DONE_SUCCESSFULLY;
    }

    public static GameMenuMessages selectBuilding(int x, int y) {
        Building selectedBuilding;
        if (!Validation.areCoordinatesValid(x, y))
            return GameMenuMessages.INVALID_PLACE;
        if ((selectedBuilding = currentGame.getMap().findBuildingWithXAndY(x, y)) == null)
            return GameMenuMessages.NO_BUILDINGS;
        if (!selectedBuilding.getOwner().equals(currentGame.getCurrentPlayer()))
            return GameMenuMessages.NOT_YOURS;

        BuildingMenuController.setSelectedBuilding(selectedBuilding, x, y);
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
        UnitMenuController.init(selectedUnits, x, y);
        return GameMenuMessages.DONE_SUCCESSFULLY;
    }

    public static GameMenuMessages setTexture(int x, int y, String textureType) {
        Cell cell;
        Texture texture;

        if (!Validation.areCoordinatesValid(x, y))
            return GameMenuMessages.INVALID_PLACE;
        if ((texture = Texture.findTextureWithName(textureType)) == null) {
            return GameMenuMessages.INVALID_TEXTURE;
        }
        cell = currentGame.getMap().findCellWithXAndY(x, y);
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
        currentGame.addObject(new Rock(Directions.getDirectionWithName(direction)), x, y, null);
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
        currentGame.addObject(new Tree(TreeType.getTreeTypeWithName(treeName)), x, y, null);
        return GameMenuMessages.DONE_SUCCESSFULLY;
    }

    public static GameMenuMessages dropSmallStoneGate(User player, Parser parser) {
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
        Building building = BuildingFactory.makeBuilding("small stone gate");
        ((PlainBuilding) building).addPeople(8);
        currentGame.addObject(building, player, x, y);
        currentGame.addObject(BuildingFactory.makeBuilding("stockpile"), player, x + 1, y);
        GameMenuController.getCurrentGame().addUnit(MakeUnitInstances.makeLord(), player, x, y);
        return GameMenuMessages.DONE_SUCCESSFULLY;
    }

    public static GameMenuMessages makeUnit(Unit unit, int count, User player) {
        Government currentPlayersGovernment = currentGame.getPlayersGovernment(player);
        if (currentPlayersGovernment.numberOfUnemployed() < count)
            return GameMenuMessages.NOT_ENOUGH_PEOPLE;
        if (!currentPlayersGovernment.hasEnoughEquipmentsForUnit(unit.getName(), count))
            return GameMenuMessages.NOT_ENOUGH_EQUIPMENTS;
        currentPlayersGovernment.recruitPeople(count, currentPlayersGovernment.getSmallStone());
        reduceEquipmentForUnit(unit.getName(), currentPlayersGovernment, count);
        UnitMenuController.makeUnits(unit,count);
        return GameMenuMessages.DONE_SUCCESSFULLY;
    }

    public static GameMenuMessages dropUnit(int x, int y, Unit unit, int count, User player) {
        if (count == 0)
            return GameMenuMessages.INVALID_NUMBER;

        for (int i = 0; i < count; i++) {
            currentGame.addUnit(unit, player, x, y);
        }
        UnitMenuController.useUnits(unit,count);
        return GameMenuMessages.DONE_SUCCESSFULLY;
    }

    private static void reduceEquipmentForUnit(String type, Government government, int numberOfUnits) {
        for (UnitType unitType : EnumSet.allOf(UnitType.class)) {
            if (unitType.getName().equals(type)) {
                for (MartialEquipment martialEquipment : unitType.getMartialEquipmentsNeeded())
                    government.reduceItem(martialEquipment, numberOfUnits);
                government.reduceItem(Material.GOLD, numberOfUnits * unitType.getPrice());
                return;
            }
        }
    }

    public static User findUserWithGovernment(Government government) {
        for (User player : currentGame.getPlayers()) {
            if (currentGame.getPlayersGovernment(player).equals(government))
                return player;
        }
        return null;
    }

    public static void nextTurn(GameMenu gameMenu) {
        currentGame.nextTurn(gameMenu);
        for (CellWrapper cellWrapper : getCurrentGameCellWrappers()) {
            cellWrapper.addUnitsImages();
        }
    }
}
