package models.units;

public enum UnitType {
    //  EUROPEAN TROOPS
    ARCHER("Archer"),
    CROSSBOWMEN("Crossbowman"),
    SPEARMEN("Spearman"),
    PIKEMEN("Pikeman"),
    MACEMEN("Maceman"),
    SWORDSMEN("Swordman"),
    KNIGHT("Knight"),
    TUNNELER("Tunneler"),
    LADDERMEN("Ladderman"),
    ENGINEER("Engineer"),
    BLACK_MONK("Black Monk"),
    // ARABIAN TROOPS
    ARCHER_BOW("Archer Bow"),
    SLAVES("Slave"),
    SLINGERS("Slinger"),
    ASSASSINS("Assassin"),
    HORSE_ARCHERS("Horse Archer"),
    ARABIAN_SWORDSMEN("Arabian Swordman"),
    FIRE_THROWERS("Fire Thrower");

    private final String name;

    UnitType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
