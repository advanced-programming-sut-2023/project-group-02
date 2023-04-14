package models.units;

import models.Building;

public class Unit {

    private UnitQuantity damage;
    private UnitQuantity hitpoint;
    private UnitQuantity pace;

    private int price;
    private UnitState state;
    private UnitType type;
    private boolean isLongRange;
    private boolean canDigMoat;
    private boolean canClimbLadder;
    // private Building whereCanBeTrained; // Barracks, Engineer guild or cathedral.
    // private Arraylist<Equipments> ---> weapons, armory and horse, if they ride them.
    public Unit(UnitQuantity damage, UnitQuantity hitpoint, UnitQuantity pace,
                int price, UnitState state, UnitType type,
                boolean isLongRange, boolean canDigMoat, boolean canClimbLadder) {
        this.damage = damage;
        this.hitpoint = hitpoint;
        this.pace = pace;
        this.price = price;
        this.state = state;
        this.type = type;
        this.isLongRange = isLongRange;
        this.canDigMoat = canDigMoat;
        this.canClimbLadder = canClimbLadder;
    }

    public UnitQuantity getDamage() {
        return damage;
    }

    public UnitQuantity getHitpoint() {
        return hitpoint;
    }

    public UnitQuantity getPace() {
        return pace;
    }

    public UnitType getType() {
        return type;
    }

    public UnitState getState() {
        return state;
    }

    public void setState(UnitState state) {
        this.state = state;
    }
}
