import view.MainMenu;
import view.ProfileMenu;
import view.SignupMenu;

import java.io.IOException;
import java.util.Scanner;

import controllers.UserController;
import controllers.database.Database;

public class Main {
    public static void main(String[] args) {
        try {
            Database.init();
        } catch (IOException e) {
        }

        UserController.loadUsersFromFile();
        UserController.loadCurrentUserFromFile();

        Scanner scanner = new Scanner(System.in);
        if (UserController.isAuthorized()) {
            new MainMenu().run(scanner);
            new SignupMenu().run(scanner);
        } else {
            new SignupMenu().run(scanner);
        }
        scanner.close();
    }
}

/*
 * samples
 * user create -u danial -p Danial01* Danial01* --email danielparnian@gmail.com -n Dani -s random
 * question pick -q 2 -a jack -c jack
 */
