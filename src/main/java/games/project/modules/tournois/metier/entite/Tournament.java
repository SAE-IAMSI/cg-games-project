package games.project.modules.tournois.metier.entite;

import games.project.metier.entite.AuthPlayer;
import games.project.metier.entite.Jeu;
import games.project.metier.entite.Score;
import games.project.metier.manager.PlayerManager;
import games.project.modules.tournois.metier.manager.TournamentManager;

import java.sql.Timestamp;
import java.util.*;

public class Tournament {
    private int tournamentCode;

    private String label;

    private Timestamp startDate;
    private Timestamp endDate;

    private int maxParticipants;

    private List<AuthPlayer> participants;

    private List<Jeu> games;

    public Tournament(String label, Timestamp startDate, Timestamp endDate, int maxParticipants) {
        this.label = label;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxParticipants = maxParticipants;
        this.games = new ArrayList<>();
        this.participants = new ArrayList<>();
    }

    public int getTournamentCode() {
        return tournamentCode;
    }

    public void setTournamentCode(int tournamentCode) {
        this.tournamentCode = tournamentCode;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public List<AuthPlayer> getParticipants() {
        return participants;
    }

    public void setParticipants(List<AuthPlayer> participants) {
        this.participants = participants;
    }

    public List<Jeu> getGames() {
        return games;
    }

    public void setGames(List<Jeu> games) {
        this.games = games;
    }

    public void registerPlayer(AuthPlayer player) {
        participants.add(player);
    }

    /**
     * Renvoie une map représentant le classement du jeu passé en paramètre contenant une ligne de score
     * et le nombre de points associé à cette chaque ligne.
     * @param game Le jeu dont le classement sera renvoyé.
     * @return Une map représentant le classement d'un jeu du tournoi.
     */
    public Map<Score, Integer> getGameLeaderboard(Jeu game) {
       Map<Score, Integer> leaderboard = new HashMap<>();
       List<Score> scores = TournamentManager.getInstance().getLeaderboardByGame(game, tournamentCode);
       for (int i = 0; i < scores.size(); i++) {
           leaderboard.put(scores.get(i), getPoints(i));
       }
       return leaderboard;
    }

    /**
     * Renvoie le classement général d'un tournoi à partir des classements de chaque jeu du tournoi.
     * @return Une map représentant le classement général du tournoi.
     */
    public Map<AuthPlayer, Integer> getMainLeaderboard() {
        Map<AuthPlayer, Integer> mainLeaderboard = new HashMap<>();
        for (Jeu game : games) {
            Map<Score, Integer> leaderboard = getGameLeaderboard(game);
            for (Score s : leaderboard.keySet()) {
                AuthPlayer p = PlayerManager.getInstance().getPlayer(s.getLogin());
                mainLeaderboard.put(p, mainLeaderboard.getOrDefault(p, 0) + leaderboard.get(s));
            }
        }
        return mainLeaderboard;
    }

    /**
     * Renvoie le nombre de points que chaque joueur gagne selon sa position dans le classement.
     * @param position La position du joueur dans le classement, comprise entre 0 (1er) et maxParticipants - 1 (dernier)
     * @return Le nombre de points d'un joueur
     */
    private int getPoints(int position) {
        return maxParticipants - position;
    }
}
