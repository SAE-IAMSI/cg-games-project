package games.project.modules.tournois.metier.manager;

import games.project.metier.entite.AuthPlayer;
import games.project.metier.entite.Jeu;
import games.project.modules.tournois.metier.entite.Tournament;
import games.project.modules.tournois.stockage.sql.StockageTournamentDatabase;

import java.sql.Timestamp;
import java.util.List;

public class TournamentManager {

    private static TournamentManager instance = null;
    private final StockageTournamentDatabase stockage = new StockageTournamentDatabase();

    private TournamentManager() {}

    public static TournamentManager getInstance() {
        if (instance == null) instance = new TournamentManager();
        return instance;
    }

    public void createTournament(Tournament tournament) {
        stockage.create(tournament);
        stockage.setGamesOnTournament(tournament.getGames(), tournament.getTournamentCode());
        for (AuthPlayer p : tournament.getParticipants()) stockage.addParticipantInTournament(p, tournament.getTournamentCode());
    }

    public void updateTournament(int tournamentCode, String label, Timestamp startDate, Timestamp endDate, int maxParticipants) {
        Tournament tournament = stockage.getByCode(tournamentCode);
        tournament.setLabel(label);
        tournament.setStartDate(startDate);
        tournament.setEndDate(endDate);
        tournament.setMaxParticipants(maxParticipants);
        stockage.update(tournament);
    }

    public void deleteTournament(int tournamentCode) {
        stockage.delete(tournamentCode);
    }

    public List<Tournament> getAllTournaments() {
        return stockage.getAll();
    }

    public Tournament getByCode(int tournamentCode) {
        return stockage.getByCode(tournamentCode);
    }

    /**
     * Returns all tournaments which endDate is after the date in parameter.
     * @param date
     */
    public List<Tournament> getByDate(Timestamp date) {
        return stockage.getTournamentsByDate(date);
    }

    public List<Jeu> getTournamentGames(int tournamentCode) {
        return stockage.getGamesByTournament(tournamentCode);
    }

    public List<AuthPlayer> getTournamentParticipants(int tournamentCode) {
        return stockage.getParticipantsByTournament(tournamentCode);
    }

    public void addParticipant(AuthPlayer p, int tournamentCode) {
        stockage.addParticipantInTournament(p, tournamentCode);
    }
}
