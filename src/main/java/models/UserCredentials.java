package models;

public record UserCredentials(int id, String password) {
    public static UserCredentials of(User user) {
        return new UserCredentials(user.id, user.getPasswordHash());
    }
}
