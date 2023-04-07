package games.project.galactica;

public class GalacticaInfinite extends Galactica {

    /**
     * Constructeur de la classe SpaceInvadersClassic
     */
    public GalacticaInfinite() {
        super();
    }

    /**
     * Définit le nombre de vies (Infinit = 3)
     */
    @Override
    public void setLives() {
        leftPanel.setLives(3);
    }
}
