package controllers;

import java.util.ArrayList;
import java.util.LinkedList;

import models.Coordinates;
import models.Map;
import models.units.Unit;
import models.units.UnitState;
import models.units.UnitType;
import utils.PathFinder;
import utils.Validation;
import view.enums.UnitMenuMessages;

public class UnitMenuController {
    private static ArrayList<Unit> selectedUnits;

    public static ArrayList<Unit> getSelectedUnits() {
        return selectedUnits;
    }
    private static int unitsX;
    private static int unitsY;

    public static int getUnitsX() {
        return unitsX;
    }

    public static int getUnitsY() {
        return unitsY;
    }

    public static void setUnitsXAndY(int x, int y) {
        unitsX = x;
        unitsY = y;
    }

    public static void setSelectedUnits(ArrayList<Unit> selectedUnits) {
        UnitMenuController.selectedUnits = selectedUnits;
    }

    public static UnitType selectedUnitsType() {
        return selectedUnits.get(0).getType();
    }

    public static UnitMenuMessages moveUnit(int x, int y) {
        Map map = GameMenuController.getCurrentGame().getMap();
        // since all of the selected units are located in the same cell, we can bring
        // this line out of the loop
        // All units are of the same type, so they have the same pace
        int pace = selectedUnits.get(0).getPace();
        LinkedList<Coordinates> path = PathFinder.getPath(map, unitsX, unitsY, x, y);
        int length = path.size() - 1;
        if (length == 0) {
            return UnitMenuMessages.ALREADY_DONE;
        }
        if (length < 0 || length > pace / 10) {
            return UnitMenuMessages.CANT_GO_THERE;
        }

        for (Unit unit : selectedUnits) {
            map.moveUnit(unit, x, y);
        }
        setUnitsXAndY(x,y);
        return UnitMenuMessages.DONE_SUCCESSFULLY;
    }

    public static UnitMenuMessages patrolUnit(int x1, int y1, int x2, int y2) {
        return null;
    }

    public static UnitMenuMessages setState(UnitState state) {
        for (Unit unit : selectedUnits) {
            unit.setState(state);
        }
        return UnitMenuMessages.DONE_SUCCESSFULLY;
    }

    public static UnitMenuMessages attack(int enemiesX, int enemiesY) {
        return null;
    }

    public static UnitMenuMessages pourOil(String direction) {
        return null;
    }

    public static UnitMenuMessages digTunnel(int x, int y) {
        if (!Validation.areCoordinatesValid(x,y))
            return UnitMenuMessages.INVALID_PLACE;
        if (!GameMenuController.getCurrentGame().getMap().findCellWithXAndY(x,y).canDigTunnel())
            return UnitMenuMessages.CANT_DIG;
        LinkedList<Coordinates> path = PathFinder.getPath(GameMenuController.getCurrentGame().getMap(), unitsX, unitsY, x, y);
        int length = path.size() - 1;
        if (length == 0)
            return UnitMenuMessages.ALREADY_DONE;
        if (length == -1 || length > selectedUnits.get(0).getPace()/10)
            return UnitMenuMessages.CANT_GO_THERE;

        for (Coordinates coordinate : path) {
            coordinate.getCellWithCoordinates(GameMenuController.getCurrentGame().getMap()).destroyObjects();
        }
        for (Unit unit : selectedUnits) {
            GameMenuController.getCurrentGame().getMap().moveUnit(unit, x, y);
        }
        setUnitsXAndY(x,y);
        return UnitMenuMessages.DONE_SUCCESSFULLY;
    }

    public static UnitMenuMessages build(String equipmentName) {
        if (!selectedUnits.get(0).getType().equals(UnitType.ENGINEER))
            return UnitMenuMessages.NOT_ENGINEER;

        return null;
    }

    public static UnitMenuMessages dropLadder(int x, int y) {
        if (!Validation.areCoordinatesValid(x,y))
            return UnitMenuMessages.INVALID_PLACE;
        LinkedList<Coordinates> path = PathFinder.getPath(GameMenuController.getCurrentGame().getMap(), unitsX, unitsY, x, y);
        int length = path.size() - 1;
        if (length == -1 || length > selectedUnits.get(0).getPace()/10)
            return UnitMenuMessages.CANT_GO_THERE;
        GameMenuController.getCurrentGame().getMap().findCellWithXAndY(x,y).setHasLadder(true);
        return UnitMenuMessages.DONE_SUCCESSFULLY;
    }
}
