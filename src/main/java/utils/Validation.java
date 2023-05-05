package utils;

import controllers.GameMenuController;

import java.util.ArrayList;

public class Validation {
    private static final int MIN_PASSWORD_LENGTH = 6;

    public static boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9_.]+@[a-zA-Z0-9_.]+\\.[a-zA-Z0-9_.]+$");
    }

    public static boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z0-9_]+$");
    }

    public static ArrayList<PasswordProblem> validatePassword(String password) {
        ArrayList<PasswordProblem> problems = new ArrayList<PasswordProblem>();

        if (password.length() < MIN_PASSWORD_LENGTH) {
            problems.add(PasswordProblem.TOO_SHORT);
        }

        boolean hasLowercase = false;
        boolean hasUppercase = false;
        boolean hasDigit = false;
        boolean hasSpecialCharacter = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                hasSpecialCharacter = true;
            }
        }

        if (!hasLowercase) {
            problems.add(PasswordProblem.NO_LOWERCASE);
        }

        if (!hasUppercase) {
            problems.add(PasswordProblem.NO_UPPERCASE);
        }

        if (!hasDigit) {
            problems.add(PasswordProblem.NO_DIGIT);
        }

        if (!hasSpecialCharacter) {
            problems.add(PasswordProblem.NO_SPECIAL_CHARACTER);
        }

        return problems;
    }

    public static boolean areCoordinatesValid(int x, int y) {
        if (x < 0 || y < 0 || x > GameMenuController.getCurrentGame().getMap().getWidth() || y > GameMenuController.getCurrentGame().getMap().getHeight())
            return false;
        return true;
    }

    public static boolean areCoordinatesValid(int x1, int y1, int x2, int y2) {
        if (!areCoordinatesValid(x1, y1) || !areCoordinatesValid(x2, y2))
            return false;
        if (x2 < x1 || y2 < y1) return false;
        return true;
    }
}
