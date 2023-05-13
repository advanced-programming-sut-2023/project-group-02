package models;

import models.buildings.InventoryBuilding;
import models.units.Unit;
import utils.Utils;
import utils.Validation;

import java.util.ArrayList;

public class Map {
    private final int width;
    private final int height;
    private final Cell[][] map;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.map = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[i][j] = new Cell(Texture.EARTH);
            }
        }
    }

    public Cell findCellWithXAndY(int x, int y) {
        return map[y][x];
    }

    public ArrayList<Cell> findMoreThanOneCell(int x1, int y1, int x2, int y2) {
        ArrayList<Cell> answer = new ArrayList<>();
        for (int i = y1; i <= y2; i++) {
            for (int j = x1; j <= x2; j++) {
                answer.add(map[i][j]);
            }
        }
        return answer;
    }

    public Building findBuildingWithXAndY(int x, int y) {
        return map[y][x].getBuilding();
    }

    public ArrayList<Unit> findUnitsWithXAndY(int x, int y) {
        return map[y][x].getUnits();
    }

    public Coordinates locateUnit(Unit unit) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell cell = map[i][j];
                if (cell.getUnits().contains(unit)) {
                    return new Coordinates(j, i);
                }
            }
        }
        return null;
    }

    public void moveUnit(Unit unit, int x, int y) {
        Coordinates prev = locateUnit(unit);
        findUnitsWithXAndY(prev.x, prev.y).remove(unit);
        findUnitsWithXAndY(x, y).add(unit);
    }

    public void addObject(MapObject object, int x, int y) {
        findCellWithXAndY(x, y).setObject(object);
        object.setCoordinates(x, y);
    }

    public void addUnit(Unit unit, int x, int y) {
        findCellWithXAndY(x, y).getUnits().add(unit);
        unit.setCoordinates(x, y);
    }

    public ArrayList<Building> getPlayersBuildings(User player) {
        ArrayList<Building> playersBuildings = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (map[i][j].getBuilding() != null && map[i][j].getBuilding().getOwner().equals(player))
                    playersBuildings.add(map[i][j].getBuilding());
            }
        }
        return playersBuildings;
    }

    public ArrayList<Unit> getAllUnits() {
        ArrayList<Unit> allUnits = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                allUnits.addAll(map[i][j].getUnits());
            }
        }
        return allUnits;
    }

    private Cell[][] getMiniMap(int x, int y) {
        int startX = Utils.keepNumbersLimited(y - 5, 0, width);
        int endX = Utils.keepNumbersLimited(y + 5, 0, width);
        int startY = Utils.keepNumbersLimited(x - 5, 0, height);
        int endY = Utils.keepNumbersLimited(x + 5, 0, height);
        Cell[][] miniMap = new Cell[endX - startX + 1][endY - startY + 1];

        for (int i = 0; i <= endX - startX; i++) {
            for (int j = 0; j <= endY - startY; j++) {
                miniMap[i][j] = map[i + startX][j + startY];
            }
        }
        return miniMap;
    }

    private String showMapAlgorithm(Cell[][] miniMap) {
        String answer = "";
        int height = miniMap.length;
        int width = miniMap[0].length;

        for (int i = 0; i <= 4 * height; i++) {
            for (int j = 0; j <= 2 * width; j++) {
                if (j % 2 == 0 && i % 4 != 0) {
                    answer += ("\u001B[0m" + "|");
                    continue;
                }
                if (i % 4 == 0) {
                    answer += "---";
                    continue;
                }
                answer += miniMap[i / 4][j / 2].getTexture().getBackGroundColorCodeWithTexture();
                if (i % 4 == 1) {
                    if (miniMap[i / 4][j / 2].getObject() instanceof Building) {
                        answer += "  B  ";
                    } else
                        answer += "     ";
                } else if (i % 4 == 2) {
                    if (miniMap[i / 4][j / 2].getUnits().size() > 0) {
                        answer += "  S  ";
                    } else
                        answer += "     ";
                } else if (i % 4 == 3) {
                    if (miniMap[i / 4][j / 2].getObject() instanceof Tree) {
                        answer += "  T  ";
                    } else if (miniMap[i / 4][j / 2].getObject() instanceof Rock) {
                        answer += "  R  ";
                    } else
                        answer += "     ";
                }
            }
            answer += ("\u001B[0m" + "\n");
        }
        return answer.trim();
    }

    public String printMiniMap(int x, int y) {
        return showMapAlgorithm(getMiniMap(x, y));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Coordinates> getNeighbors(Coordinates point) {
        ArrayList<Coordinates> neighbors = new ArrayList<>();
        if (point.x > 0) {
            neighbors.add(new Coordinates(point.x - 1, point.y));
        }
        if (point.x < width - 1) {
            neighbors.add(new Coordinates(point.x + 1, point.y));
        }
        if (point.y > 0) {
            neighbors.add(new Coordinates(point.x, point.y - 1));
        }
        if (point.y < height - 1) {
            neighbors.add(new Coordinates(point.x, point.y + 1));
        }
        return neighbors;
    }

    public ArrayList<Coordinates> getMoreNeighbors(Coordinates point, int n) {
        ArrayList<Coordinates> neighbors = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n - i; j++) {
                if (Validation.areCoordinatesValid(point.x + i, point.y + j)) {
                    neighbors.add(new Coordinates(point.x - i, point.y - j));
                }
                if (Validation.areCoordinatesValid(point.x - i, point.y - j)) {
                    neighbors.add(new Coordinates(point.x + i, point.y + j));
                }
                if (Validation.areCoordinatesValid(point.x - i, point.y + j)) {
                    neighbors.add(new Coordinates(point.x - i, point.y + j));
                }
                if (Validation.areCoordinatesValid(point.x + i, point.y - j)) {
                    neighbors.add(new Coordinates(point.x + i, point.y - j));
                }
            }
        }
        return neighbors;
    }

    public Coordinates findEnemyNear(Unit unit, int distance) {
        ArrayList<Coordinates> neighbors = getMoreNeighbors(unit.getCoordinates(), distance);
        for (Coordinates neighbor : neighbors) {
            if (findCellWithXAndY(neighbor.x, neighbor.y).getUnits().size() > 0) {
                for (Unit enemy : findCellWithXAndY(neighbor.x, neighbor.y).getUnits()) {
                    if (!enemy.getOwner().getUsername().equals(unit.getOwner().getUsername())) {
                        return neighbor;
                    }
                }
            }
        }
        return null;
    }
}
