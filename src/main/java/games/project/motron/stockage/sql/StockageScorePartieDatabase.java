package games.project.motron.stockage.sql;

import games.project.motron.metier.entite.ScorePartie;
import games.project.motron.metier.entite.Stat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockageScorePartieDatabase implements Stockage<ScorePartie>{

    @Override
    public void create(ScorePartie element) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "INSERT INTO SCORES_TRON (idPartie, login, score, kill, death, nbBlocs, resultat) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.setInt(1, element.getId());
            st.setString(2, element.getLogin());
            st.setInt(3, element.getScore());
            st.setInt(4, element.getKill());
            st.setInt(5, element.getDeath());
            st.setInt(6, element.getNbBlocs());
            st.setString(7, element.getResultat());
            st.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ScorePartie element) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "UPDATE SCORES_TRON SET score = ?, kill = ?, death = ?, nbBlocs = ?, resultat = ? WHERE idPartie = ? AND login = ?";
        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.setInt(1, element.getScore());
            st.setInt(2, element.getKill());
            st.setInt(3, element.getDeath());
            st.setInt(4, element.getNbBlocs());
            st.setString(5, element.getResultat());
            st.setInt(6, element.getId());
            st.setString(7, element.getLogin());
            st.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int LastId(){
        int i = 0;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT MAX(idPartie) FROM SCORES_TRON";
        try(PreparedStatement st = connection.prepareStatement(req)) {
            try (ResultSet result = st.executeQuery();) {
                if (result.next()) {
                    i = result.getInt("MAX(idPartie)");
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public ScorePartie getByLogin(String login) {
        ScorePartie scorePartie = null;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM SCORES_TRON WHERE login = ?";
        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.setString(1, login);
            try (ResultSet result = st.executeQuery()) {
                if (result.next()) {
                    scorePartie = new ScorePartie(  result.getInt("idPartie"),
                                                    login,
                                                    result.getInt("score"),
                                                    result.getInt("kill"),
                                                    result.getInt("death"),
                                                    result.getInt("nbBlocs"),
                                                    result.getString("resultat"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scorePartie;
    }

    @Override
    public List<ScorePartie> getAll() {
        List<ScorePartie> scorePartieList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM SCORES_TRON";
        try (PreparedStatement st = connection.prepareStatement(req)) {
            try (ResultSet result = st.executeQuery()) {
                if (result.next()) {
                    scorePartieList.add(new ScorePartie(result.getInt("idPartie"),
                                                        result.getString("login"),
                                                        result.getInt("score"),
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
        String req = "SELECT Sum(nbBlocs), Sum(death), Sum(kill) FROM SCORES_TRON WHERE login = ?";
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
        String req = "SELECT Count(resultat) as victoire FROM SCORES_TRON WHERE login = ? AND resultat = 'V'";
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
        String req = "SELECT Count(resultat) as defaite FROM SCORES_TRON WHERE login = ? AND resultat = 'D'";
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
        String req = "SELECT Count(resultat) as egalite FROM SCORES_TRON WHERE login = ? AND resultat = 'E'";
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
