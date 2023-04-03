package games.project.pong.view;

import games.project.koala_rock.Stockage.Session;
import games.project.metier.entite.Score;
import games.project.metier.manager.ScoreManager;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PlayerScoreMenuView extends GenericView{
    @FXML
    public Text textScore;

    public PlayerScoreMenuView() {
        super("PlayerScoreMenu.fxml");
        initScore();
    }

    private void initScore(){
        ScoreManager sm = ScoreManager.getInstance();
        List<Score> listScore = sm.getScoresHistoryByLogin(Session.getInstance().getLogin());

        int size = 0;
        if(listScore.size()>=10){size=10;}
        else {
            size = listScore.size();
        }
        for (int i=0;i<size;i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(listScore.get(i).getHorodatage());
            String month = "";
            switch (cal.get(Calendar.MONTH)) {
                case 0:
                    month = "Janvier";
                    break;
                case 1:
                    month = "Fevrier";
                    break;
                case 2:
                    month = "Mars";
                    break;
                case 3:
                    month = "Avril";
                    break;
                case 4:
                    month = "Mai";
                    break;
                case 5:
                    month = "Juin";
                    break;
                case 6:
                    month = "Juillet";
                    break;
                case 7:
                    month = "Aout";
                    break;
                case 8:
                    month = "Septembre";
                    break;
                case 9:
                    month = "Octobre";
                    break;
                case 10:
                    month = "Novembre";
                    break;
                default:
                    month = "Decembre";
                    break;
            }
            String line = (listScore.get(i).getLogin() == null ? "Invite" : listScore.get(i).getLogin()) + " ---> " + listScore.get(i).getScore() + " || " + cal.get(Calendar.DAY_OF_MONTH) + " " + month + " " + cal.get(Calendar.YEAR) + " " + cal.get(Calendar.HOUR_OF_DAY) + "H" + cal.get(Calendar.MINUTE) + " || " + listScore.get(i).getGameCode() + " ||";
            System.out.println(line);
            textScore.setText(textScore.getText() + " \n" + line + "\n");
        }

    }

    @FXML
    private void back(){
        gameController.removeScreen(this);
        gameController.displayScreen(new ScoreMenuView());
    }
}
