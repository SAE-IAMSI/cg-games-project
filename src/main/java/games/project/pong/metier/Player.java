package games.project.pong.metier;

public class Player {
    private String nom;
    private Score score;

    public Player(String nom){
        this.nom=nom;
        score = new Score();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Score getScore() {
        return score;
    }
}
