package models;

public class Trade {
    public static int nowId = 1;
    private final int id;
    private final Material resourceType;
    private final int amount;
    private final int price;
    private final String requesterMessage;
    private String acceptorMessage;
    private final User requester;
    private User acceptor;

    public Trade(User requester, Material resourceType, int amount, int price, String requesterMessage) {
        this.requester = requester;
        this.resourceType = resourceType;
        this.amount = amount;
        this.price = price;
        this.requesterMessage = requesterMessage;
        this.id = nowId;
        nowId++;
    }

    public void setAcceptor(User acceptor) {
        this.acceptor = acceptor;
    }

    public User getRequester() {
        return requester;
    }

    public User getAcceptor() {
        return acceptor;
    }

    public Material getResourceType() {
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

    public boolean isAccepted() {
        return acceptor != null;
    }

    @Override
    public String toString() {
        String answer = id + ". Requester: " + requester.getUsername();
        if (isAccepted()) answer += " Acceptor: " + acceptor.getUsername();
        answer += "\nResourceType: " + resourceType.getName() +
        " Amount: " + amount + " Price: " + price +
        "\nRequester Message: " + requesterMessage +
            "\nAcceptor Message: " + acceptorMessage;

        return answer;
    }
}
