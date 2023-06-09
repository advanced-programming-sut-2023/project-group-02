package utils;

import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {
    public static int keepNumbersLimited(int number, int min, int max) {
        if (number < min)
            return min;
        if (number > max)
            return max;
        return number;
    }

    public static String encryptUsingSHA256(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    public static boolean areIntegers(String[] strings) {
        for (String string : strings) {
            if (!isInteger(string))
                return false;
        }
        return true;
    }

    public static int getValidInt(Scanner scanner) {
        String input;
        while (true) {
            input = scanner.nextLine();
            if (isInteger(input) && Integer.parseInt(input) >= 0)
                return Integer.parseInt(input);
            System.out.println("Your input isn't a positive number! Please try again: ");
        }
    }

    public static String toCamelCase(String string) {
        String[] words = string.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            String firstLetter = word.substring(0, 1);
            result.append(i == 0 ? firstLetter.toLowerCase() : firstLetter.toUpperCase());
            result.append(word.substring(1).toLowerCase());
        }
        return result.toString();
    }

    public static String sevenCharacterise(String string) {
        if (string.length() < 7) {
            for (int i = 0; i < 7 - string.length(); i++) {
                string += " ";
            }
            return string;
        } else {
            return string.substring(0, 7);
        }
    }
}
