package models;

import models.buildings.InventoryBuilding;

import java.util.ArrayList;
import java.util.HashSet;

public class Government {
    private User user;
    private Map map;

    private int popularity = 0;
    private HashSet<Food> foodStock = new HashSet<>(4);
    private int foodRate = -2;
    private static final int MIN_FOOD_RATE = -2;
    private static final int MAX_FOOD_RATE = 2;
    private int taxRate = 0;
    private static final int MIN_TAX_RATE = -3;
    private static final int MAX_TAX_RATE = 8;
    private int fearRate = 0;
    private static final int MIN_FEAR_RATE = -5;
    private static final int MAX_FEAR_RATE = 5;

    private final ArrayList<People> people = new ArrayList<>();

    public void setMap(Map map) {
        this.map = map;
    }

    public int getMaterialAmount(Material material) {
        int amount = 0;
        ArrayList<Building> buildings = map.getPlayersBuildings(user);
        for (Building building : buildings) {
            InventoryBuilding<Material> inventoryBuilding = InventoryBuilding.castIfPossible(building, material);
            if (inventoryBuilding != null) {
                amount += inventoryBuilding.getAmount(material);
            }
        }
        return amount;
    }

    public void reduceMaterial(Material material, int amount) {
        int amountLeft = amount;
        ArrayList<Building> buildings = map.getPlayersBuildings(user);
        for (Building building : buildings) {
            if (amountLeft == 0)
                break;
            InventoryBuilding<Material> inventoryBuilding = InventoryBuilding.castIfPossible(building, material);
            if (inventoryBuilding != null) {
                amountLeft = inventoryBuilding.decreaseItem(material, amountLeft);
            }
        }
    }

    public void increaseMaterial(Material material, int amount) {
        int amountLeft = amount;
        ArrayList<Building> buildings = map.getPlayersBuildings(user);
        for (Building building : buildings) {
            if (amountLeft == 0)
                break;
            InventoryBuilding<Material> inventoryBuilding = InventoryBuilding.castIfPossible(building, material);
            if (inventoryBuilding != null) {
                amountLeft = inventoryBuilding.increaseItem(material, amountLeft);
            }
        }
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public boolean setFoodRate(int foodRate) {
        if (foodRate >= MIN_FOOD_RATE && foodRate <= MAX_FOOD_RATE) {
            this.foodRate = foodRate;
            return true;
        } else {
            return false;
        }
    }

    public int getTaxRate() {
        return taxRate;
    }

    public boolean setTaxRate(int taxRate) {
        if (taxRate >= MIN_TAX_RATE && taxRate <= MAX_TAX_RATE) {
            this.taxRate = taxRate;
            return true;
        } else {
            return false;
        }
    }

    public int getFearRate() {
        return fearRate;
    }

    public boolean setFearRate(int fearRate) {
        if (fearRate >= MIN_FEAR_RATE && fearRate <= MAX_FEAR_RATE) {
            this.fearRate = fearRate;
            return true;
        } else {
            return false;
        }
    }

    public HashSet<Food> getFoodStock() {
        return foodStock;
    }

    public int getPopulation() {
        return people.size();
    }

    public void addToFoodStock(Food food) {
        if (foodStock.size() < 4) {
            foodStock.add(food);
        }
    }
}
