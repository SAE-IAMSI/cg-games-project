package games.project.modules.tournois;

import games.project.metier.entite.Player;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class TournoisTest {



    @Test // sert a rien
    void TestCreationlistejoueur(String nomtournoi, int idtournoi, Timestamp datedebut, Timestamp datefin, int nbparticipantmax) {
        Tournois t =new Tournois(idtournoi,nomtournoi,datedebut,datefin,nbparticipantmax,null);
        assertEquals(new ArrayList<Player>(),t.getListeParticipant());

    }



    /*
    test ajout joueur
    test recup score par jeu
    test  recup classement generel en fonction des position des jouer dans les jeux -> test parametrique ??


     */

}