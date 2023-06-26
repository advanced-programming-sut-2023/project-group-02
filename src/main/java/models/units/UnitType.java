package models.units;

import models.MartialEquipment;

import java.util.ArrayList;
import java.util.Arrays;

public enum UnitType {
    //  EUROPEAN TROOPS
    ARCHER("Archer", "Barrack", 12, MartialEquipment.BOW),
    CROSSBOWMAN("Crossbowman", "Barrack", 20, MartialEquipment.CROSSBOW, MartialEquipment.LEATHER_ARMOUR),
    SPEARMAN("Spearman", "Barrack", 8, MartialEquipment.SPEAR),
    PIKEMAN("Pikeman", "Barrack", 20, MartialEquipment.PIKE, MartialEquipment.METAL_ARMOUR),
    MACEMAN("Maceman", "Barrack", 20, MartialEquipment.MACE, MartialEquipment.LEATHER_ARMOUR),
    SWORDSMAN("Swordsman", "Barrack", 40, MartialEquipment.SWORD, MartialEquipment.METAL_ARMOUR),
    KNIGHT("Knight", "Barrack", 40, MartialEquipment.SWORD, MartialEquipment.METAL_ARMOUR, MartialEquipment.HORSE),
    TUNNELER("Tunneler", "Engineer Guild", 30),
    LADDERMAN("Ladderman", "Engineer Guild", 4),
    ENGINEER("Engineer", "Engineer Guild", 30),
    BLACK_MONK("Black Monk", "Cathedral", 10),
    // ARABIAN TROOPS
    ARCHER_BOW("Archer Bow", "Mercenary Post", 75),
    SLAVE("Slave", "Mercenary Post", 5),
    SLINGER("Slinger", "Mercenary Post", 12),
    ASSASSIN("Assassin", "Mercenary Post", 60),
    HORSE_ARCHER("Horse Archer", "Mercenary Post", 80),
    ARABIAN_SWORDSMAN("Arabian Swordman", "Mercenary Post", 80),
    FIRE_THROWER("Fire Thrower", "Mercenary Post", 100),
    LORD("Lord", null, 0);

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
