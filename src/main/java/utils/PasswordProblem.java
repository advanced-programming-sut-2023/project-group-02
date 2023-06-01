package utils;

import java.util.ArrayList;

public enum PasswordProblem {
    TOO_SHORT("make your password longer than 6 characters"),
    NO_LOWERCASE("add lowercase characters"),
    NO_UPPERCASE("add uppercase characters"),
    NO_DIGIT("add digits"),
    NO_SPECIAL_CHARACTER("add special characters");

    private final String solution;

    private PasswordProblem(String solution) {
        this.solution = solution;
    }

    public String getSolution() {
        return solution;
    }

    public static String showErrors(ArrayList<PasswordProblem> passwordProblems) {
        int arrayListLength = passwordProblems.size();
        String errorsAndSolutions = "You must ";
        for (int i = 0; i < arrayListLength - 2; i++) {
            errorsAndSolutions = errorsAndSolutions + passwordProblems.get(i).solution + ", ";
        }
        if (arrayListLength > 1)
            errorsAndSolutions = errorsAndSolutions + passwordProblems.get(arrayListLength - 2).solution + " and\n";
        errorsAndSolutions = errorsAndSolutions + passwordProblems.get(arrayListLength - 1).solution + " to make your password stronger.";
        return errorsAndSolutions;
    }
}
