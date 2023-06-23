package models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.Utils;

public enum Food {
    APPLES("apple",4),
    WHEAT("wheat",4),
    FLOUR("flour",3),
    BREAD("bread",3),
    MEAT("meat",8),
    BARLEY("barley",4),
    BEER("beer",3),
    CHEESE("cheese",5);

    private final String name;
    private final int price;
    private final String imagePath;

    Food(String name, int price) {
        this.name = name;
        this.price = price;
        imagePath = "/images/items/" + name + ".png";
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

    public String getImagePath() {
        return imagePath;
    }
}
