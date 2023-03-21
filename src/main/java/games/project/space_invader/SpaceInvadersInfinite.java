package games.project.space_invader;

public class SpaceInvadersInfinite extends SpaceInvaders {
    public SpaceInvadersInfinite() {
        super();
    }

    @Override
    public void setLives() {
        leftPanel.setLives(3);
    }
}
