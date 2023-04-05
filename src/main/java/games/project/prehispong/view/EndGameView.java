package games.project.prehispong.view;

import games.project.prehispong.controller.GameController;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class EndGameView extends GenericView {

    @FXML
    public Text title;
    @FXML
    public Text score;

    public EndGameView(String winner,int score, GameController controller) {
        super("EndGame.fxml", controller);
        if(score>=0){
            title.setText("Le joueur '" + winner + "' à remporté la partie");
            this.score.setText("Score : "+score);
        }
        else{
            title.setText("Vous avez perdu !");
        }
    }
    public EndGameView(String winner, GameController controller) {
        super("EndGame.fxml", controller);
        title.setText("Le joueur '" + winner + "' à remporté la partie");
    }

    @FXML
    private void menu() {
        gameController.removeScreen(this);
        gameController.displayScreen(new StartMenuView(gameController));
    }

    @FXML
    private void replay() {
        gameController.removeScreen(this);
        switch (gameController.getGamemode()) {
            case "PVP" -> gameController.run();
            case "IA" -> gameController.run(gameController.getDifficulty());
        }
    }
}
