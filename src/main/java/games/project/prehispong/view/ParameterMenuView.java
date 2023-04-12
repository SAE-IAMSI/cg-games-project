package games.project.prehispong.view;

import games.project.metier.entite.AuthPlayer;
import games.project.metier.manager.PlayerManager;
import games.project.prehispong.controller.GameController;
import games.project.stockage.Security;
import games.project.stockage.Session;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class ParameterMenuView extends GenericView {

    @FXML
    public TextField idLogin;
    @FXML
    public PasswordField passwordLogin;
    @FXML
    public TextField idRegister;
    @FXML
    public PasswordField passwordRegister;
    @FXML
    public PasswordField confirmRegister;
    @FXML
    public Label infoLogin;
    @FXML
    public Button connectBt;
    @FXML
    public Button decoBt;
    @FXML
    public Label infoRegister;
    @FXML
    public Label infoDelete;
    @FXML
    public ComboBox comboBox;

    @FXML
    public TextField editId;
    @FXML
    public PasswordField editOldP;
    @FXML
    public PasswordField editNewP;
    @FXML
    public PasswordField editConfirm;
    @FXML
    public Label editText;
    private final Session session = Session.getInstance();
    private final PlayerManager manager = PlayerManager.getInstance();


    public ParameterMenuView(GameController controller) {
        super("ParameterMenu.fxml", controller);
        Map<String, String> departments = PlayerManager.getInstance().getDepartments();
        for (String key : departments.keySet()) {
            String field = key + " - " + departments.get(key);
            comboBox.getItems().add(field);
        }
        checkForEditProfile();
        if (Session.getInstance().isConnected()) {
            connectBt.setDisable(true);
            idLogin.setDisable(true);
            passwordLogin.setDisable(true);
            decoBt.setDisable(false);
            infoLogin.setText("Le joueur '" + Session.getInstance().getLogin() + "' est connecte !");
        }

    }


    private void connectPlayer() {
        gameController.getPlayer1().setName(Session.getInstance().getLogin());
        gameController.p1.setText(gameController.getPlayer1().getName());
        checkForEditProfile();
    }

    private void disconnectPlayer() {
        gameController.getPlayer1().setName("p1");
        gameController.p1.setText(gameController.getPlayer1().getName());
    }

    private void checkForEditProfile() {
        if (Session.getInstance().isConnected()) {
            editId.setText(Session.getInstance().getLogin());
            editNewP.setDisable(false);
            editOldP.setDisable(false);
            editConfirm.setDisable(false);
        } else {
            editId.setText("");
            editNewP.setDisable(true);
            editOldP.setDisable(true);
            editConfirm.setDisable(true);
        }
    }

    @FXML
    private void back() {
        gameController.removeScreen(this);
        gameController.displayScreen(new StartMenuView(gameController));
    }

    @FXML
    private void login() {
        AuthPlayer p = null;
        editText.setText("");
        infoDelete.setText("");
        infoRegister.setText("");
        clearEditText();
        if (!(idLogin.getText().equals("")) && !(passwordLogin.getText().equals(""))) {
            p = manager.getPlayer(idLogin.getText());
            try {
                if (p != null && Security.checkPassword(passwordLogin.getText(), p.getSalt(), p.getHashedPassword())) {
                    session.connect(idLogin.getText());
                    if (session.isConnected()) {
                        infoLogin.setText("Le joueur '" + session.getLogin() + "' est connecte");
                        connectBt.setDisable(true);
                        decoBt.setDisable(false);
                        passwordLogin.setText("");
                        idLogin.setText("");
                        passwordLogin.setDisable(true);
                        idLogin.setDisable(true);
                        connectPlayer();
                        checkForEditProfile();
                    }
                } else {
                    infoLogin.setText("Identifiant ou mot de passe incorrect");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            infoLogin.setText("Tous les champs ne sont pas remplis");
        }
    }

    @FXML
    private void disconnect() {
        clearEditText();
        editText.setText("");
        infoRegister.setText("");
        infoLogin.setText("Joueur deconnecte");
        session.disconnect();
        connectBt.setDisable(false);
        decoBt.setDisable(true);
        passwordLogin.setDisable(false);
        idLogin.setDisable(false);
        disconnectPlayer();
        checkForEditProfile();
    }

    @FXML
    private void register() {
        infoDelete.setText("");
        infoLogin.setText("");
        editText.setText("");
        clearEditText();
        String dep;
        try {
            dep = this.comboBox.getValue().toString().split(" ")[0];
        } catch (NullPointerException e) {
            dep = "";
        }

        if (!idRegister.getText().equals("") && !passwordRegister.getText().equals("") && !confirmRegister.getText().equals("") && !dep.equals("")) {
            if (passwordRegister.getText().equals(confirmRegister.getText())) {
                PlayerManager.getInstance().createPlayer(idRegister.getText(), dep, passwordRegister.getText(), false);
                infoRegister.setText("Joueur '" + idRegister.getText() + "' enregister !");
                passwordRegister.setText("");
                confirmRegister.setText("");
                idRegister.setText("");
            }
        } else {
            infoRegister.setText("Tous les champs ne sont pas remplis");
        }
    }

    @FXML
    private void delete() {
        infoRegister.setText("");
        infoLogin.setText("");
        editText.setText("");
        if (Session.getInstance().isConnected()) {
            PlayerManager.getInstance().deletePlayer(Session.getInstance().getLogin());
            infoDelete.setText("Joueur supprimer !");
            disconnect();
        } else {
            infoDelete.setText("Erreur !");
        }
    }

    @FXML
    private void editProfile() {
        infoLogin.setText("");
        infoRegister.setText("");
        if (!editOldP.getText().equals("") && !editNewP.getText().equals("") && !editConfirm.getText().equals("")) {
            if (editNewP.getText().equals(editConfirm.getText())) {
                AuthPlayer p = manager.getPlayer(Session.getInstance().getLogin());
                try {
                    if (p != null && Security.checkPassword(editOldP.getText(), p.getSalt(), p.getHashedPassword())) {
                        manager.updatePlayer(Session.getInstance().getLogin(), p.getDepartment(), editNewP.getText(), false);
                        editText.setText("Modification effectue !");
                    } else {
                        editText.setText("Mot de passe invalide");
                    }
                } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                    throw new RuntimeException(e);
                }
            } else {
                editText.setText("Les mots de passe ne sont pas similaire");
            }
        } else {
            editText.setText("Tous les champs ne sont pas remplis");
        }
        clearEditText();
    }

    private void clearEditText() {
        editNewP.setText("");
        editOldP.setText("");
        editConfirm.setText("");
    }

}
