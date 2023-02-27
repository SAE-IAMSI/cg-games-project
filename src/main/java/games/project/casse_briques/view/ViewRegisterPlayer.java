package games.project.casse_briques.view;

import games.project.casse_briques.BrickBreakerApplication;
import games.project.casse_briques.controller.BrickBreakerController;
import games.project.casse_briques.controller.GameMenuController;
import games.project.casse_briques.metier.manager.PlayerManager;
import games.project.casse_briques.stockage.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Map;

public class ViewRegisterPlayer extends Pane {

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private Label loginLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private Text title;
    @FXML
    private Text connect;

    @FXML
    private Text registerBtText;
    @FXML
    private Text quitText;

    @FXML
    private Text errorText;
    @FXML
    private ComboBox comboBox;
    @FXML
    private Text depText;
    @FXML
    private CheckBox checkBoxPDF;

    private final BrickBreakerController brickBreakerController;
    private final GameMenuController gameMenu;

    public ViewRegisterPlayer(BrickBreakerController brickBreakerController) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        this.brickBreakerController = brickBreakerController;
        this.gameMenu = new GameMenuController(brickBreakerController);
        Font cFont = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 7);
        Font tFont = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 12);
        Font btFont = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 6);
        confirmPasswordLabel.setFont(cFont);
        passwordLabel.setFont(cFont);
        loginLabel.setFont(cFont);
        title.setFont(tFont);
        registerBtText.setFont(btFont);
        quitText.setFont(btFont);
        connect.setFont(cFont);
        errorText.setFont(cFont);
        depText.setFont(cFont);

        // Code Snippet to put values in the combobox (menu déroulant)
        Map<String, String> departments = PlayerManager.getInstance().getDepartments();
        for (String key : departments.keySet()) {
            String field = key + " - " + departments.get(key);
            comboBox.getItems().add(field);
        }
    }

    @FXML
    private void register() {

        String dep;
        try {
            dep = this.comboBox.getValue().toString().split(" ")[0];
        } catch (NullPointerException e) {
            dep = "";
        }
        String loginR = this.login.getText();
        String pass = this.password.getText();
        String passC = this.confirmPassword.getText();
        if (checkBoxPDF.isSelected()) {
            if (loginR == null || pass == null || passC == null || dep == null || loginR.equals("") || pass.equals("") || passC.equals("") || dep.equals("")) {
                errorText.setText("Veuillez remplir tous les champs");
                errorText.setVisible(true);
            } else if (!pass.equals(passC)) {
                errorText.setText("Les mots de passe ne correspondent pas");
                errorText.setVisible(true);
                this.confirmPassword.clear();
            } else {
                PlayerManager.getInstance().createPlayer(loginR, dep, pass);
                brickBreakerController.getChildren().remove(this);
                gameMenu.showBtStartMenuConnected();
                Session.getInstance().connect(loginR);
            }
        } else {
            errorText.setText("Case de confidentialite non coche");
            errorText.setVisible(true);
        }
    }

    @FXML
    private void clickPDF() {
        try {
            Runtime.getRuntime().exec("explorer.exe " + BrickBreakerApplication.class.getResource("pdf/Politique-de-confidentialite-rgpd.pdf"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void clickConnect() {
        gameMenu.connectPlayer();
        brickBreakerController.getChildren().remove(this);
    }

    @FXML
    private void clickExit() {
        gameMenu.showBtStartMenuDisconnected();
        brickBreakerController.getChildren().remove(this);
    }
}
