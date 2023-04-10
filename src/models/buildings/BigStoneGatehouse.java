package models.buildings;

import models.Building;
import models.BuildingType;
import models.Material;
import models.MaterialInstance;

import java.util.ArrayList;
import java.util.List;

public class BigStoneGatehouse extends Building {
    public static final BuildingType type = BuildingType.CASTLE_BUILDINGS;
    public static ArrayList<MaterialInstance> cost = new ArrayList<>(List.of(new MaterialInstance(Material.STONE, 20)));

    public final String name = "Big stone gatehouse";
    public final int hitpoint = 1; // TODO
}
