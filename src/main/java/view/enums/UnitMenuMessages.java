package view.enums;

public enum UnitMenuMessages {
    WRONG_UNIT("Wrong unit type"),
    NOT_ENGINEER("You need to select engineers to build equipments!"),
    CANT_GO_THERE("Can't go there"),
    NO_UNIT_THERE("No unit is here"),
    ALREADY_DONE("Already done"),
    ALREADY_MOVED("this unit has already moved this turn!"),
    INVALID_DIRECTION("Invalid direction"),
    INVALID_PLACE("Invalid place"),
    INVALID_EQUIPMENT("Invalid equipment"),
    NOT_ENOUGH_MATERIAL("You don't have enough materials"),
    CANT_DIG("You can't dig this cell"),
    DONE_SUCCESSFULLY("Done");

    private final String message;

    private UnitMenuMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
