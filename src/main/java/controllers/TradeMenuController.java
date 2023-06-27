package controllers;

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

    public static String showAllTrades() {
        if (allTrades.size() == 0) return "There are no trades yet!";
        StringBuilder answer = new StringBuilder();
        for (Trade trade : allTrades) {
            answer.append(trade.toString()).append("\n");
        }
        return answer.toString().trim();
    }

    public static TradeMenuMessages tradeRequest(User receptionist ,Object item, int amount, String priceStr, String message) {
        if (!Utils.isInteger(priceStr)) return TradeMenuMessages.INVALID_PRICE;
        int price = Integer.parseInt(priceStr);

        if (amount < 0)
            return TradeMenuMessages.INVALID_AMOUNT;
        if (price < 0)
            return TradeMenuMessages.INVALID_PRICE;

        if (price > GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getItemAmount(Material.GOLD))
            return TradeMenuMessages.NOT_ENOUGH_MONEY;

        allTrades.add(new Trade(GameMenuController.getCurrentGame().getCurrentPlayer(), receptionist,item,amount,price,message));
        return TradeMenuMessages.REQUEST_IS_MADE;
    }

    public static TradeMenuMessages acceptTrade(int id, String message) {
        Trade trade;
        if ((trade = findTradeById(id)) == null)
            return TradeMenuMessages.ID_DOESNT_EXIST;
        if (GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getItemAmount(trade.getResourceType()) < trade.getAmount())
            return TradeMenuMessages.NOT_ENOUGH_MATERIAL;
        if (trade.isAccepted())
            return TradeMenuMessages.ALREADY_ACCEPTED;
        if (trade.getRequester().equals(GameMenuController.getCurrentGame().getCurrentPlayer()))
            return TradeMenuMessages.CANT_ACCEPT_YOUR_TRADE;

//        trade.setReceptionist(GameMenuController.getCurrentGame().getCurrentPlayer());
        trade.setAcceptorMessage(message);
        tradeDone(trade);
        return TradeMenuMessages.ACCEPTED;
    }

    static void tradeDone(Trade trade) {
        GameMenuController.getCurrentGame().getPlayersGovernment(trade.getRequester()).increaseItem(trade.getResourceType(), trade.getAmount());
        GameMenuController.getCurrentGame().getPlayersGovernment(trade.getRequester()).reduceItem(Material.GOLD, trade.getPrice());
        trade.getRequester().getUsersNewTrades().add(trade);
        GameMenuController.getCurrentGame().getPlayersGovernment(trade.getReceptionist()).reduceItem(trade.getResourceType(),trade.getAmount());
        GameMenuController.getCurrentGame().getPlayersGovernment(trade.getReceptionist()).increaseItem(Material.GOLD, trade.getPrice());
        trade.getReceptionist().getUsersNewTrades().add(trade);
    }

    public static String showTradeHistory() {
        User player;
        StringBuilder answer = new StringBuilder();
        if ((player = GameMenuController.getCurrentGame().getCurrentPlayer()).getUsersNewTrades().size() == 0)
            return "You don't have any new trades!";

        for (Trade trade : player.getUsersNewTrades()) {
            answer.append(trade.toString()).append("\n");
        }
        player.getUsersNewTrades().clear();
        return answer.toString().trim();
    }
}
