package games.project.pong.view;

import games.project.Launcher;
import games.project.metier.entite.AuthPlayer;
import games.project.metier.entite.Player;
import games.project.metier.manager.PlayerManager;
import games.project.stockage.Security;
import games.project.stockage.Session;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.w3c.dom.Text;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class ParameterMenuView extends GenericView{

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
public ComboBox comboBox;
private Session session = Session.getInstance();
private PlayerManager manager = PlayerManager.getInstance();


    public ParameterMenuView() {
        super("ParameterMenu.fxml");
        Map<String, String> departments = PlayerManager.getInstance().getDepartments();
        for (String key : departments.keySet()) {
            String field = key + " - " + departments.get(key);
            comboBox.getItems().add(field);
        }
    }

    @FXML
    private void back(){
        gameController.removeScreen(this);
        gameController.displayScreen(new StartMenuView());
    }

    @FXML
    private void login(){
        AuthPlayer p = null;

        if(!(idLogin.getText().equals("")) && !(passwordLogin.getText().equals(""))){
                p = manager.getPlayer(idLogin.getText());
                try {
                    if(p!=null&&Security.checkPassword(passwordLogin.getText(),p.getSalt(),p.getHashedPassword())){
                        session.connect(idLogin.getText());
                        if(session.isConnected()){
                            infoLogin.setText("Le joueur '"+session.getLogin()+"' est connecté");
                            connectBt.setDisable(true);
                            decoBt.setDisable(false);
                            passwordLogin.setText("");
                            idLogin.setText("");
                            passwordLogin.setDisable(true);
                            idLogin.setDisable(true);
                        }
                    }
                    else{
                        infoLogin.setText("Identifiant ou mot de passe incorrect");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }
        else{
            infoLogin.setText("Tous les champs ne sont pas remplis");
        }
    }

    @FXML
    private void disconnect(){
        infoLogin.setText("Joueur déconnecté");
        session.disconnect();
        connectBt.setDisable(false);
        decoBt.setDisable(true);
        passwordLogin.setDisable(false);
        idLogin.setDisable(false);
    }

    @FXML
    private void register(){

        String dep;
        try {
            dep = this.comboBox.getValue().toString().split(" ")[0];
        } catch (NullPointerException e) {
            dep = "";
        }

        if(!idRegister.getText().equals("") && !passwordRegister.getText().equals("") && !confirmRegister.getText().equals("")){
            if(passwordRegister.getText().equals(confirmRegister.getText())){
                PlayerManager.getInstance().createPlayer(idRegister.getText(), dep, passwordRegister.getText());
            }
        }
        else{
            infoRegister.setText("Tous les champs ne sont pas remplis");
        }
    }

    @FXML
    private void delete(){
        if(Session.getInstance().isConnected()){
            PlayerManager.getInstance().deletePlayer(Session.getInstance().getLogin());
        }

    }

}
