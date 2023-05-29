package models;

import java.util.ArrayList;

public enum SecurityQuestion {
    FATHERS_NAME("What's your father's name?"),
    PET_NAME("What was your first pet's name?"),
    MOTHERS_LAST_NAME("What's your mother's last name?");

    public final String fullSentence;

    SecurityQuestion(String fullSentence) {
        this.fullSentence = fullSentence;
    }

    public static ArrayList<String> getSecurityQuestions() {
        ArrayList<String> questions = new ArrayList<>();
        for (SecurityQuestion securityQuestion : SecurityQuestion.values()) {
            questions.add(securityQuestion.fullSentence);
        }
        return questions;
    }

    public static SecurityQuestion getSecurityQuestion(String question) {
        for (SecurityQuestion securityQuestion : SecurityQuestion.values()) {
            if (securityQuestion.fullSentence.equals(question)) {
                return securityQuestion;
            }
        }
        return null;
    }
}
