package models.units;

import models.Building;

public class Unit {

    private int damage; //out of 100
    private int hitpoint; //out of 100
    private int pace; //out of 100
    private int price;
    private UnitState state;
    private UnitType type;
    private boolean isLongRange;
    private boolean canDigMoat;
    private boolean canClimbLadder;
    // private Building whereCanBeTrained; // Barracks, Engineer guild or cathedral.
    // private Arraylist<Equipments> ---> weapons, armory and horse, if they ride them.
    public Unit(int damage, int hitpoint, int pace,
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

    public int getDamage() {
        return damage;
    }

    public int getHitpoint() {
        return hitpoint;
    }

    public int getPace() {
        return pace;
    }

    public UnitType getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public boolean isLongRange() {
        return isLongRange;
    }

    public boolean isCanDigMoat() {
        return canDigMoat;
    }

    public boolean isCanClimbLadder() {
        return canClimbLadder;
    }

    public UnitState getState() {
        return state;
    }

    public void setState(UnitState state) {
        this.state = state;
    }
}
