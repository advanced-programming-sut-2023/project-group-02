package models;

import controllers.UserController;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    private final ArrayList<User> players;
    private HashMap<User, Colors> playersColors;
    private HashMap<User, Government> playersGovernments;
    private User currentPlayer;
    private int turnCounter;
    private final int numberOfTurns;
    private final Map map;
    private int numberOfPlayers;

    public Game(ArrayList<User> players, int numberOfTurns, Map map) {
        this.currentPlayer = UserController.getCurrentUser();
        this.players = players;
        this.numberOfTurns = numberOfTurns;
        this.map = map;
    }

    public void givePlayersColors(ArrayList<User> players) {

    }

    public void givePlayersGovernments(ArrayList<User> players) {

    }

    public Government getCurrentPlayersGovernment() {
        return playersGovernments.get(currentPlayer);
    }

    public Government getPlayersGovernment(User player) {
        return playersGovernments.get(player);
    }

    public void addObject(MapObject object, int x, int y) {
        map.addObject(object, x, y);
        object.setOwner(currentPlayer);
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public User getCurrentPlayer() {
        return currentPlayer;
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<User> getPlayers() {
        return players;
    }

    public User getPlayerByUsername(String username) {
        return null;
    }

    public Colors getPlayersColor(String username) {
        return null;
    }

    public User getPlayerWithColor(Colors color) {
        return null;
    }

    public User nextPlayer(User currentPlayer) {
        return null;
    }

    public void nextTurn() {

    }

    public boolean isGameOver() {
        return false;
    }
}
