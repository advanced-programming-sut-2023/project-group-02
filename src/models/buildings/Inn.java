package models.buildings;

import models.Building;
import models.BuildingType;
import models.Material;
import models.MaterialInstance;

import java.util.ArrayList;
import java.util.List;

public class Inn extends Building {
    public static final BuildingType type = BuildingType.FOOD_PROCESSING_BUILDINGS;
    public static final ArrayList<MaterialInstance> cost = new ArrayList<>
            (List.of(new MaterialInstance(Material.WOOD,20),new MaterialInstance(Material.GOLD,100)));

    public static final String name = "Inn";
    public static final int popularityRate = 2;
    public static final int wineUsage = 2;
    public static final int rate = 1; //TODO
}
