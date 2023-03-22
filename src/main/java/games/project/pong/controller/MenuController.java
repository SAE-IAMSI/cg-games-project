package games.project.pong.controller;


import games.project.pong.view.StartMenuView;

public class MenuController {


    public static void pauseMenu(){
        //GameController.getInstance().displayScreen(new )
    }
    public static void startMenu(){
        GameController.getInstance().displayScreen(new StartMenuView());
    }
}
