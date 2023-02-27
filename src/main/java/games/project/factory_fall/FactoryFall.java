package games.project.factory_fall;

import games.project.factory_fall.stockage.SQLUtils;
import games.project.factory_fall.vues.VueMenuPrincipal;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class FactoryFall extends Application {

    private final EventHandler<ActionEvent> quitter = actionEvent -> {
        try {
            stop();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VueMenuPrincipal vueMenuPrincipal = VueMenuPrincipal.getInstance();
        vueMenuPrincipal.setButtonQuitterListener(quitter);
    }

    @Override
    public void stop() throws Exception {
        SQLUtils.getInstance().closeConnection();
        super.stop();
    }

}
