package controllers;

import models.Government;
import models.Material;
import models.Trade;
import models.User;
import utils.Utils;
import view.enums.TradeMenuMessages;

import java.util.ArrayList;

public class TradeMenuController {
    private static ArrayList<Trade> allTrades = new ArrayList<>();

    private static Trade findTradeById(int id) {
        for (Trade trade : allTrades) {
            if (trade.getId() == id)
                return trade;
        }
        return null;
    }

    public static TradeMenuMessages tradeRequest(User receptionist ,Object item, int amount, String priceStr, String message) {
        if (!Utils.isInteger(priceStr)) return TradeMenuMessages.INVALID_PRICE;
        int price = Integer.parseInt(priceStr);

        if (item == null)
            return TradeMenuMessages.INVALID_RESOURCE;
        if (amount < 0)
            return TradeMenuMessages.INVALID_AMOUNT;
        if (price < 0)
            return TradeMenuMessages.INVALID_PRICE;
        if (price > GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getItemAmount(Material.GOLD))
            return TradeMenuMessages.NOT_ENOUGH_MONEY;
        if (receptionist == null)
            return TradeMenuMessages.NULL_RECEPTIONIST;

        Government requesterGovernment = GameMenuController.getCurrentGame().getCurrentPlayersGovernment();
        Government receptionistGovernment = GameMenuController.getCurrentGame().getPlayersGovernment(receptionist);
        Trade newTrade = new Trade(requesterGovernment,receptionistGovernment,item,amount,price,message);
        allTrades.add(newTrade);
        requesterGovernment.getAllSentTrades().add(newTrade);
        receptionistGovernment.getInboxOfTrades().add(newTrade);

        return TradeMenuMessages.REQUEST_IS_MADE;
    }

    public static TradeMenuMessages acceptTrade(Trade trade, String message) {
        if (GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getItemAmount(trade.getResourceType()) < trade.getAmount())
            return TradeMenuMessages.NOT_ENOUGH_MATERIAL;

        trade.setDeciderMessage(message);
        tradeDone(trade);
        return TradeMenuMessages.ACCEPTED;
    }

    static void tradeDone(Trade trade) {
        trade.getRequester().increaseItem(trade.getResourceType(), trade.getAmount());
        trade.getRequester().reduceItem(Material.GOLD, trade.getPrice());
        trade.getReceptionist().reduceItem(trade.getResourceType(),trade.getAmount());
        trade.getReceptionist().increaseItem(Material.GOLD, trade.getPrice());
    }
}
