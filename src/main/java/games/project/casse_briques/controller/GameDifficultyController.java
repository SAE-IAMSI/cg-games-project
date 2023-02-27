package games.project.casse_briques.controller;

public class GameDifficultyController {
    private final BrickBreakerController brickBreakerController;

    public GameDifficultyController(BrickBreakerController brickBreakerController) {
        this.brickBreakerController = brickBreakerController;
    }

    public void setEasy() {
        brickBreakerController.getBall().setMinSpeedX(2);
        brickBreakerController.getBall().setSpeedX(2);
        brickBreakerController.getBall().setMinSpeedY(2);
        brickBreakerController.getBall().setSpeedY(2);
        brickBreakerController.getRacket().setMinRacketSize(225);
        brickBreakerController.getRacket().setRacketSize(225);
        brickBreakerController.getRacket().racketResetPosition();
    }

    public void setMedium() {
        brickBreakerController.getBall().setMinSpeedX(3);
        brickBreakerController.getBall().setSpeedX(3);
        brickBreakerController.getBall().setMinSpeedY(3);
        brickBreakerController.getBall().setSpeedY(3);
        brickBreakerController.getRacket().setMinRacketSize(125);
        brickBreakerController.getRacket().setRacketSize(125);
        brickBreakerController.getRacket().racketResetPosition();
    }

    public void setHard() {
        brickBreakerController.getBall().setMinSpeedX(5);
        brickBreakerController.getBall().setSpeedX(5);
        brickBreakerController.getBall().setMinSpeedY(5);
        brickBreakerController.getBall().setSpeedY(5);
        brickBreakerController.getRacket().setMinRacketSize(75);
        brickBreakerController.getRacket().setRacketSize(75);
        brickBreakerController.getRacket().racketResetPosition();
    }
}
