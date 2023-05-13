package models.units;

import controllers.GameMenuController;
import controllers.UnitMenuController;
import models.Coordinates;
import models.Map;
import models.User;

import java.util.ArrayList;

public class Unit {
    private User owner;
    private int currentX;
    private int currentY;
    private final int damage; //out of 100
    private int hitpoint; //out of 100
    private final int pace; //out of 100
    private final int price;
    private UnitState state;
    private final UnitType type;
    private final boolean isLongRange;
    private final boolean canDigMoat;
    private final boolean canClimbLadder;
    private ArrayList<Unit> unitsInSight = new ArrayList<>();
    private boolean isInBattle = false;
    private boolean hasMoved = false;

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

    public void setHitpoint(int hitpoint) {
        this.hitpoint = hitpoint;
    }

    public int getPace() {
        return pace;
    }

    public int getMaxDistance() {
        return pace / 10;
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

    public String getName() {
        return type.getName();
    }

    public Coordinates getCoordinates() {
        return new Coordinates(currentX, currentY);
    }

    public void findUnitsInSight(Map map) {
        unitsInSight = new ArrayList<>();
        ArrayList<Coordinates> neighbours = new ArrayList<>();
        if (isLongRange)
            neighbours = map.getMoreNeighbors(new Coordinates(currentX, currentY), 5);
        else
            neighbours = map.getNeighbors(new Coordinates(currentX, currentY));
        for (Coordinates neighbour : neighbours) {
            for (Unit unit : map.findUnitsWithXAndY(neighbour.x, neighbour.y))
                if (!unit.getOwner().getUsername().equals(owner.getUsername()))
                    unitsInSight.add(unit);
        }
        if (unitsInSight.size() > 0)
            isInBattle = true;
        else isInBattle = false;
    }

    public boolean engageWithEnemies(Map map) {
        findUnitsInSight(map);
        if (!isInBattle)
            return false;
        for (Unit enemyUnit : unitsInSight) {
            enemyUnit.setHitpoint(enemyUnit.getHitpoint() - (int) Math.ceil(damage / unitsInSight.size()));
        }
        return true;
    }

    public void attackCloseEnemies(Map map) {
        Coordinates destination = map.findEnemyNear(this, 15);
        if (destination == null)
            return;
        UnitMenuController.moveUnit(destination.x, destination.y);
    }

    public void attackFarEnemies(Map map) {
        Coordinates destination = map.findEnemyNear(this, 30);
        if (destination == null)
            return;
        UnitMenuController.moveUnit(destination.x, destination.y);
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean hasMoved() {
        return hasMoved;
    }
}
