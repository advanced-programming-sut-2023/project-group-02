package client.view.enums;

public enum SignUpMenuMessages {
    EMPTY_USERNAME("Username can't be empty!"),
    EMPTY_PASSWORD("Password can't be empty!"),
    EMPTY_NICKNAME("Nickname can't be empty!"),
    EMPTY_EMAIL("Email can't be empty!"),
    INVALID_USERNAME("Username is invalid!"),
    USERNAME_EXISTS("Username already exists!"),
    WEAK_PASSWORD("Password is weak!"),
    PASSWORD_CONFIRMATION_WRONG("Passwords don't match!"),
    PASSWORD_CONFIRMATION_NEEDED("Password confirmation needed!"),
    INVALID_EMAIL("Email is invalid!"),
    EMAIL_EXISTS("Email already exists!"),
    SIGN_UP_SUCCESSFUL("Sign up successful!");

    private final String message;

    SignUpMenuMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
