package games.project.modules.tournois;

import games.project.metier.entite.Player;
import games.project.modules.tournois.metier.entite.Tournament;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;

class TournamentTest {



    @Test // sert a rien
    void TestCreationlistejoueur(String nomtournoi, int idtournoi, Timestamp datedebut, Timestamp datefin, int nbparticipantmax) {
        Tournament t =new Tournament(nomtournoi,datedebut,datefin,nbparticipantmax);
        assertEquals(new ArrayList<Player>(),t.getParticipants());

    }



    /*
    test ajout joueur
    test recup score par jeu
    test  recup classement generel en fonction des position des jouer dans les jeux -> test parametrique ??


     */

}