package utils;

public class TextCaptcha {
    private final String answer;

    public TextCaptcha(String answer) {
        this.answer = answer;
    }

    public boolean checkAnswer(String answer) {
        return this.answer.equals(answer);
    }

    public String getImage() {
        // TODO
        return "";
    }
}
