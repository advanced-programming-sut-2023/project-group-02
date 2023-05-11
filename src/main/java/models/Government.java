package models;

import models.buildings.InventoryBuilding;
import models.buildings.PlainBuilding;

import java.util.ArrayList;
import java.util.HashSet;

public class Government {
    private User user;
    private Colors color;
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

    public Government(User user, Colors color) {
        this.user = user;
        this.color = color;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public User getUser() {
        return user;
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

    public void reduceFood(Food food, int amount) {
        int amountLeft = amount;
        ArrayList<Building> buildings = map.getPlayersBuildings(user);
        for (Building building : buildings) {
            if (amountLeft == 0)
                break;
            InventoryBuilding<Food> inventoryBuilding = InventoryBuilding.castIfPossible(building, food);
            if (inventoryBuilding != null) {
                amountLeft = inventoryBuilding.decreaseItem(food, amountLeft);
            }
        }
    }

    public void increaseFood(Food food, int amount) {
        int amountLeft = amount;
        ArrayList<Building> buildings = map.getPlayersBuildings(user);
        for (Building building : buildings) {
            if (amountLeft == 0)
                break;
            InventoryBuilding<Food> inventoryBuilding = InventoryBuilding.castIfPossible(building, food);
            if (inventoryBuilding != null) {
                amountLeft = inventoryBuilding.increaseItem(food, amountLeft);
            }
        }
    }

    public boolean hasEnoughMaterialsForBuilding(Building building) {
        MaterialInstance[] materialInstances = building.getBuildingMaterials();
        for (MaterialInstance materialInstance : materialInstances) {
            if (materialInstance.amount > getMaterialAmount(materialInstance.material))
                return false;
        }
        return true;
    }

    public void reduceMaterialsForBuilding(Building building) {
        MaterialInstance[] materialInstances = building.getBuildingMaterials();
        for (MaterialInstance materialInstance : materialInstances) {
            increaseMaterial(materialInstance.material, materialInstance.amount);
        }
    }

    public int getPopulation() {
        int population = 0;
        ArrayList<Building> buildings = map.getPlayersBuildings(user);
        for (Building building : buildings) {
            if (building instanceof PlainBuilding) {
                population += ((PlainBuilding) building).getPopulation();
            }
        }
        return population;
    }

    public int numberOfUnemployed() {
        int unemployed = 0;
        ArrayList<Building> buildings = map.getPlayersBuildings(user);
        for (Building building : buildings) {
            if (building instanceof PlainBuilding) {
                unemployed += ((PlainBuilding) building).numberOfUnemployedPeople();
            }
        }
        return unemployed;
    }

    public void recruitPeople(int number, Building workingPlace) {
        ArrayList<Building> buildings = map.getPlayersBuildings(user);
        for (Building building : buildings) {
            if (building instanceof PlainBuilding) {
                number = ((PlainBuilding) building).recruit(workingPlace, number);
                if (number == 0)
                    return;
            }
        }
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public void updatePopularity() {
        popularity += foodRate * 4;
        popularity += switch (taxRate) {
            case -3 -> 7;
            case -2 -> 5;
            case -1 -> 3;
            case 0 -> 1;
            case 1 -> -2;
            case 2 -> -4;
            case 3 -> -6;
            case 4 -> -8;
            case 5 -> -12;
            case 6 -> -16;
            case 7 -> -20;
            case 8 -> -24;
            default -> 0; // unreachable
        };
        popularity += foodStock.size() - 1;
        popularity -= fearRate;
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

    public void addToFoodStock(Food food) {
        if (foodStock.size() < 4) {
            foodStock.add(food);
        }
    }

    public Building getSmallStone() {
        ArrayList<Building> buildings = map.getPlayersBuildings(user);
        for (Building building : buildings) {
            if (building.getName().equals("Small Stone Gate"))
                return building;
        }
        return null;
    }
}
