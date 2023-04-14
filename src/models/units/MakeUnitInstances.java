package models.units;

public class MakeUnitInstances {
    public static Unit makeArcher() {
        return new Unit(UnitQuantity.LOW, UnitQuantity.LOW, UnitQuantity.HIGH,
                12, UnitState.STANDING, UnitType.ARCHER,
                true, true, true);
    }

    public static Unit makeCrossbowmen() {
        return new Unit(UnitQuantity.LOW, UnitQuantity.MEDIUM, UnitQuantity.LOW,
                20, UnitState.STANDING, UnitType.CROSSBOWMEN,
                true, false, false);
    }

    public static Unit makeSpearmen() {
        return new Unit(UnitQuantity.MEDIUM, UnitQuantity.VERY_LOW, UnitQuantity.MEDIUM,
                8, UnitState.STANDING, UnitType.SPEARMEN,
                false, true, true);
    }

    public static Unit makePikemen() {
        return new Unit(UnitQuantity.MEDIUM, UnitQuantity.HIGH, UnitQuantity.LOW,
                20, UnitState.STANDING, UnitType.PIKEMEN,
                false, true, false);
    }

    public static Unit makeMacemen() {
        return new Unit(UnitQuantity.HIGH, UnitQuantity.MEDIUM, UnitQuantity.MEDIUM,
                20, UnitState.STANDING, UnitType.MACEMEN,
                false, true, true);
    }

    public static Unit makeSwordsmen() {
        return new Unit(UnitQuantity.VERY_HIGH, UnitQuantity.VERY_HIGH, UnitQuantity.VERY_LOW,
                20, UnitState.STANDING, UnitType.SWORDSMEN,
                false, false, false);
    }

    public static Unit makeKnight() {
        return new Unit(UnitQuantity.VERY_HIGH, UnitQuantity.HIGH, UnitQuantity.VERY_HIGH,
                40, UnitState.STANDING, UnitType.KNIGHT,
                false, false, false);
    }

    public static Unit makeTunneler() {
        return new Unit(UnitQuantity.MEDIUM, UnitQuantity.VERY_LOW, UnitQuantity.HIGH,
                30, UnitState.STANDING, UnitType.TUNNELER,
                false, false, false);
    }

    public static Unit makeLaddermen() {
        return new Unit(UnitQuantity.NULL, UnitQuantity.VERY_LOW, UnitQuantity.HIGH,
                4, UnitState.STANDING, UnitType.LADDERMEN,
                false, false, false);
    }

    public static Unit makeEngineer() {
        return new Unit(UnitQuantity.NULL, UnitQuantity.VERY_LOW, UnitQuantity.MEDIUM,
                30, UnitState.STANDING, UnitType.ENGINEER,
                false, true, false);
    }

    public static Unit makeBlackMonk() {
        return new Unit(UnitQuantity.MEDIUM, UnitQuantity.MEDIUM, UnitQuantity.LOW,
                10, UnitState.STANDING, UnitType.BLACK_MONK,
                false, false, false);
    }

    public static Unit makeArcherBow() {
        return new Unit(UnitQuantity.LOW, UnitQuantity.LOW, UnitQuantity.HIGH,
                75, UnitState.STANDING, UnitType.ARCHER_BOW,
                true, false, false);
    }

    public static Unit makeSlaves() {
        return new Unit(UnitQuantity.VERY_LOW, UnitQuantity.VERY_VERY_LOW, UnitQuantity.HIGH,
                5, UnitState.STANDING, UnitType.SLAVES,
                false, true, false);
    }

    public static Unit makeSlingers() {
        return new Unit(UnitQuantity.LOW, UnitQuantity.VERY_LOW, UnitQuantity.HIGH,
                12, UnitState.STANDING, UnitType.SLINGERS,
                false, false, false);
    }

    public static Unit makeAssassins() {
        return new Unit(UnitQuantity.MEDIUM, UnitQuantity.MEDIUM, UnitQuantity.MEDIUM,
                60, UnitState.STANDING, UnitType.ASSASSINS,
                false, false, false);
    }

    public static Unit makeHorseArchers() {
        return new Unit(UnitQuantity.LOW, UnitQuantity.MEDIUM, UnitQuantity.VERY_HIGH,
                80, UnitState.STANDING, UnitType.HORSE_ARCHERS,
                true, false, false);
    }

    public static Unit makeArabianSwordsmen() {
        return new Unit(UnitQuantity.HIGH, UnitQuantity.HIGH, UnitQuantity.VERY_LOW,
                80, UnitState.STANDING, UnitType.ARABIAN_SWORDSMEN,
                false, false, false);
    }

    public static Unit makeFireThrowers() {
        return new Unit(UnitQuantity.HIGH, UnitQuantity.LOW, UnitQuantity.VERY_HIGH,
                100, UnitState.STANDING, UnitType.FIRE_THROWERS,
                false, false, false);
    }
}
