import view.SignupMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        new SignupMenu().run(scanner);
    }
}
