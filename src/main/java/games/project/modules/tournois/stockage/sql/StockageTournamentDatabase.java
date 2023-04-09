package games.project.modules.tournois.stockage.sql;

import games.project.metier.entite.AuthPlayer;
import games.project.metier.entite.Jeu;
import games.project.metier.entite.Score;
import games.project.metier.manager.JeuManager;
import games.project.metier.manager.PlayerManager;
import games.project.modules.tournois.metier.entite.Tournament;
import games.project.stockage.sql.SQLUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StockageTournamentDatabase {

    public void create(Tournament tournament) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "INSERT INTO TOURNOIS(libelleTournoi, dateDebut, dateFin, nbParticipantsMax) VALUES (?, ?, ?, ?)";
        try (
                PreparedStatement statement = connection.prepareStatement(req)
        ) {
            statement.setString(1, tournament.getLabel());
            statement.setTimestamp(2, tournament.getStartDate());
            statement.setTimestamp(3, tournament.getEndDate());
            statement.setInt(4, tournament.getMaxParticipants());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Tournament tournament) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "UPDATE TOURNOIS SET libelleTournoi = ?, dateDebut = ?, dateFin = ?, nbParticipantsMax= ? WHERE codeTournoi = ?";
        try (
                PreparedStatement statement = connection.prepareStatement(req)
        ) {
            statement.setInt(5, tournament.getTournamentCode());
            statement.setString(1, tournament.getLabel());
            statement.setTimestamp(2, tournament.getStartDate());
            statement.setTimestamp(3, tournament.getEndDate());
            statement.setInt(4, tournament.getMaxParticipants());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int tournamentCode) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "DELETE FROM TOURNOIS WHERE codeTournoi = ?";
        try (
                PreparedStatement statement = connection.prepareStatement(req)
        ) {
            statement.setInt(1, tournamentCode);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Tournament> getAll() {
        List<Tournament> tournamentList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM TOURNOIS";
        try (
                PreparedStatement statement = connection.prepareStatement(req);
                ResultSet result = statement.executeQuery();
        ) {
            while (result.next()) {
                int tournamentCode = result.getInt("codeTournoi");
                String label = result.getString("libelleTournoi");
                Timestamp startDate = result.getTimestamp("dateDebut");
                Timestamp endDate = result.getTimestamp("dateFin");
                int maxParticipants = result.getInt("nbParticipantsMax");
                Tournament tournament = new Tournament(label, startDate, endDate, maxParticipants);
                tournament.setTournamentCode(tournamentCode);
                tournament.setGames(getGamesByTournament(tournamentCode));
                tournament.setParticipants(getParticipantsByTournament(tournamentCode));
                tournamentList.add(tournament);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tournamentList;
    }

    public Tournament getByCode(int tournamentCode) {
        Tournament tournament = null;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM TOURNOIS WHERE codeTournoi = ?";
        try (
                PreparedStatement statement = connection.prepareStatement(req);
        ) {
            statement.setInt(1, tournamentCode);
            try (
                    ResultSet result = statement.executeQuery();
            ) {
                if (result.next()) {
                    String label = result.getString("libelleTournoi");
                    Timestamp startDate = result.getTimestamp("dateDebut");
                    Timestamp endDate = result.getTimestamp("dateFin");
                    int maxParticipants = result.getInt("nbParticipantsMax");
                    tournament = new Tournament(label, startDate, endDate, maxParticipants);
                    tournament.setTournamentCode(tournamentCode);
                    tournament.setGames(getGamesByTournament(tournamentCode));
                    tournament.setParticipants(getParticipantsByTournament(tournamentCode));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tournament;
    }

    public List<Tournament> getTournamentsByDate(Timestamp date) {
        List<Tournament> tournamentList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM TOURNOIS WHERE dateFin > ?";
        try (
                PreparedStatement statement = connection.prepareStatement(req)
            ) {
            statement.setTimestamp(1, date);
            try (
                    ResultSet result = statement.executeQuery()
                ) {
                while (result.next()) {
                    int tournamentCode = result.getInt("codeTournoi");
                    String label = result.getString("libelleTournoi");
                    Timestamp startDate = result.getTimestamp("dateDebut");
                    Timestamp endDate = result.getTimestamp("dateFin");
                    int maxParticipants = result.getInt("nbParticipantsMax");
                    Tournament tournament = new Tournament(label, startDate, endDate, maxParticipants);
                    tournament.setTournamentCode(tournamentCode);
                    tournament.setGames(getGamesByTournament(tournamentCode));
                    tournament.setParticipants(getParticipantsByTournament(tournamentCode));
                    tournamentList.add(tournament);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tournamentList;
    }

    public List<Tournament> getTournamentByLogin(String login) {
        List<Tournament> tournamentList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT t.codeTournoi, libelleTournoi, dateDebut, dateFin, nbParticipantsMax FROM TOURNOIS t JOIN PARTICIPER p ON t.codeTournoi = p.codeTournoi WHERE login = ?";
        try (
                PreparedStatement statement = connection.prepareStatement(req)
        ) {
            statement.setString(1, login);
            try (
                    ResultSet result = statement.executeQuery()
            ) {
                while (result.next()) {
                    int tournamentCode = result.getInt("codeTournoi");
                    String label = result.getString("libelleTournoi");
                    Timestamp startDate = result.getTimestamp("dateDebut");
                    Timestamp endDate = result.getTimestamp("dateFin");
                    int maxParticipants = result.getInt("nbParticipantsMax");
                    Tournament tournament = new Tournament(label, startDate, endDate, maxParticipants);
                    tournament.setTournamentCode(tournamentCode);
                    tournament.setGames(getGamesByTournament(tournamentCode));
                    tournament.setParticipants(getParticipantsByTournament(tournamentCode));
                    tournamentList.add(tournament);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tournamentList;
    }

    public List<Jeu> getGamesByTournament(int tournamentCode) {
        List<Jeu> gamesList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM COMPOSERTOURNOI WHERE codeTournoi = ?";
        try (
                PreparedStatement statement = connection.prepareStatement(req);
        ) {
            statement.setInt(1, tournamentCode);
            try (
                    ResultSet result = statement.executeQuery();
            ) {
                while (result.next()) {
                    String gameCode = result.getString("codeJeu");
                    Jeu game = JeuManager.getInstance().getJeu(gameCode);
                    gamesList.add(game);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gamesList;
    }

    public List<AuthPlayer> getParticipantsByTournament(int tournamentCode) {
        List<AuthPlayer> participants = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM PARTICIPER WHERE codeTournoi = ?";
        try (
                PreparedStatement statement = connection.prepareStatement(req);
        ) {
            statement.setInt(1, tournamentCode);
            try (
                    ResultSet result = statement.executeQuery();
            ) {
                while (result.next()) {
                    String login = result.getString("login");
                    AuthPlayer player = PlayerManager.getInstance().getPlayer(login);
                    participants.add(player);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participants;
    }

    public void setGamesOnTournament(List<Jeu> games, int tournamentCode) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        for (Jeu jeu : games) {
            String req = "INSERT INTO COMPOSERTOURNOI(codeTournoi, codeJeu) VALUES (?, ?)";
            try (
                    PreparedStatement statement = connection.prepareStatement(req)
            ) {
                statement.setInt(1, tournamentCode);
                statement.setString(2, jeu.getCode());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addParticipantInTournament(AuthPlayer player, int tournamentCode) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "INSERT INTO PARTICIPER(codeTournoi, login) VALUES (?, ?)";
        try (
                PreparedStatement statement = connection.prepareStatement(req)
            ) {
            statement.setInt(1, tournamentCode);
            statement.setString(2, player.getLogin());
            statement.executeUpdate();
    } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Score> getLeaderboardByGame(Jeu game, int tournamentCode) {
        List<Score> leaderboard = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT tmp.hs.codeScore AS codeScore, tmp.hs.score AS score, tmp.hs.horodatage AS horodatage, tmp.hs.login AS login, tmp.hs.codeJeu AS codeJeu\n" +
                "FROM (SELECT HIGHSCOREBYGAMEANDPERIOD(login, ?, codeTournoi) AS hs \n" +
                "      FROM PARTICIPER\n" +
                "      WHERE codeTournoi = ?) tmp\n" +
                "ORDER BY score DESC";
        try (
                PreparedStatement statement = connection.prepareStatement(req)
            ) {
            statement.setString(1, game.getCode());
            statement.setInt(2, tournamentCode);
            try (
                    ResultSet result = statement.executeQuery()
                ) {
                while (result.next()) {
                    int codeScore = result.getInt("codeScore");
                    int score = result.getInt("score");
                    Timestamp horodatage = result.getTimestamp("horodatage");
                    String login = result.getString("login");
                    Score s = new Score(score, horodatage, game.getCode());
                    s.setLogin(login);
                    s.setId(codeScore);
                    leaderboard.add(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaderboard;
    }

    public int getLastTournamentCode() {
        int code = Integer.MAX_VALUE;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT MAX(codeTournoi) AS codeTournoi FROM TOURNOIS";
        try (
                PreparedStatement statement = connection.prepareStatement(req);
                ResultSet result = statement.executeQuery();
            ) {
            if (result.next()) {
                code = result.getInt("codeTournoi");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return code;
    }

}
