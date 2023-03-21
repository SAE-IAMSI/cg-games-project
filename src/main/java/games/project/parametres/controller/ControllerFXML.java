package games.project.parametres.controller;

import games.project.motron.Motron;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class ControllerFXML {
    @FXML
    private Button motronButton;

    @FXML
    protected void lanceMotron(){
        Motron m = new Motron();
        m.start();
    }
}
