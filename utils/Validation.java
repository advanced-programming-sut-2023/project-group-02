package utils;

public class Validation {
  private static final int MIN_PASSWORD_LENGTH = 6;

  public static boolean isValidEmail(String email) {
    return email.matches("^[a-zA-Z0-9_.]+@[a-zA-Z0-9_.]+\\.[a-zA-Z0-9_.]+$");
  }

  public static boolean isValidUsername(String username) {
    return username.matches("^[a-zA-Z0-9_]+$");
  }

  public static boolean isStrongPassword(String password) {
    return password.length() > MIN_PASSWORD_LENGTH && password.matches(".*[a-z].*") && password.matches(".*[A-Z].*")
        && password.matches(".*[0-9].*") && password.matches(".*[^a-zA-Z0-9].*");
  }
}
