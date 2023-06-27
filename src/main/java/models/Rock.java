package models;

import javafx.scene.image.ImageView;

public class Rock extends MapObject {
    private final Directions direction;

    public Rock(Directions direction) {
        this.direction = direction;
        this.name = "rock";
        this.owner = null;
        this.imagePath = "/images/rocks/" + direction.name().toLowerCase() + ".png";
    }

    public Directions getDirection() {
        return direction;
    }

    public ImageView getImageView() {
        ImageView imageView = new ImageView(getClass().getResource("/images/rocks/" + direction.name().toLowerCase() + ".png").toExternalForm());
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        return imageView;
    }
}
