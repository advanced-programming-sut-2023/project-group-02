package models.units;

import com.google.gson.annotations.Until;
import models.Building;
import utils.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class MakeUnitInstances {
    public static Unit makeArcher() {
        return new Unit(40, 45, 90,
            12, UnitState.STANDING, UnitType.ARCHER,
            true, true, true);
    }

    public static Unit makeCrossbowman() {
        return new Unit(70, 75, 55,
            20, UnitState.STANDING, UnitType.CROSSBOWMAN,
            true, false, false);
    }

    public static Unit makeSpearman() {
        return new Unit(50, 50, 60,
            8, UnitState.STANDING, UnitType.SPEARMAN,
            false, true, true);
    }

    public static Unit makePikeman() {
        return new Unit(76, 77, 60,
            20, UnitState.STANDING, UnitType.PIKEMAN,
            false, true, false);
    }

    public static Unit makeMaceman() {
        return new Unit(75, 70, 85,
            20, UnitState.STANDING, UnitType.MACEMAN,
            false, true, true);
    }

    public static Unit makeSwordsman() {
        return new Unit(94, 94, 40,
            40, UnitState.STANDING, UnitType.SWORDSMAN,
            false, false, false);
    }

    public static Unit makeKnight() {
        return new Unit(94, 90, 95,
            40, UnitState.STANDING, UnitType.KNIGHT,
            false, false, false);
    }

    public static Unit makeTunneler() {
        return new Unit(45, 30, 90,
            30, UnitState.STANDING, UnitType.TUNNELER,
            false, false, false);
    }

    public static Unit makeLadderman() {
        return new Unit(0, 30, 90,
            4, UnitState.STANDING, UnitType.LADDERMAN,
            false, false, false);
    }

    public static Unit makeEngineer() {
        return new Unit(0, 30, 60,
            30, UnitState.STANDING, UnitType.ENGINEER,
            false, true, false);
    }

    public static Unit makeBlackMonk() {
        return new Unit(75, 50, 55,
            10, UnitState.STANDING, UnitType.BLACK_MONK,
            false, false, false);
    }

    public static Unit makeArcherBow() {
        return new Unit(45, 45, 90,
            75, UnitState.STANDING, UnitType.ARCHER_BOW,
            true, false, false);
    }

    public static Unit makeSlave() {
        return new Unit(10, 20, 90,
            5, UnitState.STANDING, UnitType.SLAVE,
            false, true, false);
    }

    public static Unit makeSlinger() {
        return new Unit(36, 20, 90,
            12, UnitState.STANDING, UnitType.SLINGER,
            true, false, false);
    }

    public static Unit makeAssassin() {
        return new Unit(76, 73, 67,
            60, UnitState.STANDING, UnitType.ASSASSIN,
            false, false, false);
    }

    public static Unit makeHorseArcher() {
        return new Unit(50, 55, 95,
            80, UnitState.STANDING, UnitType.HORSE_ARCHER,
            true, false, false);
    }

    public static Unit makeArabianSwordsman() {
        return new Unit(88, 88, 40,
            80, UnitState.STANDING, UnitType.ARABIAN_SWORDSMAN,
            false, false, false);
    }

    public static Unit makeFireThrower() {
        return new Unit(84, 60, 60,
            100, UnitState.STANDING, UnitType.FIRE_THROWER,
            true, false, false);
    }

    public static Unit makeLord() {
        return new Unit(100, 100, 55,
                0, UnitState.STANDING, UnitType.LORD,
                false, false, false);
    }

    public static Unit createUnitInstance(String name) {
        name = Utils.toCamelCase("make " + name);
        Method[] methods = MakeUnitInstances.class.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(name)) {
                try {
                    Unit unit = (Unit) method.invoke(null);
                    if (unit.getType().equals(UnitType.LORD)) {
                        continue;
                    }
                    return unit;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static ArrayList<Unit> getAllUnits() {
        ArrayList<Unit> allUnits = new ArrayList<>();
        Method[] methods = MakeUnitInstances.class.getMethods();
        for (Method method : methods) {
            if (method.getParameterCount() == 0 && Unit.class.isAssignableFrom(method.getReturnType())) {
                try {
                    Unit unit = (Unit) method.invoke(null);
                    if (unit != null && !unit.getName().equals("Lord")) {
                        allUnits.add(unit);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return allUnits;
    }

    public static ArrayList<Unit> getUnitsBasedOfType(String placeToMake) {
        ArrayList<Unit> units = new ArrayList<>();
        for (Unit unit : getAllUnits()) {
            if (unit.getType().getWhereCanBeTrained() != null && unit.getType().getWhereCanBeTrained().equals(placeToMake))  units.add(unit);
        }
        return units;
    }
}
