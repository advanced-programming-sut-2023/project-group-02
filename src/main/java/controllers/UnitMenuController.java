package controllers;

import java.util.ArrayList;
import java.util.LinkedList;

import models.Coordinates;
import models.Game;
import models.Map;
import models.units.Unit;
import models.units.UnitState;
import models.units.UnitType;
import utils.PathFinder;
import utils.Validation;
import view.enums.UnitMenuMessages;

public class UnitMenuController {
    private static ArrayList<Unit> selectedUnits;
    private static int unitsX;
    private static int unitsY;


    public static ArrayList<Unit> getSelectedUnits() {
        return selectedUnits;
    }

    public static int getUnitsX() {
        return unitsX;
    }

    public static int getUnitsY() {
        return unitsY;
    }

    private static void setUnitsXAndY(int x, int y) {
        unitsX = x;
        unitsY = y;
    }

    private static void setSelectedUnits(ArrayList<Unit> selectedUnits) {
        UnitMenuController.selectedUnits = selectedUnits;
    }

    public static void init(ArrayList<Unit> selectedUnits, int x, int y) {
        setSelectedUnits(selectedUnits);
        setUnitsXAndY(x, y);
    }

    public static UnitType selectedUnitsType() {
        return selectedUnits.get(0).getType();
    }

    public static UnitMenuMessages moveUnit(int x, int y) {
        Game game = GameMenuController.getCurrentGame();
        Map map = game.getMap();

        if (selectedUnits.get(0).hasMoved() || game.getDestinations().containsKey(selectedUnits.get(0))) {
            return UnitMenuMessages.ALREADY_DONE;
        }

        LinkedList<Coordinates> path = PathFinder.getPath(map, unitsX, unitsY, x, y);
        int length = path.size() - 1;
        if (length == 0) {
            return UnitMenuMessages.ALREADY_DONE;
        }
        if (length < 0) {
            return UnitMenuMessages.CANT_GO_THERE;
        }

        int maxDistance = selectedUnits.get(0).getMaxDistance();
        Coordinates finalPointForThisTurn = path.get(Math.min(path.size() - 1, maxDistance));
        for (Unit unit : selectedUnits) {
            unit.setHasMoved(true);
            map.moveUnit(unit, finalPointForThisTurn.x, finalPointForThisTurn.y);
            if (!finalPointForThisTurn.equals(path.getLast())) {
                game.scheduleMovement(unit, finalPointForThisTurn.x, finalPointForThisTurn.y, x, y);
            }
        }
        setUnitsXAndY(finalPointForThisTurn.x, finalPointForThisTurn.y);
        return UnitMenuMessages.DONE_SUCCESSFULLY;
    }

    public static UnitMenuMessages patrolUnit(int x1, int y1, int x2, int y2) {
        if (!Validation.areCoordinatesValid(x1, y1) || !Validation.areCoordinatesValid(x2, y2)) {
            return UnitMenuMessages.CANT_GO_THERE;
        }
        if (selectedUnits.get(0).hasMoved() || GameMenuController.getCurrentGame().getDestinations().containsKey(selectedUnits.get(0))) {
            return UnitMenuMessages.ALREADY_DONE;
        }
        for (Unit unit : selectedUnits) {
            Coordinates[] coordinates = {new Coordinates(x1, y1), new Coordinates(x2, y2)};
            unit.setPatrollingPoint(coordinates);
            moveUnit(x1, y1);
        }
        return UnitMenuMessages.DONE_SUCCESSFULLY;
    }

    public static UnitMenuMessages setState(UnitState state) {
        for (Unit unit : selectedUnits) {
            unit.setState(state);
        }
        return UnitMenuMessages.DONE_SUCCESSFULLY;
    }

    public static UnitMenuMessages attack(int enemiesX, int enemiesY, Boolean isAerial) {
        Boolean unitExists = false;
        Game game = GameMenuController.getCurrentGame();
        Map map = game.getMap();
        for (Unit unit : map.findUnitsWithXAndY(enemiesX, enemiesY)) {
            if (unit.getOwner().getUsername().equals(selectedUnits.get(0).getOwner().getUsername()))
                unitExists = true;
        }
        if (!unitExists)
            return UnitMenuMessages.NO_UNIT_THERE;
        if (isAerial) {
            if (!isInRange(selectedUnits.get(0).getCurrentX(), selectedUnits.get(0).getCurrentY(), enemiesX, enemiesY))
                return UnitMenuMessages.TOO_FAR;
            else return UnitMenuMessages.DONE_SUCCESSFULLY;
        }
        moveUnit(enemiesX, enemiesY);
        return UnitMenuMessages.DONE_SUCCESSFULLY;
    }

    private static boolean isInRange(int currentX, int currentY, int enemiesX, int enemiesY) {
        return Math.abs(currentX - enemiesX) + Math.abs(currentY - enemiesY) < 5;
    }

    public static UnitMenuMessages pourOil(String direction) {
        return null;
    }

    public static UnitMenuMessages digTunnel(int x, int y) {
        if (!Validation.areCoordinatesValid(x, y))
            return UnitMenuMessages.INVALID_PLACE;
        if (!GameMenuController.getCurrentGame().getMap().findCellWithXAndY(x, y).canDigTunnel())
            return UnitMenuMessages.CANT_DIG;
        LinkedList<Coordinates> path = PathFinder.getPath(GameMenuController.getCurrentGame().getMap(), unitsX, unitsY, x, y);
        int length = path.size() - 1;
        if (length == 0)
            return UnitMenuMessages.ALREADY_DONE;
        if (length == -1 || length > selectedUnits.get(0).getMaxDistance())
            return UnitMenuMessages.CANT_GO_THERE;

        for (Coordinates coordinate : path) {
            coordinate.getCellWithCoordinates(GameMenuController.getCurrentGame().getMap()).destroyObjects();
        }
        for (Unit unit : selectedUnits) {
            GameMenuController.getCurrentGame().getMap().moveUnit(unit, x, y);
        }
        setUnitsXAndY(x, y);
        return UnitMenuMessages.DONE_SUCCESSFULLY;
    }

    public static UnitMenuMessages build(String equipmentName) {
        if (!selectedUnits.get(0).getType().equals(UnitType.ENGINEER))
            return UnitMenuMessages.NOT_ENGINEER;

        return null;
    }

    public static UnitMenuMessages dropLadder(int x, int y) {
        if (!Validation.areCoordinatesValid(x, y))
            return UnitMenuMessages.INVALID_PLACE;
        LinkedList<Coordinates> path = PathFinder.getPath(GameMenuController.getCurrentGame().getMap(), unitsX, unitsY, x, y);
        int length = path.size() - 1;
        if (length == -1 || length > selectedUnits.get(0).getPace() / 10)
            return UnitMenuMessages.CANT_GO_THERE;
        GameMenuController.getCurrentGame().getMap().findCellWithXAndY(x, y).setHasLadder(true);
        return UnitMenuMessages.DONE_SUCCESSFULLY;
    }

    public static String disbandUnit() {
        for (Unit unit : selectedUnits) {
            GameMenuController.getCurrentGame().getMap().removeUnit(unit, unit.getCurrentX(), unit.getCurrentY());
            GameMenuController.getCurrentGame().getPlayersGovernment(unit.getOwner()).removeTroop();
            GameMenuController.getCurrentGame().getPlayersGovernment(unit.getOwner()).addPeople(1);
        }
        return "Done!";
    }
}
