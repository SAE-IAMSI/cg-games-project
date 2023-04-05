package games.project.modules.tournois;

import games.project.metier.entite.Jeu;
import games.project.metier.entite.Player;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Tournois {
    private final int IdTournoi;

    private final String Nomtournoi;

    private final Timestamp Datedebut;
    private final Timestamp Datefin;

    private final int NbParticipentMax;

    private final ArrayList<Player> ListeParticipant;

    private final ArrayList<Jeu> Listedesjeux;

    public Tournois(int idtournoi, String nomtournoi, Timestamp datedebut, Timestamp datefin, int nbparticipantmax, ArrayList<Jeu> listedesjeux) {
        IdTournoi = idtournoi;
        Nomtournoi = nomtournoi;
        Datedebut = datedebut;
        Datefin = datefin;
        NbParticipentMax = nbparticipantmax;
        ListeParticipant = new ArrayList<>();
        Listedesjeux = listedesjeux;
    }

    public int getIdTournoi() {
        return IdTournoi;
    }

    public String getNomtournoi() {
        return Nomtournoi;
    }

    public Date getDatedebut() {
        return Datedebut;
    }

    public Date getDatefin() {
        return Datefin;
    }

    public int getNbParticipentMax() {
        return NbParticipentMax;
    }

    public ArrayList<Player> getListeParticipant() {
        return ListeParticipant;
    }
}
