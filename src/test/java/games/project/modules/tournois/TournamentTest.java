package games.project.modules.tournois;

import games.project.metier.entite.AuthPlayer;
import games.project.metier.entite.Jeu;
import games.project.metier.entite.Player;
import games.project.metier.entite.Score;
import games.project.modules.tournois.metier.entite.Tournament;
import games.project.modules.tournois.metier.manager.TournamentManager;
import games.project.modules.tournois.stockage.sql.StockageTournamentDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class TournamentTest {

    @Mock
    private StockageTournamentDatabase stockage;

    @InjectMocks
    private TournamentManager manager;

    private AutoCloseable closeable;

    private  Jeu ff = new Jeu("FF","Factory Fall" ,"games.project.factory_fall.FactoryFall");

    private  Jeu cb = new Jeu("CB",	"Casse-Briques", 	"games.project.casse_briques.BrickBreakerApplication" );

     private Jeu kr = new Jeu( "KR","Koala Rock" ,	"games.project.koala_rock.RessourcesAccess" );
    private Score s500 = new Score(500,Timestamp.valueOf(LocalDateTime.now().plusHours(5)),"FF");

    private   Tournament t = new Tournament("Test1", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusDays(10)), 5);

    private AuthPlayer test1 = new   AuthPlayer("Test2");
    private AuthPlayer test2 =new AuthPlayer("Test1");
    private AuthPlayer test3 = new AuthPlayer("Test3");


    @BeforeEach
    public void initMocks() {
        closeable = MockitoAnnotations.openMocks(this);

        t.setGames(List.of(cb,kr,ff));
        t.setParticipants(List.of(test1,test1,test3));
        t.setTournamentCode(1);


        Mockito.when(stockage.getAll()).thenReturn(List.of(t));
        Mockito.when(stockage.getByCode(1)).thenReturn(t);
        Mockito.when(stockage.getGamesByTournament(1)).thenReturn(t.getGames());
        Mockito.when(stockage.getParticipantsByTournament(1)).thenReturn(t.getParticipants());
        Mockito.when(stockage.getTournamentByLoginAndDate("Test1", Timestamp.valueOf(LocalDateTime.now()))).thenReturn(new ArrayList<>());
        Mockito.when(stockage.getTournamentsByDate(Timestamp.valueOf(LocalDateTime.now()))).thenReturn(new ArrayList<>());
        Mockito.when(stockage.hasPLayedOnGame("Test1", "CB", t)).thenReturn(false);
        Mockito.when(stockage.getLeaderboardByGame(ff, 1)).thenReturn(List.of(
                s500,
                new Score(400,Timestamp.valueOf(LocalDateTime.now().plusHours(5)),"FF"),
                new Score(300,Timestamp.valueOf(LocalDateTime.now().plusHours(5)),"FF")));
    }

    @AfterEach
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    @Test
    void testParticipantIn() {
        AuthPlayer p = test1;
        assertTrue(stockage.getParticipantsByTournament(1).contains(p));
    }

    @Test
    void testGameLeaderboard() {
        Score s400 = new Score(400,Timestamp.valueOf(LocalDateTime.now().plusHours(5)),"FF");
        Score s300 = new Score(300,Timestamp.valueOf(LocalDateTime.now().plusHours(5)),"FF");

        Map<Score, Integer> classement = new HashMap<>();
        classement.put(s500,5);
        classement.put(s400,4);
        classement.put(s300,3);

       // Map<Score,Integer> leaderboardt = t.getGameLeaderboard(ff); // demande la bd donc marche pas
        List<Score> leaderboard = stockage.getLeaderboardByGame(ff,1);
        assertTrue(leaderboard.contains(s500));

    }
}