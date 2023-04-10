package models.buildings;

import models.Building;
import models.BuildingType;
import models.Material;
import models.MaterialInstance;

import java.util.ArrayList;
import java.util.List;

public class LookoutTower extends Building {
    public static final BuildingType type = BuildingType.CASTLE_BUILDINGS;
    public static final ArrayList<MaterialInstance> cost = new ArrayList<>(
            List.of(new MaterialInstance(Material.STONE, 10)));

    public static final String name = "Lookout Tower";
    public static final int hitpoint = 1;
    public static final int fireRange = 1;
    public static final int defendRage = 1; // TODO
}
