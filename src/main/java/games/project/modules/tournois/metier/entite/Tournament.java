package games.project.modules.tournois.metier.entite;

import games.project.metier.entite.AuthPlayer;
import games.project.metier.entite.Jeu;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

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

    public Stack<AuthPlayer> getGameLeaderboard(Jeu game) {
        return new Stack<>();
    }

    public Stack<AuthPlayer> getMainLeaderboard() {
        return new Stack<>();
    }
}
