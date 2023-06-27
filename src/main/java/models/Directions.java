package models;

import java.util.ArrayList;
import java.util.Random;

public enum Directions {
    NORTH,
    EAST,
    WEST,
    SOUTH;

    public static Directions[] allDirections = {NORTH, SOUTH, WEST, EAST};

    public static Directions getDirectionWithName(String name) {
        if (name.equals("north")) return NORTH;
        if (name.equals("east")) return EAST;
        if (name.equals("west")) return WEST;
        if (name.equals("south")) return SOUTH;
        else return allDirections[new Random().nextInt(3)];
    }

    public static ArrayList<String> getDirectionsLowerCase() {
        ArrayList<String> directions = new ArrayList<>();
        for (Directions direction : allDirections) {
            directions.add(direction.name().toLowerCase());
        }
        return directions;
    }
}
