package games.project.motron.metier.entite;

public class Stat {

    private int nbVictoire;
    private int nbDefaite;
    private int nbEgalite;
    private final int nbBlocs;
    private final int death;
    private final int kill;
    private float moyenneKillParPartie;

    public Stat(int nbBlocs, int death, int kill) {
        this.nbBlocs = nbBlocs;
        this.death = death;
        this.kill = kill;
        nbVictoire = 0;
        nbDefaite = 0;
        nbEgalite = 0;
        moyenneKillParPartie = 0;
    }

    public Stat(Stat stat) {
        this.nbVictoire = stat.nbVictoire;
        this.nbDefaite = stat.nbDefaite;
        this.nbEgalite = stat.nbEgalite;
        this.nbBlocs = stat.nbBlocs;
        this.death = stat.death;
        this.kill = stat.kill;
        this.moyenneKillParPartie = stat.moyenneKillParPartie;
    }

    public int getNbVictoire() {
        return nbVictoire;
    }

    public void setNbVictoire(int nbVictoire) {
        this.nbVictoire = nbVictoire;
    }

    public int getNbDefaite() {
        return nbDefaite;
    }

    public void setNbDefaite(int nbDefaite) {
        this.nbDefaite = nbDefaite;
    }

    public int getNbEgalite() {
        return nbEgalite;
    }

    public void setNbEgalite(int nbEgalite) {
        this.nbEgalite = nbEgalite;
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

    public float getMoyenneKillParPartie() {
        return moyenneKillParPartie;
    }

    public void setMoyenneKillParPartie() {
        if (nbVictoire + nbDefaite + nbEgalite == 0) {
            moyenneKillParPartie = 0;
        } else {
            float k = kill;
            float v = nbVictoire;
            float d = nbDefaite;
            float e = nbEgalite;
            this.moyenneKillParPartie = k / (v + d + e);
        }
    }
}
