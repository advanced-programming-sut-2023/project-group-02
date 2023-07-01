package server;


public class Main {
    public static void main(String[] args) {
        try {
            Database.init();
            SqliteDatabase.init();
            ServerUserController.loadUsersFromDatabase();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        new Master(8080);
    }
}
