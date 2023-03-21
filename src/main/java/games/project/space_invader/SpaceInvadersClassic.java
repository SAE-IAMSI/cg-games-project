package games.project.space_invader;

public class SpaceInvadersClassic extends SpaceInvaders {
    public SpaceInvadersClassic() {
        super();
    }

    @Override
    public void setLives() {
        leftPanel.setLives(1);
    }
}
