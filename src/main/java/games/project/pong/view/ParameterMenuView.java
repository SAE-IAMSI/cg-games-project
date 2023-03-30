package games.project.pong.view;

import games.project.Launcher;
import games.project.metier.entite.AuthPlayer;
import games.project.metier.manager.PlayerManager;
import games.project.stockage.Security;
import games.project.stockage.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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

private Session session = Session.getInstance();
private PlayerManager manager = PlayerManager.getInstance();


    public ParameterMenuView() {
        super("ParameterMenu.fxml");
    }

    @FXML
    private void back(){
        gameController.removeScreen(this);
        gameController.displayScreen(new StartMenuView());
    }

    @FXML
    private void login(){
        AuthPlayer p = null;

        if(manager.getPlayer(idLogin.getText())==null){
            infoLogin.setText("L'id n'existe pas");
        }
        else{
            p = manager.getPlayer(idLogin.getText());
            try {
                if(Security.checkPassword(passwordLogin.getText(),p.getSalt(),p.getHashedPassword())){

                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void register(){

    }

}
