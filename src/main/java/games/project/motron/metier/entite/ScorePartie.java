package games.project.motron.metier.entite;

public class ScorePartie {

    private final String resultat;
    private final int nbBlocs;
    private final int death;
    private final int kill;
    private final int codeScore;

    public ScorePartie(int codeScore, int kill, int death, int nbBlocs, String resultat) {
        this.resultat = resultat;
        this.nbBlocs = nbBlocs;
        this.death = death;
        this.kill = kill;
        this.codeScore = codeScore;
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

    public int getCodeScore() {
        return codeScore;
    }
}
