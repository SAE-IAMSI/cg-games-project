package games.project.casse_briques.controller;

import games.project.casse_briques.BrickBreakerApplication;
import games.project.casse_briques.model.BonusModel;
import javafx.scene.image.Image;

import java.util.Random;

public class BonusController {
    private final BrickBreakerController brickBreakerController;

    public BonusController(BrickBreakerController brickBreakerController) {
        this.brickBreakerController = brickBreakerController;
    }

    /**
     * Génère un bonus aléatoirement parmi ceux existant
     **/
    public BonusModel generateBonus() {
        BonusModel bonus = new BonusModel(null, null);

        int a = new Random().nextInt(5); // Entier aléatoire entre 0 et X-1
        if (a == 0) {
            bonus = bonusLife();
        } else if (a == 1) {
            bonus = bonusPlusSize();
        } else if (a == 2) {
            bonus = malusSize();

        } else if (a == 3) {
            bonus = bonusTime();

        } else if (a == 4) {
            bonus = malusTime();
        }
        return bonus;
    }

    public BonusModel bonusLife() {
        BonusModel bonus = new BonusModel("life", new Image(BrickBreakerApplication.class.getResource("textures/LifeBonus.png").toString()));
        bonus.getShapeBonus().setFitWidth(20);
        bonus.getShapeBonus().setFitHeight(20);
        return bonus;
    }

    public BonusModel bonusPlusSize() {
        BonusModel bonus = new BonusModel("plusSize", new Image(BrickBreakerApplication.class.getResource("textures/LenghtBonus.png").toString()));
        bonus.getShapeBonus().setFitWidth(20);
        bonus.getShapeBonus().setFitHeight(20);

        return bonus;
    }

    public BonusModel malusSize() {
        BonusModel malus = new BonusModel("malusSize", new Image(BrickBreakerApplication.class.getResource("textures/LenghtMalus.png").toString()));
        malus.getShapeBonus().setFitWidth(25);
        malus.getShapeBonus().setFitHeight(15);
        return malus;
    }

    public BonusModel bonusTime() {
        BonusModel bonus = new BonusModel("bonusTime", new Image(BrickBreakerApplication.class.getResource("textures/bonusSablier.png").toString()));
        bonus.getShapeBonus().setFitHeight(30);
        bonus.getShapeBonus().setFitWidth(30);
        return bonus;
    }

    public BonusModel malusTime() {
        BonusModel malus = new BonusModel("malusTime", new Image(BrickBreakerApplication.class.getResource("textures/malusSablier.png").toString()));
        malus.getShapeBonus().setFitHeight(30);
        malus.getShapeBonus().setFitWidth(30);
        return malus;
    }


}
