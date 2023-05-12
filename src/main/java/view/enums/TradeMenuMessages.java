package view.enums;

public enum TradeMenuMessages {
    INVALID_RESOURCE("Please enter name of a valid resource!"),
    NOT_ENOUGH_MONEY("You don't have enough money!"),
    NOT_ENOUGH_MATERIAL("You don't have enough material!"),
    ALREADY_ACCEPTED("This trade is already accepted!"),
    ID_DOESNT_EXIST("This id doesn't exist!"),
    INVALID_PRICE("Please import a valid price!"),
    INVALID_AMOUNT("Please import a valid amount!"),
    REQUEST_IS_MADE("Your request is made!"),
    ACCEPTED("You accepted this trade!"),
    CANT_ACCEPT_YOUR_TRADE("You can't accept your own trades!");

    private final String message;

    TradeMenuMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
