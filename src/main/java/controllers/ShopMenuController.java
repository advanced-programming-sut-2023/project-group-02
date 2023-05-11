package controllers;

import models.Food;
import models.MartialEquipment;
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
        for (MartialEquipment materialEquipment : ItemsController.getAllMaterialEquipments()) {
            answer.append(materialEquipment.getName()).append(" Buy: ").append(materialEquipment.getBuyPrice()).append(" Sell: ").append(materialEquipment.getSellPrice()).append("\n");
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

    public static void sellOperation(Object item, int amount) {
        int price = 0;
        if (item instanceof Food) price = ((Food) item).getSellPrice();
        else if (item instanceof Material) price = ((Material) item).getSellPrice();
        else if (item instanceof MartialEquipment) price = ((MartialEquipment) item).getSellPrice();
        GameMenuController.getCurrentGame().getCurrentPlayersGovernment().increaseItem(Material.GOLD,price);
        GameMenuController.getCurrentGame().getCurrentPlayersGovernment().reduceItem(item,amount);
    }

    private static void buyOperation(Object item, int amount) {
        int price = 0;
        if (item instanceof Food) price = ((Food) item).getBuyPrice();
        else if (item instanceof Material) price = ((Material) item).getBuyPrice();
        else if (item instanceof MartialEquipment) price = ((MartialEquipment) item).getBuyPrice();
        GameMenuController.getCurrentGame().getCurrentPlayersGovernment().increaseItem(Material.GOLD,price);
        GameMenuController.getCurrentGame().getCurrentPlayersGovernment().reduceItem(item,amount);
    }
}
