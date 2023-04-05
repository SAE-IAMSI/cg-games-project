package games.project.metier.entite;

public class Player {
    String name;
    private Score score;

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, Score score) {
        this(name);
        this.score = score;
    }

    public Score getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score.setScore(score);
    }
}