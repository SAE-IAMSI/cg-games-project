package games.project.casse_briques.view;


import games.project.casse_briques.BrickBreakerApplication;
import games.project.casse_briques.controller.BrickBreakerController;
import games.project.casse_briques.controller.GameMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

public class ViewRules extends Pane {

    @FXML
    private Text title;
    @FXML
    private Text objText;
    @FXML
    private Text lTxt;
    @FXML
    private Text objTitle;
    @FXML
    private Text lTitle;
    @FXML
    private Text backBtTxt;

    private final Font cFont;
    private final Font dFont;

    private final GameMenuController gameMenu;
    private final BrickBreakerController brickBreakerController;

    public ViewRules(BrickBreakerController brickBreakerController) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Rules.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        this.brickBreakerController = brickBreakerController;
        gameMenu = new GameMenuController(brickBreakerController);

        cFont = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 12);
        dFont = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 8);

        title.setFont(cFont);

        lTitle.setFont(cFont);
        objTitle.setFont(cFont);


        objText.setText("L’objectif d’une partie de Casse-Briques est de détruire toutes les briques qui apparaissent sur la fenêtre de jeu.\n " +
                "Ce jeu s’appuie sur les réflexes du joueur qui doit faire rebondir une bille avant que celle-ci ne sorte du cadre du jeu par le bas.");
        lTxt.setText("Lorsque le joueur lance une partie, un niveau contenant un certain nombre de briques est généré et les briques sont placées dans la partie supérieure de la fenêtre.\n" +
                "Dans la partie inférieure de la fenêtre de jeu, une raquette apparaît au centre et une bille statique est ajoutée au-dessus de la raquette.\n" +
                "Pour lancer la bille et commencer le niveau il faut appuyer sur [Espace].\n");
        backBtTxt.setFont(dFont);

    }

    @FXML
    private void clickBack(MouseEvent event) {
        brickBreakerController.getChildren().remove(this);
        gameMenu.startMenu();
    }

    @FXML
    private void nextPage() {
        this.brickBreakerController.getChildren().remove(this);
        gameMenu.rules2();
    }
}
