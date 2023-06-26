package models;

public class Trade {
    public static int nowId = 1;
    private final int id;
    private final Object resourceType;
    private final int amount;
    private final int price;
    private final String requesterMessage;
    private String acceptorMessage;
    private final User requester;
    private User receptionist;
    private boolean isSeen = false;

    public Trade(User requester, User receptionist, Object resourceType, int amount, int price, String requesterMessage) {
        this.requester = requester;
        this.receptionist = receptionist;
        this.resourceType = resourceType;
        this.amount = amount;
        this.price = price;
        this.requesterMessage = requesterMessage;
        this.id = nowId;
        nowId++;
    }

    public User getRequester() {
        return requester;
    }

    public User getReceptionist() {
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

    public boolean isAccepted() {
        return receptionist != null;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    @Override
    public String toString() {
        String answer = id + ". Requester: " + requester.getUsername();
        if (isAccepted()) answer += " - Acceptor: " + receptionist.getUsername();
        if (resourceType instanceof Material) answer += "\nResourceType: " + ((Material) resourceType).getName();
        else if (resourceType instanceof Food) answer += "\nResourceType: " + ((Food) resourceType).getName();
        else if (resourceType instanceof MartialEquipment) answer += "\nResourceType: " + ((MartialEquipment) resourceType).getName();
        answer += " Amount: " + amount + " Price: " + price +
        "\nRequester Message: " + requesterMessage;
        if (receptionist != null)
            answer += "\nAcceptor Message: " + acceptorMessage;

        return answer;
    }
}
