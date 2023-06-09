package models;

import models.buildings.InventoryBuilding;
import models.buildings.LimitedProductionBuilding;
import models.buildings.PlainBuilding;
import models.buildings.ProductionBuilding;
import models.units.MakeUnitInstances;
import models.units.Unit;
import models.units.UnitType;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
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
    private final ArrayList<Trade> allSentTrades = new ArrayList<>();
    private final ArrayList<Trade> inboxOfTrades = new ArrayList<>();
    private HashMap<String,Integer> amountOfUnits;
    private boolean isLordAlive = true;

    public Government(User user, Colors color) {
        this.user = user;
        this.color = color;
        initializeAmountOfUnits();
    }

    private void initializeAmountOfUnits() {
        amountOfUnits = new HashMap<>();
        for (Unit unit : MakeUnitInstances.getAllUnits()) {
            amountOfUnits.put(unit.getName(),0);
        }
    }

    public HashMap<String, Integer> getAmountOfUnits() {
        return amountOfUnits;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public ArrayList<Trade> getAllSentTrades() {
        return allSentTrades;
    }

    public ArrayList<Trade> getInboxOfTrades() {
        return inboxOfTrades;
    }

    public User getUser() {
        return user;
    }

    public int getItemAmount(Object item) {
        int amount = 0;
        ArrayList<Building> buildings = map.getPlayersBuildings(user);
        for (Building building : buildings) {
            if (!(building instanceof InventoryBuilding))
                continue;
            amount += ((InventoryBuilding) building).getAmount(item);
        }
        return amount;
    }

    public void increaseItem(Object item, int amount) {
        int amountLeft = amount;
        ArrayList<Building> buildings = map.getPlayersBuildings(user);
        for (Building building : buildings) {
            if (amountLeft == 0)
                break;
            if (!(building instanceof InventoryBuilding) || !((InventoryBuilding) building).isItemTypeSuitable(item))
                continue;
            amountLeft = ((InventoryBuilding) building).increaseItem(item, amountLeft);
        }
    }

    public void reduceItem(Object item, int amount) {
        int amountLeft = amount;
        ArrayList<Building> buildings = map.getPlayersBuildings(user);
        for (Building building : buildings) {
            if (amountLeft == 0)
                break;
            if (!(building instanceof InventoryBuilding))
                continue;
            amountLeft = ((InventoryBuilding) building).decreaseItem(item, amountLeft);
        }
    }

    public boolean hasEnoughMaterialsForBuilding(Building building) {
        MaterialInstance[] materialInstances = building.getBuildingMaterials();
        for (MaterialInstance materialInstance : materialInstances) {
            if (materialInstance.amount > getItemAmount(materialInstance.material))
                return false;
        }
        return true;
    }

    public boolean hasEnoughEquipmentsForUnit(String unitType, int number) {
        for (UnitType unitType1 : EnumSet.allOf(UnitType.class)) {
            if (unitType1.getName().equals(unitType)) {
                for (MartialEquipment martialEquipment : unitType1.getMartialEquipmentsNeeded()) {
                    if (this.getItemAmount(martialEquipment) < number)
                        return false;
                }
                if (this.getItemAmount(Material.GOLD) < unitType1.getPrice() * number)
                    return false;
                return true;
            }
        }
        throw new IllegalStateException("unitType is invalid!");
    }

    public void reduceMaterialsForBuilding(Building building) {
        MaterialInstance[] materialInstances = building.getBuildingMaterials();
        for (MaterialInstance materialInstance : materialInstances) {
            reduceItem(materialInstance.material, materialInstance.amount);
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

    // addPeople returns true if all the people are added
    public boolean addPeople(int number) {
        ArrayList<Building> buildings = map.getPlayersBuildings(user);
        for (Building building : buildings) {
            if (building instanceof PlainBuilding) {
                number = ((PlainBuilding) building).addPeople(number);
                if (number == 0)
                    return true;
            }
        }
        return false;
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

    public boolean hasEnoughWorkerForBuilding(Building building) {
        return building.getWorkerCount() <= numberOfUnemployed();
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    private int findSickCellsCount() {
        int count = 0;
        for (int x = 0; x < map.getWidth(); x++)
            for (int y = 0; y < map.getHeight(); y++)
                if (map.findCellWithXAndY(x, y).hasSickness())
                    count++;
        return count;
    }

    public void updatePopularity() {
        popularity -= findSickCellsCount() / 15;
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
        for (Building building : map.getPlayersBuildings(user)) {
            popularity += building.getEffectOnPopularity();
        }
    }

    public void collectTax() {
        float taxForEveryPerson = switch (taxRate) {
            case -3 -> -1;
            case -2 -> -0.8f;
            case -1 -> -0.6f;
            case 0 -> 0;
            case 1 -> 0.6f;
            case 2 -> 0.8f;
            case 3 -> 1;
            case 4 -> 1.2f;
            case 5 -> 1.4f;
            case 6 -> 1.6f;
            case 7 -> 1.8f;
            case 8 -> 2;
            default -> 0; // unreachable
        };
        int totalAmount = Math.round(taxForEveryPerson * getPopulation());
        if (totalAmount >= 0) {
            // add gold
            increaseItem(Material.GOLD, totalAmount);
        } else {
            int availableAmount = getItemAmount(Material.GOLD);
            if (totalAmount > availableAmount) {
                // there's not enough gold to give to people, so set the tax rate to zero
                setTaxRate(0);
            } else {
                // since `totalAmount` is negative we can't use `increaseItem`
                reduceItem(Material.GOLD, -totalAmount);
            }
        }
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

    public void givePeopleFood() {
        float foodAmountPerPopulation = switch (foodRate) {
            case -2 -> 0;
            case -1 -> 0.5f;
            case 0 -> 1;
            case 1 -> 1.5f;
            case 2 -> 2;
            default -> 0; // unreachable
        };
        int foodAmount = Math.round(foodAmountPerPopulation * getPopulation());
        for (Food food : foodStock) {
            int totalAmount = getItemAmount(food);
            if (totalAmount >= foodAmount) {
                reduceItem(food, foodAmount);
            }
            if (totalAmount <= foodAmount) {
                foodStock.remove(food);
            }
        }
    }

    public void doTheProductions() {
        ArrayList<Building> buildings = map.getPlayersBuildings(user);
        for (Building building : buildings) {
            if (!(building instanceof ProductionBuilding))
                continue;
            ProductionBuilding productionBuilding = (ProductionBuilding) building;
            int amount = productionBuilding.getRate(); // perhaps the rate is per month
            if (building instanceof LimitedProductionBuilding) {
                amount = Math.min(amount, ((LimitedProductionBuilding) building).getCapacity());
            }

            if (productionBuilding.needsMaterial()) {
                if (getItemAmount(productionBuilding.getMaterial()) < amount) {
                    continue;
                }
                // TODO: use a variable amount
                reduceItem(productionBuilding.getMaterial(), amount);
            }
            for (Object product : productionBuilding.getProducts()) {
                increaseItem(product, Math.round(amount * (1 + fearRate * 4 / 100)));
            }
        }
    }

    public PlainBuilding getSmallStone() {
        ArrayList<Building> buildings = map.getPlayersBuildings(user);
        for (Building building : buildings) {
            if (building.getName().equals("Small Stone Gate"))
                return (PlainBuilding) building;
        }
        return null;
    }

    public boolean isLordAlive() {
        return isLordAlive;
    }

    public void lordDied() {
        isLordAlive = false;
    }

    public void removeTroop() {
        ArrayList<Building> buildings = map.getPlayersBuildings(user);
        for (Building building : buildings) {
            if (building instanceof PlainBuilding) {
                for (People people : ((PlainBuilding) building).getPeople()) {
                    if (people.getWorkingPlace().equals(getSmallStone())) {
                        ((PlainBuilding) building).getPeople().remove(people);
                        return;
                    }
                }
            }
        }
    }
}
