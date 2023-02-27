package games.project.koala_rock.View;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class VueDemarrage {

    private final VueMenu vueMenu = new VueMenu();


    public void screenStart() {
        Stage stage = new Stage();
        stage.setTitle("Koala Rock");
        stage.setResizable(false);

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: transparent ;");

        Pane pane = new Pane();
        borderPane.setCenter(pane);
        pane.setStyle("-fx-border-color: green ;");

        vueMenu.demarrerMenu(stage);
    }
}
