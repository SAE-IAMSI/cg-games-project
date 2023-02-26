package games.project.casse_briques.controller;

import games.project.casse_briques.view.*;

public class GameMenuController {
    private final BrickBreakerController brickBreakerController;
    private static ViewMenuStart viewMenuStart;

    public GameMenuController(BrickBreakerController brickBreakerController) {
        this.brickBreakerController = brickBreakerController;


    }

    /**
     * Génère le menu de démarrage du jeu
     **/
    public void startMenu() {
        viewMenuStart = new ViewMenuStart(brickBreakerController);
        brickBreakerController.getChildren().add(viewMenuStart);
    }

    /**
     * Génère la sélection des niveaux
     **/
    public void levelSelectMenu() {
        brickBreakerController.getChildren().add(new ViewSelectLevel(brickBreakerController));
    }

    public void levelSelectMenu2() {
        brickBreakerController.getChildren().add(new ViewSelectLevel2(brickBreakerController));
    }

    /**
     * Génère le tableau des scores
     **/
    public void leaderBoard() {
        brickBreakerController.getChildren().add(new ViewLeaderBoard(brickBreakerController));
    }

    /**
     * Génère le tableau des scores du joueur connecté
     **/
    public void playerLeaderBoard() {
        ViewPlayerLeaderBoard viewPlayerLeaderBoard = new ViewPlayerLeaderBoard(brickBreakerController);
        viewPlayerLeaderBoard.setLayoutX(brickBreakerController.getPrefWidth() * 0.5 - viewPlayerLeaderBoard.getPrefWidth() * 0.5);
        viewPlayerLeaderBoard.setLayoutY(brickBreakerController.getPrefHeight() * 0.5 - viewPlayerLeaderBoard.getPrefHeight() * 0.5);
        brickBreakerController.getChildren().add(viewPlayerLeaderBoard);
    }

    /**
     * Génère l'affichage de création de compte
     **/
    public void registerPlayer() {
        ViewRegisterPlayer viewRegisterPlayer = new ViewRegisterPlayer(brickBreakerController);
        viewRegisterPlayer.setLayoutX(brickBreakerController.getPrefWidth() * 0.5 - viewRegisterPlayer.getPrefWidth() * 0.5);
        viewRegisterPlayer.setLayoutY(brickBreakerController.getPrefHeight() * 0.5 - viewRegisterPlayer.getPrefHeight() * 0.5);
        brickBreakerController.getChildren().add(viewRegisterPlayer);
    }

    /**
     * Génère l'affichage de connexion
     **/
    public void connectPlayer() {
        ViewConnectPlayer viewConnectPlayer = new ViewConnectPlayer(brickBreakerController);
        viewConnectPlayer.setLayoutX(brickBreakerController.getPrefWidth() * 0.5 - viewConnectPlayer.getPrefWidth() * 0.5);
        viewConnectPlayer.setLayoutY(brickBreakerController.getPrefHeight() * 0.5 - viewConnectPlayer.getPrefHeight() * 0.5);
        brickBreakerController.getChildren().add(viewConnectPlayer);
    }

    /**
     * Génère l'écran de fin du jeu (IN-GAME)
     **/
    public void endScreen() {
        ViewEndScreen viewEndScreen = new ViewEndScreen(brickBreakerController);
        viewEndScreen.setLayoutX(this.brickBreakerController.getPrefWidth() / 2 - viewEndScreen.getPrefWidth() / 2);
        viewEndScreen.setLayoutY(this.brickBreakerController.getPrefHeight() / 2 - viewEndScreen.getPrefHeight() / 2);
        brickBreakerController.getChildren().add(viewEndScreen);
    }

    /**
     * Génère l'affichage des règles du jeu
     **/
    public void rules() {
        brickBreakerController.getChildren().add(new ViewRules(brickBreakerController));
    }

    public void rules2() {
        brickBreakerController.getChildren().add(new ViewRules2(brickBreakerController));
    }

    public void rules3() {
        brickBreakerController.getChildren().add(new ViewRules3(brickBreakerController));
    }

    /**
     * Génère l'affichage du menu pause du jeu (IN-GAME)
     **/
    public void pauseMenu() {  // Génère le menu Pause du jeu
        ViewPause viewPause = new ViewPause(brickBreakerController);
        viewPause.setLayoutX(brickBreakerController.getPrefWidth() / 2 - viewPause.getPrefWidth() / 2);
        viewPause.setLayoutY(brickBreakerController.getPrefHeight() / 2 - viewPause.getPrefHeight() / 2);
        brickBreakerController.getChildren().add(viewPause);
    }


    /**
     * Génère l'affichage du menu paramètre du jeu (IN-GAME)
     **/
    public void parameterMenu() { //Génère les paramètres du jeu
        ViewParameters viewParameters = new ViewParameters(brickBreakerController);
        viewParameters.setLayoutX(brickBreakerController.getPrefWidth() / 2 - viewParameters.getPrefWidth() / 2);
        viewParameters.setLayoutY(brickBreakerController.getPrefHeight() / 2 - viewParameters.getPrefHeight() / 2);
        brickBreakerController.getChildren().add(viewParameters);
    }

    /**
     * Affiche le profile du joueur
     **/
    public void playerProfile() {
        ViewPlayerProfile viewPlayerProfile = new ViewPlayerProfile(brickBreakerController);
        viewPlayerProfile.setLayoutX(brickBreakerController.getPrefWidth() * 0.5 - viewPlayerProfile.getPrefWidth() * 0.5);
        viewPlayerProfile.setLayoutY(brickBreakerController.getPrefHeight() * 0.5 - viewPlayerProfile.getPrefHeight() * 0.5);
        brickBreakerController.getChildren().add(viewPlayerProfile);
    }

    /**
     * Affiche le formulaire d'édition du joueur
     **/
    public void editPlayerProfile() {
        ViewEditPlayerProfile viewEditPlayerProfile = new ViewEditPlayerProfile(brickBreakerController);
        viewEditPlayerProfile.setLayoutX(brickBreakerController.getPrefWidth() * 0.5 - viewEditPlayerProfile.getPrefWidth() * 0.5);
        viewEditPlayerProfile.setLayoutY(brickBreakerController.getPrefHeight() * 0.5 - viewEditPlayerProfile.getPrefHeight() * 0.5);
        brickBreakerController.getChildren().add(viewEditPlayerProfile);
    }

    public void showBtStartMenuConnected() {
        viewMenuStart.showBtConnected();
    }

    public void showBtStartMenuDisconnected() {
        viewMenuStart.showBtDisconnected();
    }
}
