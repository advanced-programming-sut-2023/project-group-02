package models.buildings;

import models.*;

import java.util.ArrayList;
import java.util.List;

public class Church extends Building {
    public static final BuildingType type = BuildingType.TOWN_BUILDINGS;
    public static final ArrayList<MaterialInstance> cost = new ArrayList<>
            (List.of(new MaterialInstance(Material.GOLD,250)));

    public static final String name = "Church";
    //TODO popularity+=2
}
