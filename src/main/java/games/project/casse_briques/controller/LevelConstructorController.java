package games.project.casse_briques.controller;

import games.project.casse_briques.model.Brick;

import java.util.Random;

public class LevelConstructorController {
    private final BrickBreakerController brickBreakerController;

    public LevelConstructorController(BrickBreakerController brickBreakerController) {
        this.brickBreakerController = brickBreakerController;
    }

    /** Permet de construire un niveau à partir d'une matrice                  **/
    /** La première brique est positionner le plus à gauche de l'espace de jeu **/
    /** Les briques sont positionnées de gauche à droite                       **/
    /** Ne pas dépasser (largeur)y=10 ou les briques dépasseront du niveau en largeur   **/
    /**
     * (hauteur)i=20 correspond à la hauteur de la raquette
     **/

    private void setupBricks(int[][] bricks, int life) {
        for (int i = 0; i < bricks.length; i++) { // hauteur
            for (int y = 0; y < bricks[0].length; y++) { // largeur
                if (bricks[i][y] == 1) {

                    Brick brick = new Brick(60, 25, life);

                    brick.setLayoutX(brickBreakerController.getPrefWidth() * 0.23 + y * (brick.getWidth() + 10));
                    brick.setLayoutY(brickBreakerController.getPrefHeight() * 0.05 + (brick.getHeight() + 10) * i);

                    brick.deleteBrick(brickBreakerController);
                    brickBreakerController.getBrickMemoryAdd().add(brick);
                    brickBreakerController.getChildren().add(brick);
                }
            }
        }
    }


    public void initBrickLevel1() {
        clearBricks();

        int[][] lv1 = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
        setupBricks(lv1, 1);
    }

    public void initBrickLevel2() {
        clearBricks();
        int[][] a = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {0, 1, 1, 1, 1, 1, 1, 1, 1, 0}, {0, 0, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 1, 1, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 0, 0, 0, 0}};
        setupBricks(a, 3);

        //Dernière brique
        Brick brick = new Brick(60, 25, 3);
        brick.setLayoutX(brickBreakerController.getPrefWidth() * 0.23 + 4.5 * (brick.getWidth() + 10));
        brick.setLayoutY(brickBreakerController.getPrefHeight() * 0.05 + (brick.getHeight() + 10) * 5);
        brick.deleteBrick(brickBreakerController);
        brickBreakerController.getBrickMemoryAdd().add(brick);
        brickBreakerController.getChildren().add(brick);
    }

    public void initBrickLevel3() {
        clearBricks();
        int[][] a = {{1, 1, 1, 1, 0, 0, 1, 1, 1, 1},
                {1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
                {1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
                {1, 1, 1, 1, 0, 0, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 0, 0, 0, 0, 1, 1, 1},
                {1, 1, 1, 1, 0, 0, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
        setupBricks(a, 4);
    }


    public void initBrickLevel4() {
        clearBricks();
        int[][] a = {
                {0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 1, 1, 0, 0, 0, 0, 1, 1, 0},
                {0, 1, 1, 1, 0, 0, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 1, 1, 0, 1, 1, 0, 1, 1, 1},
                {1, 1, 1, 0, 1, 1, 0, 1, 1, 1},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 0, 0}};
        setupBricks(a, 3);
    }

    public void initBrickLevel5() {
        clearBricks();
        int[][] a = {
                {0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 0, 1, 1, 0, 1, 0, 0},
                {0, 0, 1, 0, 1, 1, 0, 1, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 1, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
                {0, 0, 1, 1, 0, 0, 1, 1, 0, 0}
        };
        setupBricks(a, 2);
    }

    public void initBrickLevel6() {
        clearBricks();
        int[][] a = {
                {0, 1, 1, 0, 0, 0, 0, 1, 1, 0},
                {1, 1, 1, 1, 0, 0, 1, 1, 1, 1},
                {1, 1, 1, 1, 0, 0, 1, 1, 1, 1},
                {1, 1, 1, 1, 0, 0, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 1, 1, 1, 1, 0, 0, 0}
        };
        setupBricks(a, 4);
    }

    public void initBrickLevelRandom() {
        clearBricks();
        int[][] a = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int y = 0; y < 10; y++) {
                int r = new Random().nextInt(2);
                a[i][y] = r;
            }
        }
        int d = new Random().nextInt(4);
        if (d == 0) {
            d++;
        }
        setupBricks(a, d);
    }

    public void brickHitboxTestLevel() {
        clearBricks();
        Brick brick = new Brick(500, 500, 4);
        brick.setLayoutY(brickBreakerController.getPrefHeight() * 0.5 - brick.getHeight() * 0.5);
        brick.setLayoutX(brickBreakerController.getPrefWidth() * 0.5 - brick.getWidth() * 0.5);

        brick.deleteBrick(brickBreakerController);

        brickBreakerController.getBrickMemoryAdd().add(brick);
        brickBreakerController.getChildren().add(brick);
    }

    public void clearBricks() {
        brickBreakerController.getChildren().removeAll(brickBreakerController.getBrickMemoryAdd());
        brickBreakerController.getBrickMemoryAdd().clear();
        brickBreakerController.getBrickMemoryRemove().clear();
    }
}
