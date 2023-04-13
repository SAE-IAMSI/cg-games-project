package games.project.modules.parametres;

import games.project.modules.parametres.controller.ControllerFXML;
import games.project.stockage.Session;
import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Parametres extends Application{
    private Scene sceneParametres;
    private static BooleanProperty isConnected = new SimpleBooleanProperty(false);
    private static BooleanProperty isNotConnected = new SimpleBooleanProperty(true);
    private static BooleanProperty isAdmin = new SimpleBooleanProperty(false);
    private static StringProperty login = new SimpleStringProperty("") {
    };

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Parametres.class.getResource("parametres.fxml"));
        sceneParametres = new Scene(fxmlLoader.load());
        ControllerFXML controllerFXML = fxmlLoader.getController();

        stage.setResizable(false);
        stage.setTitle("CG Games");
        stage.setScene(sceneParametres);
        stage.show();
        controllerFXML.getLabelTournois().visibleProperty().bind(isConnected);
        controllerFXML.getLabelStatistiques().visibleProperty().bind(isConnected.and(isAdmin));
        controllerFXML.getButtonDeconnexion().visibleProperty().bind(isConnected);
        controllerFXML.getButtonSeConnecter().visibleProperty().bind(isNotConnected);
        controllerFXML.getButtonDeconnexion().textProperty().bind(login);
        controllerFXML.setGames();
    }

    public static void changeConnexion(String log, boolean connexion){
        isConnected.set(connexion);
        isNotConnected.set(!connexion);
        login.set("Déconnecter : " + log);
    }
    public static void changeAdmin(boolean admin){
        isAdmin.set(admin);
    }
}
