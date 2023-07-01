package models;

import controllers.GameMenuController;
import controllers.UserController;
import models.units.Unit;
import models.units.UnitState;
import models.units.UnitType;
import utils.PathFinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import client.view.GameMenu;
import client.view.MainMenu;

public class Game {
    private final ArrayList<Government> governments;
    private int turnCounter = 0;
    private final int numberOfTurns;
    private final Map map;

    private HashMap<Unit, Coordinates> startingPoints = new HashMap<>();
    private HashMap<Unit, Coordinates> destinations = new HashMap<>();

    private static final int MONTH_TO_TURN = 3;

    public Game(ArrayList<Government> governments, int numberOfTurns, Map map) {
        this.governments = governments;
        this.numberOfTurns = numberOfTurns;
        this.map = map;
        GameMenuController.getAllMadeCellWrappers().put(this, new ArrayList<>());
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

    public void addObject(MapObject object, int x, int y, User owner) {
        if (owner != null)
            addObject(object, owner, x, y);
        else
            addObject(object, UserController.getCurrentUser(), x, y);
    }

    public void addObject(MapObject object, User owner, int x, int y) {
        map.addObject(object, x, y);
        if (!(object instanceof Tree) && !(object instanceof Rock))
            object.setOwner(owner);
    }

    public void addUnit(Unit unit, User player, int x, int y) {
        map.addUnit(unit, x, y);
        unit.setOwner(player);
    }

    public void addUnit(Unit unit, int x, int y) {
        addUnit(unit, getCurrentPlayer(), x, y);
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

    public void nextTurn(GameMenu gameMenu) {
        moveAllUnits();
        doAllTheFights();
        deleteDeadTroops();
        resetUnitsMoves();
        do {
            turnCounter++;
        } while (!getCurrentPlayersGovernment().isLordAlive());


        if (turnCounter % MONTH_TO_TURN == 0) {
            nextMonth();
        }

        if (isGameOver())
            gameMenu.endGame(findWinner());
        else
            gameMenu.printNowPlaying();
    }

    private void deleteDeadTroops() {
        for (Unit unit : map.getAllUnits()) {
            if (unit.getHitpoint() <= 0) {
                map.removeUnit(unit, unit.getCurrentX(), unit.getCurrentY());
                getPlayersGovernment(unit.getOwner()).removeTroop();
                if (unit.getType() == UnitType.LORD)
                    getCurrentPlayersGovernment().lordDied();
            }
        }
    }

    private void resetUnitsMoves() {
        for (Unit unit : map.getAllUnits()) {
            unit.setHasMoved(false);
        }
    }

    private void doAllTheFights() {
        for (Unit unit : map.getAllUnits()) {
            if (unit.engageWithEnemies(map))
                continue;
            if (destinations.containsKey(unit))
                continue;
            if (unit.getState() == UnitState.DEFENSIVE) {
                unit.attackCloseEnemies(map);
                continue;
            }
            if (unit.getState() == UnitState.OFFENSIVE) {
                unit.attackFarEnemies(map);
                continue;
            }
            unit.patrol();
        }
    }

    public void scheduleMovement(Unit unit, int startX, int startY, int destinationX, int destinationY) {
        startingPoints.put(unit, new Coordinates(startX, startY));
        destinations.put(unit, new Coordinates(destinationX, destinationY));
    }

    private void moveAllUnits() {
        HashMap<Unit, Coordinates> newDestinations = new HashMap<>();
        HashMap<Unit, Coordinates> newStartingPoints = new HashMap<>();
        if (destinations == null)
            return;
        for (Unit movingUnit : destinations.keySet()) {
            Coordinates destination = destinations.get(movingUnit);
            Coordinates start = startingPoints.get(movingUnit);
            LinkedList<Coordinates> path = PathFinder.getPath(map, start.x, start.y, destination.x, destination.y);
            Coordinates finalPointForThisTurn = path.get(Math.min(path.size() - 1, movingUnit.getMaxDistance()));
            map.moveUnit(movingUnit, finalPointForThisTurn.x, finalPointForThisTurn.y);
            if (!finalPointForThisTurn.equals(path.getLast())) {
                newStartingPoints.put(movingUnit, finalPointForThisTurn);
                newDestinations.put(movingUnit, destination);
            }
        }
        this.destinations = newDestinations;
        this.startingPoints = newStartingPoints;
    }

    private void initiateSickness() {
        Random random = new Random();
        int regionWidth = (int) Math.floor(0.5 * map.getWidth() * Math.pow(random.nextDouble(), 2));
        int regionHeight = (int) Math.floor(0.5 * map.getHeight() * Math.pow(random.nextDouble(), 2));
        int regionX = random.nextInt(map.getWidth() - regionWidth);
        int regionY = random.nextInt(map.getHeight() - regionHeight);
        for (int x = regionX; x < regionX + regionWidth; x++) {
            for (int y = regionY; y < regionY + regionHeight; y++) {
                map.findCellWithXAndY(x, y).setHasSickness(true);
            }
        }
    }

    public void nextMonth() {
        initiateSickness();
        for (Government government : governments) {
            government.updatePopularity();
            government.collectTax();
            government.doTheProductions();
            government.givePeopleFood();
        }
    }

    public boolean isGameOver() {
        if (turnCounter > numberOfTurns)
            return true;
        int governmentsInGame = 0;
        for (Government government : governments) {
            if (government.isLordAlive())
                governmentsInGame++;
        }
        if (governmentsInGame <= 1)
            return true;
        return false;
    }

    public User findWinner() {
        ArrayList<Government> governmentsInGame = new ArrayList<>();
        for (Government government : governments) {
            if (government.isLordAlive())
                governmentsInGame.add(government);
        }
        if (governmentsInGame.size() == 0)
            return null;
        if (governmentsInGame.size() == 1)
            return governmentsInGame.get(0).getUser();
        Government winner = governmentsInGame.get(0);
        for (Government government : governmentsInGame) {
            if (government.getItemAmount(Material.GOLD) > winner.getItemAmount(Material.GOLD))
                winner = government;
        }
        return winner.getUser();
    }

    public HashMap<Unit, Coordinates> getDestinations() {
        return destinations;
    }
}
