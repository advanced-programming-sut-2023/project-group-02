package client.view.enums;

public enum ShopMenuMessages {
    INVALID_NAME("This name is invalid!"),
    NOT_IN_MARKET("We don't have this item in our market!"),
    INVALID_AMOUNT("Please import a valid amount"),
    NOT_ENOUGH_MATERIAL("You don't have enough material to sell!"),
    NOT_ENOUGH_GOLD("You don't have enough gold to buy this!"),
    DONE_SUCCESSFULLY("Operation is done!");

    private final String message;

    private ShopMenuMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
