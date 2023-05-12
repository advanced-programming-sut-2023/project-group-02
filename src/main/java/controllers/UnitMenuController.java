package controllers;

import java.util.ArrayList;
import java.util.LinkedList;

import models.Coordinates;
import models.Map;
import models.units.Unit;
import models.units.UnitState;
import utils.PathFinder;
import view.enums.UnitMenuMessages;

public class UnitMenuController {
    private static ArrayList<Unit> selectedUnits;

    public static ArrayList<Unit> getSelectedUnits() {
        return selectedUnits;
    }

    public static void setSelectedUnits(ArrayList<Unit> selectedUnits) {
        UnitMenuController.selectedUnits = selectedUnits;
    }

    public static UnitMenuMessages moveUnit(int x, int y) {
        Map map = GameMenuController.getCurrentGame().getMap();
        // since all of the selected units are located in the same cell, we can bring
        // this line out of the loop
        Coordinates startingPoint = map.locateUnit(selectedUnits.get(0));
        for (Unit unit : selectedUnits) {
            LinkedList<Coordinates> path = PathFinder.getPath(map, startingPoint.x, startingPoint.y, x, y);
            int length = path.size() - 1;
            if (length == 0) {
                return UnitMenuMessages.ALREADY_DONE;
            }
            if (length < 0 || length > unit.getPace() / 10) {
                return UnitMenuMessages.CANT_GO_THERE;
            }
            map.moveUnit(unit, x, y);
        }
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
        return null;
    }

    public static UnitMenuMessages build(String equipmentName) {
        return null;
    }
}
