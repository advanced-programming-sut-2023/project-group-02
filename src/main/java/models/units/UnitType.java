package models.units;

import models.MartialEquipment;

import java.util.ArrayList;
import java.util.Arrays;

public enum UnitType {
    //  EUROPEAN TROOPS
    ARCHER("Archer", MartialEquipment.BOW),
    CROSSBOWMEN("Crossbowman", MartialEquipment.CROSSBOW, MartialEquipment.LEATHER_ARMOUR),
    SPEARMEN("Spearman", MartialEquipment.SPEAR),
    PIKEMEN("Pikeman", MartialEquipment.PIKE, MartialEquipment.METAL_ARMOUR),
    MACEMEN("Maceman", MartialEquipment.MACE, MartialEquipment.LEATHER_ARMOUR),
    SWORDSMEN("Swordman", MartialEquipment.SWORD, MartialEquipment.METAL_ARMOUR),
    KNIGHT("Knight", MartialEquipment.SWORD, MartialEquipment.METAL_ARMOUR, MartialEquipment.HORSE),
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
    private final ArrayList<MartialEquipment> martialEquipmentsNeeded = new ArrayList<>();

    UnitType(String name, MartialEquipment... martialEquipments) {
        this.name = name;
        martialEquipmentsNeeded.addAll(Arrays.asList(martialEquipments));
    }

    public String getName() {
        return name;
    }

    public ArrayList<MartialEquipment> getMartialEquipmentsNeeded() {
        return martialEquipmentsNeeded;
    }
}
