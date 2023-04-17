package models;

public enum SecurityQuestion {
  FATHERS_NAME("What's your father's name?"),
  PET_NAME("What was your first pet's name?"),
  MOTHERS_LAST_NAME("What's your mother's last name?");

  public final String fullSentence;

  SecurityQuestion(String fullSentence) {
    this.fullSentence = fullSentence;
  }
}
