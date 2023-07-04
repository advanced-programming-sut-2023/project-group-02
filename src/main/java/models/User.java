package models;

import controllers.UserController;
import javafx.scene.image.ImageView;
import utils.Graphics;
import utils.Utils;

import java.util.Date;

public class User {
    public final int id;
    private String username, passwordHash, nickname, email, slogan;
    private SecurityQuestion securityQuestion;
    private String securityAnswer;
    private int highScore;
    private String avatarPath;
    private boolean isOnline = false;
    private Date lastSeen;
    private final User[] friends = new User[100];

    public User(int id, String username, String password, String nickname, String email, String slogan,
                SecurityQuestion securityQuestion, String securityAnswer) {
        this.id = id;
        this.username = username;
        this.passwordHash = encrypt(password);
        this.nickname = nickname;
        this.email = email;
        this.slogan = slogan;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.setAvatarPath("/images/avatars/0.jpg");
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

    @Override
    public String toString() {
        return "Username: " + username + "\n" +
            "Password: " + passwordHash + "\n" +
            "Nickname: " + nickname + "\n" +
            "Email: " + email + "\n" +
            "Slogan: " + slogan + "\n" +
            "HighScore: " + highScore + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User otherUser = (User) obj;
            return this.id == otherUser.id;
        }
        return false;
    }

    private static String encrypt(String password) {
        return Utils.encryptUsingSHA256(password);
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public ImageView getAvatar() {
        return Graphics.getAvatarWithPath(avatarPath);
    }

    public int getRank() {
        return UserController.getUsersSorted().indexOf(this) + 1;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }

    public User[] getFriends() {
        return friends;
    }

    public void addFriend(User friend) {
        for (int i = 0; i < friends.length; i++) {
            if (friends[i] == null) {
                friends[i] = friend;
                break;
            }
        }
    }
}
