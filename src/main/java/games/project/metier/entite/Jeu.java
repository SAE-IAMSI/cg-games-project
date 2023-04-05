package games.project.metier.entite;

public class Jeu {
    private String code;
    private String libelle;
    private String path;

    public Jeu(String code) {
        this.code = code;
    }

    public Jeu(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public Jeu(String code, String libelle, String path) {
        this.code = code;
        this.libelle = libelle;
        this.path = path;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
