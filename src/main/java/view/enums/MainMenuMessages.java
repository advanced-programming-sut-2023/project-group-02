package view.enums;

public enum MainMenuMessages {
    ALREADY_LOGGED_OUT("You aren't in your account right now!"),
    LOGOUT_SUCCESSFUL("You logged out successfully!");

    private final String message;

    MainMenuMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
