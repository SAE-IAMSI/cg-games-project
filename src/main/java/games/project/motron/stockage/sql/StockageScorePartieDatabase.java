package games.project.motron.stockage.sql;

import games.project.motron.metier.entite.ScorePartie;
import games.project.motron.metier.entite.Stat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockageScorePartieDatabase {

    public void create(ScorePartie element) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "INSERT INTO MOTRON VALUES (?,?,?,?,?)";
        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.setInt(1, element.getCodeScore());
            st.setInt(2, element.getKill());
            st.setInt(3, element.getDeath());
            st.setInt(4, element.getNbBlocs());
            st.setString(5, element.getResultat());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(ScorePartie element) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "UPDATE MOTRON SET kill = ?, death = ?, nbBlocs = ?, resultat = ? WHERE codeScore = ?";
        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.setInt(4, element.getKill());
            st.setInt(2, element.getDeath());
            st.setInt(3, element.getNbBlocs());
            st.setString(4, element.getResultat());
            st.setInt(5, element.getCodeScore());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<ScorePartie> getAll() {
        List<ScorePartie> scorePartieList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM MOTRON";
        try (PreparedStatement st = connection.prepareStatement(req)) {
            try (ResultSet result = st.executeQuery()) {
                if (result.next()) {
                    scorePartieList.add(new ScorePartie(result.getInt("codeScore"),
                            result.getInt("kill"),
                            result.getInt("death"),
                            result.getInt("nbBlocs"),
                            result.getString("resultat")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scorePartieList;
    }

    public Stat getStat(String login) {
        Stat stat = null;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT Sum(nbBlocs), Sum(death), Sum(kill) FROM MOTRON m JOIN SCORES s ON m.codeScore=s.codeScore WHERE login = ?";
        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.setString(1, login);
            try (ResultSet result = st.executeQuery()) {
                if (result.next()) {
                    stat = new Stat(result.getInt("Sum(nbBlocs)"), result.getInt("Sum(death)"), result.getInt("Sum(kill)"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stat;
    }

    public int getNbVictoire(String login) {
        int stat = 0;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT Count(resultat) as victoire FROM MOTRON m JOIN SCORES s ON m.codeScore=s.codeScore WHERE login = ? AND resultat = 'V'";
        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.setString(1, login);
            try (ResultSet result = st.executeQuery()) {
                if (result.next()) {
                    stat = result.getInt("victoire");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stat;
    }

    public int getNbDefaite(String login) {
        int stat = 0;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT Count(resultat) as defaite FROM MOTRON m JOIN SCORES s ON m.codeScore=s.codeScore WHERE login = ? AND resultat = 'D'";
        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.setString(1, login);
            try (ResultSet result = st.executeQuery()) {
                if (result.next()) {
                    stat = result.getInt("defaite");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stat;
    }

    public int getNbEgalite(String login) {
        int stat = 0;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT Count(resultat) as egalite FROM MOTRON m JOIN SCORES s ON m.codeScore=s.codeScore WHERE login = ? AND resultat = 'E'";
        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.setString(1, login);
            try (ResultSet result = st.executeQuery()) {
                if (result.next()) {
                    stat = result.getInt("egalite");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stat;
    }
}
