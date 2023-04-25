package utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Captcha {
    private static String captcha;
    private static final Random random = new Random();
    private static final int width = 200 , height = 50;

    public static String showCaptcha() {
        captcha = makeCaptchaString();
        BufferedImage image = makeCaptchaImage(captcha);
        return makeFinalCaptcha(image);
    }

    private static String makeCaptchaString() {
        int length = random.nextInt(4,8);
        char[] captchaArray = new char[length];
        for (int i = 0; i < length; i++) {
            captchaArray[i] = (char) random.nextInt(48,58);
        }
        return new String(captchaArray);
    }

    public static BufferedImage makeCaptchaImage(String myCaptcha) {
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0,0,width,height);
        graphics2D.setFont(new Font("Arial",Font.BOLD,30));
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(myCaptcha,20,35);
        graphics2D.dispose();
        return addNoiseToCaptcha(image);
    }

    private static BufferedImage addNoiseToCaptcha(BufferedImage image) {
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            image.setRGB(x, y, -16777216);
        }
        return image;
    }

    private static String makeFinalCaptcha(BufferedImage image) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] shapes = {"+","*","*",".",".","#","@","$",":","0"};
        int index = random.nextInt(10);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                stringBuilder.append(image.getRGB(j, i) == -16777216 ? shapes[index] : " ");
            }
            stringBuilder.append("\n");
        }
        return new String(stringBuilder);
    }


    public static boolean inputEqualsCaptcha(String input) {
        return input.equals(captcha);
    }
}
