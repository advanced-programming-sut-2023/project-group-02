package models;

public record UserCredentials(String username, String password) {
    public static UserCredentials of(User user) {
        return new UserCredentials(user.getUsername(), user.getPasswordHash());
    }
}
