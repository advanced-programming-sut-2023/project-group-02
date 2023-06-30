package client.view.enums;

public enum BuildingMenuMessages {
    INVALID_TYPE("Invalid type!"),
    INVALID_COUNT("Invalid count!"),
    NOT_ENOUGH_MATERIAL("You don't have enough material!"),
    NOT_ENOUGH_EQUIPMENT("You don't have enough equipment!"),
    NOT_ENOUGH_PEOPLE("You don't have enough people!"),
    DONE_SUCCESSFULLY("Operation is done successfully!"),
    HP_IS_FULL("Hitpoint is already full!"),
    BUILDING_IS_BEING_USED("This building is being used!");

    private final String message;

    BuildingMenuMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
