package utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import models.Cell;
import models.Map;
import models.Coordinates;

public class PathFinder {
    private Map map;
    private final Coordinates startingPoint, endPoint;
    private LinkedList<Coordinates> path = new LinkedList<>();
    private HashSet<Coordinates> visitedPoints = new HashSet<>();

    private PathFinder(Map map, int sx, int sy, int ex, int ey) {
        this.map = map;
        this.startingPoint = new Coordinates(sx, sy);
        this.endPoint = new Coordinates(ex, ey);
        bfs();
    }

    private void bfs() {
        visitedPoints.add(startingPoint);

        // create a queue of points
        LinkedList<LinkedList<Coordinates>> queue = new LinkedList<>();

        // add to path containing the starting point to the queue
        LinkedList<Coordinates> startingPath = new LinkedList<>();
        startingPath.add(startingPoint);
        queue.add(startingPath);

        while (queue.size() > 0) {
            LinkedList<Coordinates> path = queue.get(0);
            queue.poll();
            Coordinates point = path.get(path.size() - 1);

            if (point.equals(endPoint)) {
                this.path = path;
                return;
            }

            ArrayList<Coordinates> neighbors = map.getNeighbors(point);
            for (Coordinates neighbor : neighbors) {
                Cell cell = map.findCellWithXAndY(neighbor.x, neighbor.y);
                if (!visitedPoints.contains(neighbor) && cell.isPassable()) {
                    visitedPoints.add(neighbor);
                    LinkedList<Coordinates> newPath = new LinkedList<>(path);
                    newPath.add(neighbor);
                    queue.add(newPath);
                }
            }
        }
    }

    public static LinkedList<Coordinates> getPath(Map map, int sx, int sy, int ex, int ey) {
        PathFinder bfs = new PathFinder(map, sx, sy, ex, ey);
        return bfs.path;
    }
}
