package games.project.casse_briques.view;

import games.project.casse_briques.BrickBreakerApplication;
import games.project.casse_briques.controller.BrickBreakerController;
import games.project.casse_briques.controller.GameMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

public class ViewRules2 extends Pane {
    @FXML
    private Text ballTxt;
    @FXML
    private Text racketTxt;
    @FXML
    private Text brickTxt;
    @FXML
    private Text brickTitle;
    @FXML
    private Text ballTitle;
    @FXML
    private Text racketTitle;
    @FXML
    private Text objTitle;
    @FXML
    private Text title;
    private final GameMenuController gameMenu;
    private final BrickBreakerController brickBreakerController;

    public ViewRules2(BrickBreakerController brickBreakerController) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Rules2.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        this.brickBreakerController = brickBreakerController;
        gameMenu = new GameMenuController(brickBreakerController);

        Font cFont = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 12);
        Font dFont = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 8);

        racketTitle.setFont(dFont);
        ballTitle.setFont(dFont);
        brickTitle.setFont(dFont);
        title.setFont(cFont);
        objTitle.setFont(cFont);

        brickTxt.setText("Les briques sont des blocs qui peuvent posséder entre 1 et 4 vies.\n" +
                "Les briques sont disposées dans la partie supérieure de la fenêtre de jeu et sont immobiles.\n" +
                "Lorsqu’elles sont détruites, le score du joueur est augmenté du nombre de points affiché à l’écran.\n" +
                "Lorsque toutes les briques d’un niveau sont détruites, la partie est terminée.");
        ballTxt.setText("La bille est un disque qui se déplace en ligne droite dans tout l’espace de la fenêtre de jeu, au contact d’un obstacle elle rebondit et son angle de déplacement est déterminé par l’endroit qu’elle touche.\n" +
                "Lorsque la bille touche une brique, cette dernière perd un vie ou est détruite s’il ne lui restait plus qu’une vie.\n" +
                "Si la bille sort de l’écran de jeu par le bas, alors elle perd une vie.");
        racketTxt.setText("La raquette est un rectangle de largeur comprise entre 55 et 275 qui se situe dans la partie inférieure de la fenêtre de jeu.\n" +
                "Elle se déplace vers la gauche ou la droite grâce aux touches fléchées du clavier correspondantes ou en suivant l’axe horizontal de la souris dans la fenêtre de jeu.");

    }

    @FXML
    private void previousPage() {
        this.brickBreakerController.getChildren().remove(this);
        gameMenu.rules();
    }

    @FXML
    private void nextPage() {
        this.brickBreakerController.getChildren().remove(this);
        gameMenu.rules3();
    }
}
