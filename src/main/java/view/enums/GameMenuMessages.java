package view.enums;

public enum GameMenuMessages {
    INVALID_PLACE("invalid coordinates"),
    INVALID_RATE("invalid rate"),
    INVALID_BUILDING_NAME("invalid building name"),
    INVALID_UNIT_NAME("invalid unit name"),
    FULL_CELL("This cell is full!"),
    NOT_ENOUGH_MATERIALS("You don't have enough materials for this building!"),
    SAME_CELL("Cell's texture is already this!"),
    SAME_BLOCK("Block's texture is already this!"),
    NO_BUILDINGS("There is no buildings in this cell!"),
    NO_UNITS("There is no units in this cell!"),
    NOT_YOURS("This item is not yours!"),
    INVALID_TEXTURE("Invalid texture!"),
    INVALID_DIRECTION("Invalid direction!"),
    CANT_PUT_THERE("Unable to put there!"),
    INVALID_TREE_NAME("Invalid tree name!"),
    DONE_SUCCESSFULLY("Operation is done successfully!");

    private final String message;

    GameMenuMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
