package controllers;

import models.Food;
import models.Material;
import utils.Utils;
import view.enums.ShopMenuMessages;

import java.util.ArrayList;
import java.util.List;

public class ShopMenuController {
    private static final ArrayList<Object> thingsToBuy = new ArrayList<>(
        List.of(Material.STONE,Material.WOOD,Material.IRON, Food.APPLES,Food.WHEAT,Food.CHEESE,Food.FLOUR,Food.BREAD,Food.MEAT));

    public static String showPrice() {
        String answer = "";
        for (Object o : thingsToBuy) {
            if (o instanceof Material) {
                answer += ((Material) o).getName() + " Buy: " + ((Material) o).getBuyPrice() + " Sell: "
                    + ((Material) o).getSellPrice() + "\n";
            } else if (o instanceof Food) {
                answer += ((Food) o).getName() + " Buy: " + ((Food) o).getBuyPrice() + " Sell: "
                    + ((Food) o).getSellPrice() + "\n";
            }
        }
        return answer.trim();
    }

    public static ShopMenuMessages buyItem(String itemName, int amount) {
        Material material;
        Food food = null;
        if ((material = findMaterialInShopWithName(itemName)) == null && (food = findFoodInShopWithName(itemName)) == null)
            return ShopMenuMessages.NOT_IN_MARKET;
        if (amount < 0)
            return ShopMenuMessages.INVALID_AMOUNT;

        buyOperation(material,food,amount);
        return ShopMenuMessages.DONE_SUCCESSFULLY;
    }

    public static ShopMenuMessages sellItem(String itemName, int amount) {
        Material material;
        Food food = null;
        if ((material = findMaterialInShopWithName(itemName)) == null && (food = findFoodInShopWithName(itemName)) == null)
            return ShopMenuMessages.NOT_IN_MARKET;
        if (amount < 0)
            return ShopMenuMessages.INVALID_AMOUNT;

        sellOperation(material,food,amount);
        return ShopMenuMessages.DONE_SUCCESSFULLY;
    }

    private static Food findFoodInShopWithName(String name) {
        for (Object o : thingsToBuy) {
            if (o instanceof Food && ((Food) o).getName().equals(Utils.toCamelCase(name)))
                return (Food) o;
        }
        return null;
    }

    private static Material findMaterialInShopWithName(String name) {
        for (Object o : thingsToBuy) {
            if (o instanceof Material && ((Material) o).getName().equals(Utils.toCamelCase(name)))
                return (Material) o;
        }
        return null;
    }

    private static void sellOperation(Material material, Food food, int amount) {
        if (material != null)
            sellMaterialOperation(material,amount);
        if (food != null)
            sellFoodOperation(food,amount);
    }

    private static void sellMaterialOperation(Material material, int amount) {
        GameMenuController.getCurrentGame().getCurrentPlayersGovernment().increaseMaterial(Material.GOLD,
            amount * material.getSellPrice());
        GameMenuController.getCurrentGame().getCurrentPlayersGovernment().reduceMaterial(material, amount);
    }

    private static void sellFoodOperation(Food food, int amount) {
        GameMenuController.getCurrentGame().getCurrentPlayersGovernment().reduceMaterial(Material.GOLD,
            amount * food.getBuyPrice());
        GameMenuController.getCurrentGame().getCurrentPlayersGovernment().reduceFood(food, amount);
    }

    private static void buyOperation(Material material, Food food, int amount) {
        if (material != null)
            buyMaterialOperation(material,amount);
        if (food != null)
            buyFoodOperation(food,amount);
    }

    private static void buyMaterialOperation(Material material, int amount) {
        GameMenuController.getCurrentGame().getCurrentPlayersGovernment().reduceMaterial(Material.GOLD,
            amount * material.getBuyPrice());
        GameMenuController.getCurrentGame().getCurrentPlayersGovernment().increaseMaterial(material, amount);
    }

    private static void buyFoodOperation(Food food, int amount) {
        GameMenuController.getCurrentGame().getCurrentPlayersGovernment().reduceMaterial(Material.GOLD,
            amount * food.getBuyPrice());
        GameMenuController.getCurrentGame().getCurrentPlayersGovernment().increaseFood(food, amount);
    }
}
