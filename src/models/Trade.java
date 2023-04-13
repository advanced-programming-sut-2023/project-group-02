package models;

public class Trade {
    public static int nowId = 1;
    private int id;
    private final Material recourseType;
    private final int amount;
    private final int price;
    private final String message;
    private User requester;
    private User recipient;

    public Trade(User recipient, Material recourseType, int amount, int price, String message) {
        this.recipient = recipient;
        this.recourseType = recourseType;
        this.amount = amount;
        this.price = price;
        this.message = message;
        this.id = nowId;
        nowId++;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public User getRequester() {
        return requester;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public User getRecipient() {
        return recipient;
    }

    public Material getRecourseType() {
        return recourseType;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return null;
    }
}
