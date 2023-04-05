package games.project.space_invader;

public class SpaceInvadersClassic extends SpaceInvaders {

    /**
     * Constructeur de la classe SpaceInvadersClassic
     */
    public SpaceInvadersClassic() {
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
