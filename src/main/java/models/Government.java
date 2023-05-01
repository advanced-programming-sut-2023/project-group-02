package models;

import controllers.GameMenuController;
import models.buildings.InventoryBuilding;

import java.util.ArrayList;

public class Government {
    private User user;
    private Map map;
    private int popularity;
    private int foodRate = -2;
    private int taxRate = 0;
    private int fearRate = 0;
    private final int[] foodStock = new int[4];
    private final ArrayList<People> people = new ArrayList<>();

    public void setMap(Map map) {
        this.map = map;
    }

    public int getMaterialAmount(Material material, User player) {
        int amount;
        ArrayList<Building> buildings = map.getPlayersBuildings(player);
        for (Building building : buildings) {
            if (building instanceof InventoryBuilding<?>)
                amount += ((InventoryBuilding<?>) building).getAmount(material); //TODO what must i do?
        }
        return amount;
    }

    public void reduceMaterial(Material material, int amount, User player) {
        //TODO go and reduce golds in storage
    }

    public void increaseMaterial(Material material, int amount, User player) {
        //TODO go and increase golds int storage
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

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public int getFearRate() {
        return fearRate;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
    }

    public int[] getFoodStock() {
        return foodStock;
    }

    public int getPopulation() {
        return people.size();
    }
    public void addToFoodStock(int foodType, int value) {
        this.foodStock[foodType] += value;
    }
}
