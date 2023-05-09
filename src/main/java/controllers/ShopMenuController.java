package controllers;

import models.Food;
import models.Material;
import utils.Utils;
import view.enums.ShopMenuMessages;

import java.util.ArrayList;
import java.util.List;

public class ShopMenuController {
    public static String showPrice() {
        StringBuilder answer = new StringBuilder();
        for (Material material : ItemsController.getAllMaterials()) {
            answer.append(material.getName()).append(" Buy: ").append(material.getBuyPrice()).append(" Sell: ").append(material.getSellPrice()).append("\n");
        }
        for (Food food : ItemsController.getAllFoods()) {
            answer.append(food.getName()).append(" Buy: ").append(food.getBuyPrice()).append(" Sell: ").append(food.getSellPrice()).append("\n");
        }
        return answer.toString().trim();
    }

    public static ShopMenuMessages buyItem(String itemName, int amount) {
        Object item;
        if ((item = ItemsController.findItemWithName(itemName)) == null)
            return ShopMenuMessages.NOT_IN_MARKET;
        if (amount < 0)
            return ShopMenuMessages.INVALID_AMOUNT;

        buyOperation(item,amount);
        return ShopMenuMessages.DONE_SUCCESSFULLY;
    }

    public static ShopMenuMessages sellItem(String itemName, int amount) {
        Object item;
        if ((item = ItemsController.findItemWithName(itemName)) == null)
            return ShopMenuMessages.NOT_IN_MARKET;
        if (amount < 0)
            return ShopMenuMessages.INVALID_AMOUNT;

        sellOperation(item,amount);
        return ShopMenuMessages.DONE_SUCCESSFULLY;
    }

    private static void sellOperation(Object item, int amount) {
        if (item instanceof Material)
            sellMaterialOperation((Material) item,amount);
        if (item instanceof Food)
            sellFoodOperation((Food) item,amount);
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

    private static void buyOperation(Object item, int amount) {
        if (item instanceof Material)
            buyMaterialOperation((Material) item,amount);
        if (item instanceof Food)
            buyFoodOperation((Food) item,amount);
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
