package models.buildings;

import models.BuildingType;
import models.Material;
import models.MaterialInstance;

import java.util.ArrayList;
import java.util.List;

public class LookoutTower {
    public static final BuildingType type = BuildingType.CASTLE_BUILDINGS;
    public static final ArrayList<MaterialInstance> cost = new ArrayList<>(List.of(new MaterialInstance(Material.STONE,10)));

    public static final String name = "lookout tower";
    public static final int hitpoint = 1;
    public static final int fireRange = 1;
    public static final int defendRage = 1; //TODO
}
