package games.project.factory_fall.logique;

import games.project.factory_fall.stockage.Security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Cette classe représente un joueur connecté
 */
public class AuthPlayer extends Joueur {
    private String hashedPassword;
    private byte[] salt;

    private String numDepartement;

    public AuthPlayer(String login) {
        super(login);
    }

    public AuthPlayer(String login, String numDepartement, String hashedPassword, byte[] salt) {
        this(login);
        this.salt = salt;
        this.hashedPassword = hashedPassword;
        this.numDepartement = numDepartement;
    }

    public String getLogin() {
        return super.getPseudo();
    }

    public void setLogin(String login) {
        this.setPseudo(login);
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getDepartement() {
        return numDepartement;
    }

    public void setDepartement(String departement) {
        this.numDepartement = departement;
    }

    /**
     * Hash the password in params with the salt attribute and put the newly hashedPassword in the attribute.
     *
     * @param password - A clear password
     */
    public void setPassword(String password) {
        try {
            this.hashedPassword = Security.toHexString(Security.getSHA(password, salt));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}