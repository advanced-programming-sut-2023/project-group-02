package server.logic;

import models.User;

import java.util.ArrayList;
import java.util.HashMap;

public class Lobby {
    private static ArrayList<Lobby> lobbies = new ArrayList<>();

    private ArrayList<User> members = new ArrayList<>();
    private final int mapWidth;
    private final int mapHeight;
    private final int numberOfTurns;
    private int numberOfPlayers;
    private boolean isPublic;
    private String ID;

    private Lobby(User creator, int mapWidth, int mapHeight, int numberOfTurns, int numberOfPlayers, boolean isPublic) {
        this.members.add(creator);
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.numberOfTurns = numberOfTurns;
        this.numberOfPlayers = numberOfPlayers;
        this.isPublic = isPublic;
        this.ID = generateID();
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public String getID() {
        return ID;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public static ArrayList<Lobby> getLobbies() {
        return lobbies;
    }

    public static Lobby getLobbyWithID(String ID) {
        for (Lobby lobby : getLobbies()) {
            if (lobby.ID.equals(ID))
                return lobby;
        }
        return null;
    }

    public static boolean isIDFree(String ID) {
        for (Lobby lobby : getLobbies()) {
            if (lobby.ID.equals(ID))
                return false;
        }
        return true;
    }

    public static String generateID() {
        String ID = "";
        for (int i = 0; i < 4; i++) {
            ID += (char) (Math.random() * 26 + 'A');
        }
        if (isIDFree(ID))
            return ID;
        else
            return generateID();
    }

    public static Lobby makeLobby(User creator, int mapWidth, int mapHeight, int numberOfTurns, int numberOfPlayers, boolean isPublic) {
        Lobby lobby = new Lobby(creator, mapWidth, mapHeight, numberOfTurns, numberOfPlayers, isPublic);
        lobbies.add(lobby);
        return lobby;
    }
}
