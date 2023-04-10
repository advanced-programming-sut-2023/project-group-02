package models.buildings;

import models.BuildingType;
import models.Material;
import models.MaterialInstance;

import java.util.ArrayList;
import java.util.List;

public class PerimeterTower {
    public static final BuildingType type = BuildingType.CASTLE_BUILDINGS;
    public static final ArrayList<MaterialInstance> cost = new ArrayList<>(List.of(new MaterialInstance(Material.STONE,10)));

    public static final String name = "perimeter tower";
    public static final int hitpoint = 1;
    public static final int fireRange = 1;
    public static final int defendRange = 1; //TODO
}
