package models;

public enum MartialEquipment {
    // made in Poleturner using wood:
    SPEAR("Spear",5),
    PIKE("Pike",8),
    // made in Fletcher using wood:
    BOW("Bow",10),
    CROSSBOW("Crossbow",10),
    // made in Armourer:
    METAL_ARMOUR("Metal Armour",8),
    // made in Diary Farm:
    LEATHER_ARMOUR("Leather Armour",7),
    // made in Black Smith using Iron:
    MACE("Mace",13),
    SWORD("Sword",15),
    // made in Stable:
    HORSE("horse",25);

    private final String name;
    private final int price;

    MartialEquipment(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getBuyPrice() {
        return price;
    }

    public int getSellPrice() {
        return price/2;
    }
}
