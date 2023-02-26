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

public class ViewRules3 extends Pane {
    @FXML
    private Text life;
    @FXML
    private Text sablier;
    @FXML
    private Text length;
    @FXML
    private Text objTitle;
    @FXML
    private Text title;

    private final GameMenuController gameMenu;
    private final BrickBreakerController brickBreakerController;

    public ViewRules3(BrickBreakerController brickBreakerController) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Rules3.fxml"));
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
        objTitle.setFont(cFont);
        title.setFont(cFont);

        life.setText("Permet de gagner ou perdre une vie.");
        sablier.setText("Permet d'ajouter/d'enlever 10 secondes au chronomètre.");
        length.setText("Permet de d'agrandir/réduire la raquette de 20.");
    }

    @FXML
    private void previousPage() {
        this.brickBreakerController.getChildren().remove(this);
        gameMenu.rules2();
    }
}
