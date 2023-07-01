package server;


import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Database.init();
            ServerUserController.loadUsersFromDatabase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new Master(8080);
    }
}