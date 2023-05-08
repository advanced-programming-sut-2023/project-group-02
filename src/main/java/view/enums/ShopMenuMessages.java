package view.enums;

public enum ShopMenuMessages {
    INVALID_NAME("This name is invalid!"),
    NOT_TO_SELL("This item is not for sale!"),
    NOT_IN_MARKET("We don't have this item in our market!"),
    INVALID_AMOUNT("Please import a valid amount"),
    NOT_ENOUGH_MATERIAL("You don't have enough material to sell!"),
    DONE_SUCCESSFULLY("Operation is done!");

    private String message;

    private ShopMenuMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
