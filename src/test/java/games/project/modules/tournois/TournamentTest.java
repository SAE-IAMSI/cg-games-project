package games.project.modules.tournois;

import games.project.metier.entite.AuthPlayer;
import games.project.metier.entite.Jeu;
import games.project.metier.entite.Player;
import games.project.metier.entite.Score;
import games.project.modules.tournois.metier.entite.Tournament;
import org.junit.jupiter.api.Test;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TournamentTest {




    @Test
    void testclassementparjeu(){
        Jeu jeu = new Jeu("FF");
        List<Jeu> liste = new ArrayList<>();
        liste.add(jeu);
        Tournament tournoi = new Tournament("test",new Timestamp(2023,4,10,0,0,0,0),new Timestamp(2023,4,11,0,0,0,0) , 5 );
        tournoi.setGames(liste);
        Map<Score, Integer> score;
        tournoi.setTournamentCode(15);
        score = tournoi.getGameLeaderboard(jeu);
        Map<Score, Integer> resultat = new HashMap<>();
        resultat.put(new Score(500,"FF"),5); // pas sur ici
        resultat.put(new Score(450,"FF"),4); // pas sur ici
        resultat.put(new Score(0,"FF"),0); // pas sur ici
        assertEquals(resultat.values(),score.values());
       // assertEquals(resultat.keySet(),score.keySet()); // pas sur le mettre ce assert
         //recupere bien les joueur mais ne recup pas les scores associé
    }

    @Test
    void testClassementGeneral(){
        Jeu jeu = new Jeu("FF");
        Jeu jeuK = new Jeu("KR");


        List<Jeu> liste = new ArrayList<>();
        liste.add(jeu);
        liste.add(jeuK);
        Tournament tournoi = new Tournament("test",new Timestamp(2023,4,10,0,0,0,0),new Timestamp(2023,4,11,0,0,0,0) , 5 );
        tournoi.setGames(liste);
        Map<AuthPlayer, Integer> classement = new HashMap<>();
        tournoi.setTournamentCode(15);
        AuthPlayer ebr105 = new AuthPlayer("ebr105");
        AuthPlayer ebr106 = new AuthPlayer("ebr106");
        AuthPlayer maus = new AuthPlayer("maus");
        classement.put(ebr105,10); // 5 +5
        classement.put(ebr106,8); // 4+4
        classement.put(maus,0); // 0+0

        assertEquals(classement.values(),tournoi.getMainLeaderboard().values());
        //assertEquals(classement.keySet()   ,tournoi.getMainLeaderboard().keySet()); // ici on veux recup que les login soit les meme ??

    }

    /*
    INSERT INTO SCORES(score, horodatage, codeJeu, login) VALUES
     (500,TO_TIMESTAMP('2023-04-09 23:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF')  , 'FF', 'ebr105')

      INSERT INTO SCORES(score, horodatage, codeJeu, login) VALUES
     (450,TO_TIMESTAMP('2023-04-09 23:22:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF')  , 'FF', 'ebr106')
     */




}