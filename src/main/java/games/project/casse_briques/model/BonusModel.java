package games.project.casse_briques.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class BonusModel {
    private final String bonusName;
    private final ImageView shapeBonus;

    public BonusModel(String nom, Image image) {
        bonusName = nom;
        shapeBonus = new ImageView(image);
    }

    public String getBonusName() {
        return bonusName;
    }

    public ImageView getShapeBonus() {
        return shapeBonus;
    }
}
