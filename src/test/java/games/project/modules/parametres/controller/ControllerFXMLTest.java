package games.project.modules.parametres.controller;

import games.project.metier.manager.PlayerManager;
import games.project.modules.parametres.Parametres;
import games.project.stockage.Session;
import javafx.fxml.FXMLLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ControllerFXMLTest {
    private ControllerFXML controller;

    @BeforeAll
    public void initMock(){
        FXMLLoader fxmlLoader = new FXMLLoader(Parametres.class.getResource("parametres.fxml"));
        controller = fxmlLoader.getController();
    }

    /*@Test
    public void lanceTournoisQuandPpasConnecte(){
        Mockito.when(Session.getInstance().isConnected()).thenReturn(false);
        controller.lanceTournois();
        assertEquals("Le joueur doit être connecté", controller.getLabelErreur().getText());
    }
    @Test
    public void lanceTournoisQuandConnecte(){
        Mockito.when(Session.getInstance().isConnected()).thenReturn(true);
        controller.lanceTournois();
        assertEquals("", controller.getLabelErreur().getText());
    }
    @Test
    public void lanceStatsQuandPasConnecte(){
        Mockito.when(Session.getInstance().isConnected()).thenReturn(false);
        controller.lanceStatistiques();
        assertEquals("Le joueur doit être connecté", controller.getLabelErreur().getText());
    }
    @Test
    public void lanceStatsQuandPasAdmin(){
        Mockito.when(PlayerManager.getInstance().getPlayer(Session.getInstance().getLogin()).isAdmin()).thenReturn(false);
        controller.lanceStatistiques();
        assertEquals("Le joueur doit être admin", controller.getLabelErreur().getText());
    }
    @Test
    public void lanceStatsQuandAdmin(){
        Mockito.when(PlayerManager.getInstance().getPlayer(Session.getInstance().getLogin()).isAdmin()).thenReturn(true);
        controller.lanceStatistiques();
        assertEquals("", controller.getLabelErreur().getText());
    }*/

}