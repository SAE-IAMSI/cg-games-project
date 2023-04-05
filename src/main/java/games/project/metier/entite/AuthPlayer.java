package games.project.metier.entite;

import games.project.stockage.Security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AuthPlayer extends Player {
    private String department;
    private String hashedPassword;
    private byte[] salt;
    private boolean isAdmin;

    public AuthPlayer(String login) {
        super(login);
    }

    public AuthPlayer(String login, String department, String hashedPassword, byte[] salt, boolean isAdmin) {
        this(login);
        this.department = department;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
        this.isAdmin = isAdmin;
    }

    public String getLogin() {
        return super.getName();
    }

    public void setLogin(String login) {
        super.setName(login);
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    /*public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }*/

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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}