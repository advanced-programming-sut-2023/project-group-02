package models.units;

import models.MartialEquipment;

import java.util.ArrayList;
import java.util.Arrays;

public enum UnitType {
    //  EUROPEAN TROOPS
    ARCHER("Archer", "Barrack", 12, MartialEquipment.BOW),
    CROSSBOWMEN("Crossbowman", "Barrack", 20, MartialEquipment.CROSSBOW, MartialEquipment.LEATHER_ARMOUR),
    SPEARMEN("Spearman", "Barrack", 8, MartialEquipment.SPEAR),
    PIKEMEN("Pikeman", "Barrack", 20, MartialEquipment.PIKE, MartialEquipment.METAL_ARMOUR),
    MACEMEN("Maceman", "Barrack", 20, MartialEquipment.MACE, MartialEquipment.LEATHER_ARMOUR),
    SWORDSMEN("Swordman", "Barrack", 40, MartialEquipment.SWORD, MartialEquipment.METAL_ARMOUR),
    KNIGHT("Knight", "Barrack", 40, MartialEquipment.SWORD, MartialEquipment.METAL_ARMOUR, MartialEquipment.HORSE),
    TUNNELER("Tunneler", "Engineer Guild", 30),
    LADDERMEN("Ladderman", "Engineer Guild", 4),
    ENGINEER("Engineer", "Engineer Guild", 30),
    BLACK_MONK("Black Monk", "Cathedral", 10),
    // ARABIAN TROOPS
    ARCHER_BOW("Archer Bow", "Mercenary Post", 75),
    SLAVES("Slave", "Mercenary Post", 5),
    SLINGERS("Slinger", "Mercenary Post", 12),
    ASSASSINS("Assassin", "Mercenary Post", 60),
    HORSE_ARCHERS("Horse Archer", "Mercenary Post", 80),
    ARABIAN_SWORDSMEN("Arabian Swordman", "Mercenary Post", 80),
    FIRE_THROWERS("Fire Thrower", "Mercenary Post", 100);

    private final String name;
    private final String whereCanBeTrained;
    private final ArrayList<MartialEquipment> martialEquipmentsNeeded = new ArrayList<>();
    private final int price;

    UnitType(String name, String whereCanBeTrained, int price, MartialEquipment... martialEquipments) {
        this.name = name;
        this.whereCanBeTrained = whereCanBeTrained;
        this.price = price;
        martialEquipmentsNeeded.addAll(Arrays.asList(martialEquipments));
    }

    public String getName() {
        return name;
    }

    public String getWhereCanBeTrained() {
        return whereCanBeTrained;
    }

    public int getPrice() {
        return price;
    }

    public ArrayList<MartialEquipment> getMartialEquipmentsNeeded() {
        return martialEquipmentsNeeded;
    }
}
