package view.enums;

public enum GameMenuMessages {
    INVALID_INPUT_FORMAT("invalid input format!"),
    INVALID_PLACE("invalid coordinates"),
    INVALID_RATE("invalid rate"),
    INVALID_BUILDING_NAME("invalid building name"),
    INVALID_UNIT_NAME("invalid unit name"),
    FULL_CELL("Can't drop here! the cell is full or texture is inappropriate."),
    NOT_ENOUGH_MATERIALS("You don't have enough materials for this building!"),
    NOT_ENOUGH_EQUIPMENTS("You don't have enough value of required martial equipments or gold to build this unit"),
    NOT_ENOUGH_PEOPLE("You don't have enough unemployed people"),
    SAME_CELL("Cell's texture is already this!"),
    SAME_BLOCK("Block's texture is already this!"),
    NO_BUILDINGS("There is no buildings in this cell!"),
    NO_UNITS("There is no units in this cell!"),
    NOT_YOURS("This item is not yours!"),
    INVALID_TEXTURE("Invalid texture!"),
    INVALID_DIRECTION("Invalid direction!"),
    INVALID_NUMBER("the number is invalid!"),
    CANT_PUT_THERE("Unable to put there!"),
    INVALID_TREE_NAME("Invalid tree name!"),
    DONE_SUCCESSFULLY("Operation is done successfully!"),
    OUT_OF_BOUNDS("Out of bounds!");

    private final String message;

    GameMenuMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
