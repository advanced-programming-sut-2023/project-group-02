package models;

import models.units.Unit;

import java.util.ArrayList;

public class Game {
    private final ArrayList<Government> governments;
    private int turnCounter = 0;
    private final int numberOfTurns;
    private final Map map;

    public Game(ArrayList<Government> governments, int numberOfTurns, Map map) {
        this.governments = governments;
        this.numberOfTurns = numberOfTurns;
        this.map = map;
    }

    public void addGovernment(Government government) {
        governments.add(government);
        government.setMap(map);
    }

    public Government getPlayersGovernment(User player) {
        for (Government government : governments) {
            if (government.getUser().equals(player)) {
                return government;
            }
        }
        return null;
    }

    public Government getCurrentPlayersGovernment() {
        return getPlayersGovernment(getCurrentPlayer());
    }

    public void addObject(MapObject object, int x, int y) {
        map.addObject(object, x, y);
        if (!(object instanceof Tree) && !(object instanceof Rock))
            object.setOwner(getCurrentPlayer());
    }

    public void addUnit(Unit unit, int x, int y) {
        map.addUnit(unit, x, y);
        unit.setOwner(getCurrentPlayer());
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public User getCurrentPlayer() {
        if (governments.size() == 0)
            return null;
        return governments.get(turnCounter % governments.size()).getUser();
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<Government> getGovernments() {
        return governments;
    }

    public ArrayList<User> getPlayers() {
        return governments.stream().map(Government::getUser).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public User getPlayerByUsername(String username) {
        for (Government government : governments) {
            User player = government.getUser();
            if (player.getUsername().equals(username))
                return player;
        }
        return null;
    }

    public Colors getPlayersColor(String username) {
        return null;
    }

    public User getPlayerWithColor(Colors color) {
        return null;
    }

    public void nextTurn() {
        turnCounter++;
        for (Government government : governments) {
            government.updatePopularity();
        }
    }

    public boolean isGameOver() {
        return turnCounter >= numberOfTurns;
    }
}
