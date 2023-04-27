package view.enums;

public enum LoginMenuMessages {
    UNMATCHED_USERNAME_PASSWORD("Password is incorrect!"),
    USERNAME_DOESNT_EXIST("No user with this username exists!"),
    EMPTY_FIELD("Please fill all fields!"),
    LOGIN_SUCCESSFUL("You Logged in successfully!"),
    WRONG_SECURITY_ANSWER("Your answer is wrong!"),
    ENTER_NEW_PASSWORD("Please enter a new password!"),
    NEW_PASSWORD_WEAK("Your new password is weak!"),
    PASSWORD_IS_CHANGED("Your password is changed successfully!"),
    TOO_MANY_REQUESTS("Too many requests! Please try again a few seconds later.");

    private final String message;

    LoginMenuMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
