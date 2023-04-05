package games.project.space_invader;

public class SpaceInvadersInfinite extends SpaceInvaders {

    /**
     * Constructeur de la classe SpaceInvadersClassic
     */
    public SpaceInvadersInfinite() {
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
