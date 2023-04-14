package models;

import controllers.MainController;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    private final ArrayList<User> players;
    private HashMap<User,Colors> playersColors;
    private User currentPlayer;
    private int turnCounter;
    private final int numberOfTurns;
    private final Map map;
    private int numberOfPlayers;

    public Game(ArrayList<User> players, int numberOfTurns, Map map) {
        this.currentPlayer = MainController.getCurrentUser();
        this.players = players;
        this.numberOfTurns = numberOfTurns;
        this.map = map;
    }

    public void givePlayersColors(ArrayList<User> players) {

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
