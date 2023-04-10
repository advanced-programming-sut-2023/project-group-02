package models.buildings;

import models.*;

import java.util.ArrayList;
import java.util.List;

public class Well extends Building {
    public static final BuildingType type = BuildingType.TOWN_BUILDINGS;
    public static final ArrayList<MaterialInstance> cost = new ArrayList<>
            (List.of(new MaterialInstance(Material.GOLD,30)));

    public static final String name = "Well";
    public static final int numberOfWorkers = 1;
}
