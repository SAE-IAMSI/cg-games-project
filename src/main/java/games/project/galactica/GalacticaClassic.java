package games.project.galactica;

public class GalacticaClassic extends Galactica {

    /**
     * Constructeur de la classe SpaceInvadersClassic
     */
    public GalacticaClassic() {
        super();
    }

    /**
     * Définit le nombre de vies (Classic = 1)
     */
    @Override
    public void setLives() {
        leftPanel.setLives(1);
    }
}
