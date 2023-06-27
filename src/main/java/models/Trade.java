package models;

public class Trade {
    enum TradeState {
        ACCEPTED,
        REJECTED,
        NOT_SEEN,
        NOT_ANSWERED,
    }
    public static int nowId = 1;
    private final int id;
    private final Object resourceType;
    private final int amount;
    private final int price;
    private final String requesterMessage;
    private String acceptorMessage;
    private final Government requester;
    private final Government receptionist;
    TradeState state = TradeState.NOT_SEEN;

    public Trade(Government requester, Government receptionist, Object resourceType, int amount, int price, String requesterMessage) {
        this.requester = requester;
        this.receptionist = receptionist;
        this.resourceType = resourceType;
        this.amount = amount;
        this.price = price;
        this.requesterMessage = requesterMessage;
        this.id = nowId;
        nowId++;
    }

    public Government getRequester() {
        return requester;
    }

    public Government getReceptionist() {
        return receptionist;
    }

    public Object getResourceType() {
        return resourceType;
    }

    public void setAcceptorMessage(String acceptorMessage) {
        this.acceptorMessage = acceptorMessage;
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

    public TradeState getState() {
        return state;
    }

    public void setState(TradeState state) {
        this.state = state;
    }
}
