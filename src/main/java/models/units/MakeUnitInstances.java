package models.units;

import models.Building;
import models.BuildingFactory;
import utils.Utils;

import java.lang.reflect.Method;

public class MakeUnitInstances {
    public static Unit makeArcher() {
        return new Unit(40, 45, 90,
            12, UnitState.STANDING, UnitType.ARCHER,
            true, true, true);
    }

    public static Unit makeCrossbowmen() {
        return new Unit(70, 75, 55,
            20, UnitState.STANDING, UnitType.CROSSBOWMEN,
            true, false, false);
    }

    public static Unit makeSpearmen() {
        return new Unit(50, 50, 60,
            8, UnitState.STANDING, UnitType.SPEARMEN,
            false, true, true);
    }

    public static Unit makePikemen() {
        return new Unit(76, 77, 60,
            20, UnitState.STANDING, UnitType.PIKEMEN,
            false, true, false);
    }

    public static Unit makeMacemen() {
        return new Unit(75, 70, 85,
            20, UnitState.STANDING, UnitType.MACEMEN,
            false, true, true);
    }

    public static Unit makeSwordsmen() {
        return new Unit(94, 94, 40,
            40, UnitState.STANDING, UnitType.SWORDSMEN,
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

    public static Unit makeLaddermen() {
        return new Unit(0, 30, 90,
            4, UnitState.STANDING, UnitType.LADDERMEN,
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

    public static Unit makeSlaves() {
        return new Unit(10, 20, 90,
            5, UnitState.STANDING, UnitType.SLAVES,
            false, true, false);
    }

    public static Unit makeSlingers() {
        return new Unit(36, 20, 90,
            12, UnitState.STANDING, UnitType.SLINGERS,
            true, false, false);
    }

    public static Unit makeAssassins() {
        return new Unit(76, 73, 67,
            60, UnitState.STANDING, UnitType.ASSASSINS,
            false, false, false);
    }

    public static Unit makeHorseArchers() {
        return new Unit(50, 55, 95,
            80, UnitState.STANDING, UnitType.HORSE_ARCHERS,
            true, false, false);
    }

    public static Unit makeArabianSwordsmen() {
        return new Unit(88, 88, 40,
            80, UnitState.STANDING, UnitType.ARABIAN_SWORDSMEN,
            false, false, false);
    }

    public static Unit makeFireThrowers() {
        return new Unit(84, 60, 60,
            100, UnitState.STANDING, UnitType.FIRE_THROWERS,
            true, false, false);
    }

    public static Unit makeLord() {
        return new Unit(100, 100, 100,
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
}
