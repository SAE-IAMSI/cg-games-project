package games.project.motron;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class Jeu extends BorderPane { // jeu deviens un borderpane pour être dans la fenêtre principale

    Image tronTitle = new Image("file:src/main/resources/images/MotronTitle.png", 160, 50, false, true);
    ImageView tronTitleView = new ImageView(tronTitle);

    private Pane pane;
    private Pane fen;

    private Arene arene = new Arene();
    private Moto j1;
    private Moto j2;

    private ArrayList<Moto> motoList = new ArrayList<Moto>();


    private ArrayList<Rectangle> trailList = new ArrayList<Rectangle>();

    private GameOver gameOver;
    private ArrayList<KeyCode> keyList;
    private Label timer = new Label("Encore dans le menu");

    private Moto motoMorte;

    private boolean animationMort = false;
    Label scoreJ1;
    Label scoreJ2;

    public static Font policeTRONmini = Font.loadFont("file:src/main/resources/font/Megatron.otf", 20);
    public static Font policeTRON = Font.loadFont("file:src/main/resources/font/Megatron.otf", 35);
    public static Font policeTRONpetit = Font.loadFont("file:src/main/resources/font/Megatron.otf", 15);
    public static Font policeDecompte = Font.loadFont("file:src/main/resources/font/Megatron.otf", 400);
    public static Font policeScore = Font.loadFont("file:src/main/resources/font/Megatron.otf", 20);
    public static Font policeTITRE = Font.loadFont("file:src/main/resources/font/Megatron.otf", 70);

    // Getters & setters-----------------------------


    public Moto getMotoMorte() {
        return motoMorte;
    }

    public void setMotoMorte(Moto motoMorte) {
        this.motoMorte = motoMorte;
    }

    public GameOver getGameOver() {
        return gameOver;
    }

    public void setGameOver(GameOver gameOver) {
        this.gameOver = gameOver;
    }

    public Moto getJ1() {
        return j1;
    }

    public Moto getJ2() {
        return j2;
    }

    public Pane getPane() {
        return pane;
    }

    public ArrayList<Rectangle> getTrailList() {
        return trailList;
    }

    public ArrayList<Moto> getMotoList() {
        return motoList;
    }

    public void setMotoList(ArrayList<Moto> motoList) {
        this.motoList = motoList;
    }

    public ArrayList<KeyCode> getKeyList() {
        return keyList;
    }

    public void setKeyList(KeyCode k, int n) {
        this.keyList.set(n, k);
    }

    public Arene getArene() {
        return arene;
    }

    public Label getTimer() {
        return timer;
    }

    public void setTimer(Label timer) {
        this.timer = timer;
    }

    public boolean isAnimationMort() {
        return animationMort;
    }

    public void setAnimationMort(boolean animatioMort) {
        this.animationMort = animatioMort;
    }

    public void setScore(Moto joueur, int score){
        String nbZero = "";
        for(int i=0; i < 6-String.valueOf(score).length(); i++){
            nbZero += '0';
        }
        if(joueur.getMotoName() == "J1"){
            scoreJ1.setText(nbZero + score);
        }
        else{
            scoreJ2.setText(nbZero + score);
        }
    }

    public void resetScore(){
        scoreJ1.setText("000000");
        scoreJ2.setText("000000");
    }

    // ----------------------------------------------


    public Jeu(Moto j1, Moto j2, Arene arene, ArrayList<KeyCode> keyList){
        this.j1 = j1;
        this.j1.setMotoX((arene.getMapWidth() + 120) - arene.getMapWidth());
        this.j2 = j2;
        this.j2.setMotoX(arene.getMapWidth() - 140);
        this.j1.setGameOver(false);
        this.j2.setGameOver(false);
        this.arene = arene;
        this.keyList = keyList;
        this.scoreJ1 = new Label("000000");
        this.scoreJ2 = new Label("000000");
        scoreJ1.setFont(new Font(100));
        scoreJ1.setFont(Jeu.policeScore);
        scoreJ1.setStyle("-fx-text-fill: white; \n" + "-fx-stroke: black");
        scoreJ1.setAlignment(Pos.CENTER);
        scoreJ2.setFont(new Font(100));
        scoreJ2.setFont(Jeu.policeScore);
        scoreJ2.setStyle("-fx-text-fill: white; \n" + "-fx-stroke: black");
        scoreJ2.setAlignment(Pos.CENTER);
    }


    public void decompte(){
        HBox time = new HBox();
        time.setMinSize(arene.getMapWidth(), arene.getMapHeight());
        timer.setFont(new Font(100));
        timer.setFont(Jeu.policeDecompte);
        timer.setStyle("-fx-text-fill: white; \n" + "-fx-stroke: black");
        time.setAlignment(Pos.CENTER);
        time.getChildren().add(timer);
        timer.setPadding(new Insets(-50,0,80,0));
        this.getPane().getChildren().add(time);

        String[] s = {"3", "2", "1", "Go!"};
        Thread t = new Thread(() -> {
            try {
                for (int i = 0; i < 4; i++) {
                    String text = "" + s[i];
                    Platform.runLater(() -> timer.setText(text));
                    if(i != 3) {
                        Thread.sleep(500);
                    }
                    Thread.sleep(500);
                }
                Platform.runLater(() -> timer.setText(""));
            } catch (InterruptedException exc) {
                exc.printStackTrace();
            }
        });
        t.setDaemon(true); // thread will not stop application exit
        t.start();
    }

    public void test(){
        System.currentTimeMillis();
    }

    public void animationMort(Moto m){
        System.out.println("animation mort lancé: " + animationMort);
        animationMort = true;
        System.out.println("animation mort activé : " + animationMort);

        ImageView imageView = new ImageView(new Image("file:src/main/resources/images/GIF/gifboom.gif"));
        imageView.setScaleX(0.5);
        imageView.setScaleY(0.5);
        imageView.setY(m.getMotoY()-180);
        imageView.setX(m.getMotoX()-180);
        this.pane.getChildren().add(imageView);

        Thread t = new Thread(() -> {
            try {
                Thread.sleep(1000);
                Platform.runLater(() -> {
                    animationMort = false;
                   // this.pane.getChildren().remove(imageView);
                    System.out.println("fini  " + animationMort );});

            } catch (InterruptedException exc) {
                exc.printStackTrace();
            }
        });

        t.setDaemon(true);
        t.start();

    }


    // ajoute les murs au pane du Jeu
    public void setMurs() {
        for (int i = 0; i < arene.getMurs().getMurList().size(); i++) {
            pane.getChildren().add(arene.getMurs().getMurList().get(i));
        }
    }

    //ajoute les lignes à l'arène
    private void setLines() {
        for (int i= 0; i < arene.getLineList().size(); i++) {
            pane.getChildren().add(arene.getLineList().get(i));
        }
    }

    public void jouer(){
        j1.setMotoX(120);
        j2.setMotoX(arene.getMapWidth() - 140);
        j1.setMotoY(400);
        j2.setMotoY(400);
        trailList.clear();
        pane = new Pane();
        pane.setStyle("-fx-background-color : black");
        j1.makeMoto(this);
        j2.makeMoto(this);
        pane.getChildren().add(j1.getSprite().getSkinMotoView());
        pane.getChildren().add(j2.getSprite().getSkinMotoView());
        fen = new Pane();
        tronTitleView.setX(arene.getMapWidth()/2-160/2);
        tronTitleView.setY(arene.getMapHeight()/2-50);
//        pane.getChildren().add(tronTitleView);
        setLines();
        setMurs();
        VBox infosJ1 = new VBox(100);
        infosJ1.setStyle("-fx-background-color : black");
        VBox infosJ2 = new VBox(100);
        infosJ2.setStyle("-fx-background-color : black");
        for(int i=0; i<3; i++){
            infosJ1.getChildren().add(new ImageView(j1.getVies().get(i)));
            infosJ2.getChildren().add(new ImageView(j2.getVies().get(i)));
        }
        infosJ1.getChildren().add(scoreJ1);
        infosJ2.getChildren().add(scoreJ2);
        infosJ1.setMaxWidth(80);
        infosJ2.setMaxWidth(80);
        infosJ2.setMinWidth(80);
        infosJ1.setMinWidth(80);
        //  Rappel Touches ---------------------------
        GridPane rappelj1 = new GridPane();
        rappelj1.setStyle("-fx-background-color : black");
        rappelj1.setAlignment(Pos.CENTER);
        rappelj1.setHgap(20);
        rappelj1.setVgap(20);
        rappelj1.setPadding(new Insets(0, 20, 0, 0));

        GridPane rappelj2 = new GridPane();
        rappelj2.setStyle("-fx-background-color : black");
        rappelj2.setAlignment(Pos.CENTER);
        rappelj2.setHgap(20);
        rappelj2.setVgap(20);
        rappelj2.setPadding(new Insets(0, 0, 0, 20));

        for (int i =0; i < 4; i++){
            Label temp = new Label(keyList.get(i).getChar());
            temp.setAlignment(Pos.CENTER);
            temp.setFont(Jeu.policeScore);
            temp.setTextFill(Color.LIGHTBLUE);
            temp.setBorder(new Border(new BorderStroke(Color.LIGHTBLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            temp.setMinSize(50, 50);

            Label temp2 = new Label(keyList.get(i+5).getName());
            temp2.setAlignment(Pos.CENTER);
            temp2.setFont(Jeu.policeScore);
            temp2.setTextFill(Color.YELLOW);
            temp2.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            temp2.setMinSize(50, 50);

            if (i == 0){
                rappelj1.add(temp, 1, 0);
                rappelj2.add(temp2, 1, 0);
            }
            else {
                rappelj1.add(temp, i-1, 1);
                rappelj2.add(temp2, i-1, 1);
            }
        }
        HBox h_box = new HBox(250);
        h_box.setPadding(new Insets(0, 5, 0, 5));
        HBox hbox = new HBox(rappelj1, infosJ1, h_box, pane, infosJ2, rappelj2);
        fen = new Pane(hbox);
        this.setCenter(fen);
    }


    public void setTouche(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == keyList.get(0)) {
                    //System.out.println("Z");
                    if (j1.getMovingAt() != Moto.Dir.DOWN) {
                        j1.setNewDir(Moto.Dir.UP);
                    }
                } else if (event.getCode() == keyList.get(1)) {
                    //System.out.println("Q");
                    if (j1.getMovingAt() != Moto.Dir.RIGHT) {
                        j1.setNewDir(Moto.Dir.LEFT);
                    }
                } else if (event.getCode() == keyList.get(2)) {
                    //System.out.println("S");
                    if (j1.getMovingAt() != Moto.Dir.UP) {
                        j1.setNewDir(Moto.Dir.DOWN);
                    }
                } else if (event.getCode() == keyList.get(3)) {
                    //System.out.println("D");
                    if (j1.getMovingAt() != Moto.Dir.LEFT) {
                        j1.setNewDir(Moto.Dir.RIGHT);
                    }
                } else if (event.getCode() == keyList.get(4)) {
                    //System.out.println("F");
                    if (j1.isTrail()) {
                        j1.setTrail(false);
                    } else {
                        j1.setTrail(true);
                    }
                } else if (event.getCode() == keyList.get(5)) {
                    //System.out.println("UP");
                    if (j2.getMovingAt() != Moto.Dir.DOWN) {
                        j2.setNewDir(Moto.Dir.UP);
                    }
//                        j2.setNbKey(j2.getNbKey()+1);
//                    }
                } else if (event.getCode() == keyList.get(6)) {
                    //System.out.println("LEFT");
                    if (j2.getMovingAt() != Moto.Dir.RIGHT) {
                        j2.setNewDir(Moto.Dir.LEFT);
                    }
                } else if (event.getCode() == keyList.get(7)) {
                    //System.out.println("DOWN");
                    if (j2.getMovingAt() != Moto.Dir.UP) {
                        j2.setNewDir(Moto.Dir.DOWN);
                    }
                } else if (event.getCode() == keyList.get(8)) {
                    //System.out.println("RIGHT");
                    if (j2.getMovingAt() != Moto.Dir.LEFT) {
                        j2.setNewDir(Moto.Dir.RIGHT);
                    }
                } else if (event.getCode() == keyList.get(9)) {
                    //System.out.println("N");
                    if (j2.isTrail()) {
                        j2.setTrail(false);
                    } else {
                        j2.setTrail(true);
                    }
                } else if (event.getCode() == keyList.get(10)) {
                    //System.out.println("G");
                    if (j1.getMoto().getOpacity() == 0 || j2.getMoto().getOpacity() == 0){
                        j1.getMoto().setOpacity(1);
                        j2.getMoto().setOpacity(1);
                    }
                    else
                    {
                        j1.getMoto().setOpacity(0);
                        j2.getMoto().setOpacity(0);
                    }
                } else if (event.getCode() == keyList.get(11)) {
                    //System.out.println("H");
                    if (j1.getSprite().getSkinMotoView().getOpacity() == 0 || j2.getSprite().getSkinMotoView().getOpacity() == 0) {
                        j1.getSprite().getSkinMotoView().setOpacity(1);
                        j2.getSprite().getSkinMotoView().setOpacity(1);
                    } else {
                        j1.getSprite().getSkinMotoView().setOpacity(0);
                        j2.getSprite().getSkinMotoView().setOpacity(0);
                    }
                }
                event.consume();
            }
        });
    }

}
