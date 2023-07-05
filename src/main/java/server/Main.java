package server;

import controllers.UserController;

public class Main {
    public static void main(String[] args) {
        try {
            Database.init();
            SqliteDatabase.init();
            ServerUserController.loadUsersFromDatabase();
            UserController.loadUsersFromFile();
            ChatDatabase.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        new Master(8080);
    }
}
