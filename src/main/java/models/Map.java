package models;

import models.buildings.InventoryBuilding;
import models.units.Unit;
import utils.Utils;

import java.util.ArrayList;

public class Map {
    private final int width;
    private final int height;
    private final Cell[][] map;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.map = new Cell[height][width];
        for (int i = 0; i < height ; i++) {
            for (int j = 0; j < width ; j++) {
                map[i][j] = new Cell(Texture.EARTH);
            }
        }
    }

    public Cell findCellWithXAndY(int x, int y) {
        return map[y][x];
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

    private Cell[][] getMiniMap(int x, int y) {
        int startX = Utils.keepNumbersLimited(y - 10, 0, width);
        int endX = Utils.keepNumbersLimited(y + 10, 0, width);
        int startY = Utils.keepNumbersLimited(x - 10, 0, height);
        int endY = Utils.keepNumbersLimited(x + 10, 0, height);
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
        System.out.println(height + " " + width);

        for (int i = 0; i <= 3 * height; i++) {
            if (i % 3 == 0) {
                for (int j = 0; j < width; j++) {
                    answer += "--------";
                }
                answer += "\n";
                continue;
            }
            if (i % 3 == 1) {
                for (int j = 0; j <= width * 2; j++) {
                    if (j % 2 == 0) {
                        answer += "|";
                        continue;
                    }
                    if (miniMap[i/3][j/2].getObject() != null) {
                        answer += Utils.sevenCharacterise(miniMap[i/3][j/2].getObject().getName());
                    } else answer += "#######";
                    answer += miniMap[i/3][j/2].getTexture().getBackGroundColorCodeWithTexture();
                }
            }
            if (i % 3 == 2) {
                for (int j = 0; j <= width * 2; j++) {
                    if (j % 2 == 0) {
                        answer += "|";
                        continue;
                    }
                    if (miniMap[i/3][j/2].getObject() != null && miniMap[i/3][j/2].getObject().getOwner() != null) {
                        answer += Utils.sevenCharacterise(miniMap[i/3][j/2].getObject().getOwner().getUsername());
                    } else answer += "#######";
                    answer += miniMap[i/3][j/2].getTexture().getBackGroundColorCodeWithTexture();
                }
            }
            answer += "\n";
        }
        return answer.trim();
    }

    public String printMiniMap(int x, int y) {
        return showMapAlgorithm(getMiniMap(x,y));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
