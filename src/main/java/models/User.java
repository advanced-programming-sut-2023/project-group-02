package models;

import java.util.ArrayList;

import utils.Utils;

public class User {
    private String username, passwordHash, nickname, email, slogan;
    private SecurityQuestion securityQuestion;
    private String securityAnswer;
    private int highScore;
    private ArrayList<Trade> usersAllTrades = new ArrayList<>();
    private ArrayList<Trade> usersNewTrades = new ArrayList<>();

    public User(String username, String password, String nickname, String email, String slogan,
            SecurityQuestion securityQuestion, String securityAnswer) {
        this.username = username;
        this.passwordHash = encrypt(password);
        this.nickname = nickname;
        this.email = email;
        this.slogan = slogan;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
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
        this.passwordHash = encrypt(password);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean passwordEquals(String password) {
        return encrypt(password).equals(this.passwordHash);
    }

    public ArrayList<Trade> getUsersAllTrades() {
        return usersAllTrades;
    }

    public ArrayList<Trade> getUsersNewTrades() {
        return usersNewTrades;
    }

    @Override
    public String toString() {
        return "Username: " + username + "\n" +
                "Password: " + passwordHash + "\n" +
                "Nickname: " + nickname + "\n" +
                "Email: " + email + "\n" +
                "Slogan: " + slogan + "\n" +
                "HighScore: " + highScore + "\n";
    }

    private static String encrypt(String password) {
        return Utils.encryptUsingSHA256(password);
    }
}
