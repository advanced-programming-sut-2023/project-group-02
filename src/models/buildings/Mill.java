package models.buildings;

import models.Building;
import models.BuildingType;
import models.Material;
import models.MaterialInstance;

import java.util.ArrayList;
import java.util.List;

public class Mill extends Building {
    public static final BuildingType type = BuildingType.FOOD_PROCESSING_BUILDINGS;
    public static final ArrayList<MaterialInstance> cost = new ArrayList<>
            (List.of(new MaterialInstance(Material.WOOD,20)));

    public static final String name = "Mill";
    public static final int rate = 2; //TODO
    public static final int numberOfWorkers = 3;
}
