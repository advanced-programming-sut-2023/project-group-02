package models;

public class User {
  private String username, password, nickname, email, slogan;
  private SecurityQuestion securityQuestion;
  private String securityAnswer;

  private int highScore;

  public User(String username, String password, String nickname, String email, String slogan,
      SecurityQuestion securityQuestion, String securityAnswer) {
    this.username = username;
    this.password = password;
    this.nickname = nickname;
    this.email = email;
    this.slogan = slogan;
    this.securityQuestion = securityQuestion;
    this.securityAnswer = securityAnswer;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getNickname() {
    return nickname;
  }

  public String getEmail() {
    return email;
  }

  public String getSlogan() {
    return slogan;
  }

  public SecurityQuestion getSecurityQuestion() {
    return securityQuestion;
  }

  public String getSecurityAnswer() {
    return securityAnswer;
  }

  public int getHighScore() {
    return highScore;
  }

  public void setHighScore(int highScore) {
    this.highScore = highScore;
  }

  public void setSecurityQuestion(SecurityQuestion securityQuestion) {
    this.securityQuestion = securityQuestion;
  }

  public void setSecurityAnswer(String securityAnswer) {
    this.securityAnswer = securityAnswer;
  }

  public void setSlogan(String slogan) {
    this.slogan = slogan;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String toString() {
    return "Username: " + username + "\n" +
            "Password: " + password + "\n" +
            "Nickname: " + nickname + "\n" +
            "Email: " + email + "\n" +
            "Slogan: " + slogan + "\n" +
            "HighScore: " + highScore + "\n";
  }
}