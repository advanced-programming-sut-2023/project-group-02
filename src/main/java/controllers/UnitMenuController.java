package controllers;

import java.util.ArrayList;

import models.units.Unit;
import models.units.UnitState;
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
        return null;
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
