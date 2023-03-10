package games.project.motron.metier.entite;

public class ScorePartie {

    private String resultat;
    private int nbBlocs;
    private int death;
    private int kill;
    private int score;
    private String login;
    private int id;

    public ScorePartie(int id, String login,int score,int kill, int death, int nbBlocs, String resultat) {
        this.resultat = resultat;
        this.nbBlocs = nbBlocs;
        this.death = death;
        this.kill = kill;
        this.score = score;
        this.login = login;
        this.id = id;
    }

    public String getResultat() {
        return resultat;
    }

    public int getNbBlocs() {
        return nbBlocs;
    }

    public int getDeath() {
        return death;
    }

    public int getKill() {
        return kill;
    }

    public int getScore() {
        return score;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }
}
