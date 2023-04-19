package utils;

public class Utils {
    public static int keepNumbersLimited(int number, int min , int max) {
        if (number < min) return min;
        if (number > max) return max;
        return number;
    }
}
