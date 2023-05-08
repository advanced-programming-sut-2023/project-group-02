package models.units;

import models.Building;
import models.MartialEquipment;
import models.User;

import java.util.ArrayList;

public class Unit {
    private User owner;
    private int currentX;
    private int currentY;
    private final int damage; //out of 100
    private final int hitpoint; //out of 100
    private final int pace; //out of 100
    private final int price;
    private UnitState state;
    private final UnitType type;
    private final boolean isLongRange;
    private final boolean canDigMoat;
    private final boolean canClimbLadder;
    private String whereCanBeTrained; // Barracks, Engineer guild or cathedral.
    private final ArrayList<MartialEquipment> equipments = new ArrayList<>();

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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCoordinates(int x, int y) {
        currentX = x;
        currentY = y;
    }
}
