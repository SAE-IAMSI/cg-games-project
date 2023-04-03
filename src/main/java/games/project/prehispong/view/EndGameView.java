package games.project.prehispong.view;

import games.project.prehispong.controller.GameController;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class EndGameView extends GenericView{

    @FXML
    public Text title;
    private GameController gameController = GameController.getInstance();
    public EndGameView(String winner) {
        super("EndGame.fxml");
        title.setText("Le joueur : "+winner+" à remporté la partie");
    }

    @FXML
    private void menu(){
        gameController.removeScreen(this);
        gameController.displayScreen(new StartMenuView());
    }
    @FXML
    private void replay(){
        gameController.removeScreen(this);
        switch (gameController.getGamemode()) {
            case "PVP" -> gameController.run();
            case "IA" -> gameController.run(gameController.getDifficulty());
        }
    }
}
