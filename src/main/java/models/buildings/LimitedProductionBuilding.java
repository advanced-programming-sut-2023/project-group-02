package models.buildings;

import models.BuildingType;
import models.MaterialInstance;

public class LimitedProductionBuilding extends ProductionBuilding {
    private int capacity;

    public LimitedProductionBuilding(String name, BuildingType type, MaterialInstance[] buildingMaterials,
            int initialHitpoint,
            int workerCount, int effectOnPopularity, int rate, Object[] products, int capacity) {
        super(name, type, buildingMaterials, initialHitpoint, workerCount, effectOnPopularity, rate, products);
        this.capacity = capacity;
    }

    public LimitedProductionBuilding(String name, BuildingType type, MaterialInstance[] buildingMaterials,
            int initialHitpoint,
            int workerCount, int effectOnPopularity, int rate, Object[] products, Object material, int capacity) {
        super(name, type, buildingMaterials, initialHitpoint, workerCount, effectOnPopularity, rate, products,
                material);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
