package models;

import models.units.Unit;
import utils.Utils;

import java.util.ArrayList;

public class Map {
    public final int width;
    public final int height;
    private final Cell[][] map;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.map = new Cell[height][width];
    }

    public Cell findCellWithXAndY(int x, int y) {
        return map[y][x]; // TODO thinking about changing x and y
    }

    public ArrayList<Cell> findMoreThanOneCell(int x1, int y1, int x2, int y2) {
        ArrayList<Cell> answer = new ArrayList<>();
        for (int i = y1; i <= y2 ; i++) {
            for (int j = x1; j <= x2 ; j++) {
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

    public void addObject(MapObject object, int x, int y) {
        findCellWithXAndY(x, y).setObject(object);
        object.setCoordinates(x, y);
    }

    public Cell[][] getMiniMap(int x, int y) {
        Cell[][] miniMap = new Cell[21][21];
        int startX = Utils.keepNumbersLimited(y - 10, 0, width);
        int endX = Utils.keepNumbersLimited(y + 10, 0, width);
        int startY = Utils.keepNumbersLimited(x - 10, 0, height);
        int endY = Utils.keepNumbersLimited(x + 10, 0, height);

        for (int i = 0; i <= endX - startX; i++) {
            for (int j = 0; j <= endY - startY; j++) {
                miniMap[i][j] = map[i + startX][j + startY];
            }
        }
        return miniMap;
    }

    public String printMiniMap(int x, int y) {
        String answer = "";
        Cell[][] miniMap = getMiniMap(x, y);

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (!map[y][x].equals(null))
                    answer = answer + x + " " + y + " " + map[y][x].toString() + "\n";
            }
        }
        return answer;
    }
}
