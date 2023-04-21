package models;

public class WaterZone extends MapObject {
    private final WaterZoneType type;

    public WaterZone(WaterZoneType type) {
        this.type = type;
    }

    public WaterZoneType getType() {
        return type;
    }
}
