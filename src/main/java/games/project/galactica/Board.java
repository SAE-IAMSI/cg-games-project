package games.project.galactica;

import games.project.galactica.sprite.Alien;
import games.project.galactica.sprite.Player;
import games.project.galactica.sprite.Shot;
import games.project.metier.manager.ScoreManager;
import games.project.stockage.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends JPanel {
    final RightPanel rightPanel;
    final LeftPanel leftPanel;

    private Dimension d;
    private List<Alien> aliens;
    private Player player;
    private Shot shot;

    private int direction = -1;
    private int deaths = 0;

    private boolean inGame = true;
    private final URL explImg = GalacticaClassic.class.getResource("images/explosion2.png");
    private String message = "Game Over";

    private Timer timer, timerLife;
    private Boolean getDommage = true;

    Galactica game;

    private int nbTours = 0;
    private int tirPlayer = 4;
    private int tirAlien = 1;
    private int movePlayer = 2;
    private int moveAlien = 1;

    /**
     * constructeur de la classe Board
     *
     * @param rightPanel panel de droite
     * @param leftPanel panel de gauche
     * @param game instance de SpaceInvaders
     */
    public Board(RightPanel rightPanel, LeftPanel leftPanel, Galactica game) {
        initBoard();
        this.rightPanel = rightPanel;
        this.leftPanel = leftPanel;
        this.game = game;
    }

    /**
     * Initialise le plateau de jeu
     */
    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
        setBackground(Color.black);

        timer = new Timer(Commons.DELAY, new GameCycle());
        timer.start();
        gameInit();
    }

    /**
     * Initialise le jeu
     */
    void gameInit() {
        aliens = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 10; j++) {

                var alien = new Alien(Commons.ALIEN_INIT_X + 18 * j,
                        Commons.ALIEN_INIT_Y + 18 * i);
                aliens.add(alien);
            }
        }

        player = new Player(this);
        shot = new Shot();
    }

    /**
     * Dessine les aliens
     *
     * @param g Graphics
     */
    private void drawAliens(Graphics g) {
        for (Alien alien : aliens) {
            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }
            if (alien.isDying()) {
                alien.die();
            }
        }
    }

    /**
     * Dessine le joueur
     *
     * @param g Graphics
     */
    void drawPlayer(Graphics g) {

        if (player.isVisible()) {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }
        if (player.isDying()) {
            player.die();
            inGame = false;
        }
    }

    /**
     * Dessine le tir
     *
     * @param g Graphics
     */
    private void drawShot(Graphics g) {
        if (shot.isVisible()) {
            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }
    }

    /**
     * Dessine les bombes
     *
     * @param g Graphics
     */
    private void drawBombing(Graphics g) {
        for (Alien a : aliens) {
            Alien.Bomb b = a.getBomb();
            if (!b.isDestroyed()) {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    /**
     * Dessine le plateau de jeu
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    /**
     * Dessine le plateau de jeu
     *
     * @param g Graphics
     */
    private void doDrawing(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.cyan);

        if (inGame) {
            g.drawLine(0, Commons.GROUND, Commons.BOARD_WIDTH, Commons.GROUND);
            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);

        } else {
            if (timer.isRunning()) {
                timer.stop();
            }
            gameOver(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Affiche le message de fin de partie
     *
     * @param g Graphics
     */
    private void gameOver(Graphics g) {
        this.registerScore();
        g.setColor(Color.black);
        g.fillRect(0, 0, Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);

        var small = new Font("Helvetica", Font.BOLD, 14);
        var fontMetrics = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2,
                Commons.BOARD_WIDTH / 2);
        game.dispose();
        var gameOverFrame = new GameOverFrame(this);
        gameOverFrame.setPreferredSize(new Dimension(1280, 720));
        gameOverFrame.setVisible(true);
    }

    /**
     * Met à jour quand le joueur est touché
     */
    private void updateDommage() {
        getDommage = true;
        timerLife.stop();
    }

    /**
     * Met à jour le plateau de jeu
     */
    void update() {
        if (deaths == Commons.NUMBER_OF_ALIENS_TO_DESTROY) {
            if (game instanceof GalacticaInfinite) {
                aliens.clear();
                player = null;
                shot = null;
                deaths = 0;
                removeKeyListener(this.getKeyListeners()[0]);
                nbTours ++;
                if (nbTours%2 == 0) {
                    tirAlien++;
                    tirPlayer += 4;
                    moveAlien++;
                    movePlayer += 2;
                }
                initBoard();
            } else if (game instanceof GalacticaClassic) {
                inGame = false;
                message = "Game won!";
            }
            timer.stop();
        }

        // player
        player.act();

        // shot
        if (shot.isVisible()) {
            int shotX = shot.getX();
            int shotY = shot.getY();

            for (Alien alien : aliens) {
                int alienX = alien.getX();
                int alienY = alien.getY();
                if (alien.isVisible() && shot.isVisible()) {
                    if (shotX >= (alienX)
                            && shotX <= (alienX + Commons.ALIEN_WIDTH)
                            && shotY >= (alienY)
                            && shotY <= (alienY + Commons.ALIEN_HEIGHT)) {
                        var ii = new ImageIcon(explImg);
                        alien.setImage(ii.getImage());
                        alien.setDying(true);
                        deaths++;
                        shot.die();
                        rightPanel.setScore(rightPanel.getScore() + 20);
                        rightPanel.updateScore();
                    }
                }
            }

            int y = shot.getY();
            y -= tirPlayer;
            if (y < 0) {
                shot.die();
            } else {
                shot.setY(y);
            }
        }

        // aliens

        for (Alien alien : aliens) {
            int x = alien.getX();
            if (x >= Commons.BOARD_WIDTH - Commons.BORDER_RIGHT && direction != -1) {
                direction = -moveAlien;
                for (Alien a2 : aliens) {
                    a2.setY(a2.getY() + Commons.GO_DOWN);
                }
            }

            if (x <= Commons.BORDER_LEFT && direction != 1) {
                direction = moveAlien;
                for (Alien a : aliens) {
                    a.setY(a.getY() + Commons.GO_DOWN);
                }
            }
        }

        for (Alien alien : aliens) {
            if (alien.isVisible()) {
                int y = alien.getY();
                if (y > Commons.GROUND - Commons.ALIEN_HEIGHT) {
                    inGame = false;
                    message = "Invasion!";
                }
                alien.act(direction);
            }
        }

        // bombs
        var generator = new Random();
        for (Alien alien : aliens) {
            int shot = generator.nextInt(15);
            Alien.Bomb bomb = alien.getBomb();
            if (shot == Commons.CHANCE && alien.isVisible() && bomb.isDestroyed()) {
                bomb.setDestroyed(false);
                bomb.setX(alien.getX());
                bomb.setY(alien.getY());
            }

            int bombX = bomb.getX();
            int bombY = bomb.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !bomb.isDestroyed()) {
                if (bombX >= (playerX)
                        && bombX <= (playerX + Commons.PLAYER_WIDTH)
                        && bombY >= (playerY)
                        && bombY <= (playerY + Commons.PLAYER_HEIGHT) && getDommage) {
                    getDommage = false;
                    timerLife = new Timer(5000, e -> updateDommage());
                    timerLife.start();
                    leftPanel.setLives(leftPanel.getLives() - 1);
                    leftPanel.updateLives();

                    if (leftPanel.getLives() == 0) {
                        var ii = new ImageIcon(explImg);
                        player.setImage(ii.getImage());
                        player.setDying(true);
                        bomb.setDestroyed(true);
                    }
                }
            }

            if (!bomb.isDestroyed()) {
                bomb.setY(bomb.getY() + tirAlien);
                if (bomb.getY() >= Commons.GROUND - Commons.BOMB_HEIGHT) {
                    bomb.setDestroyed(true);
                }
            }
        }
    }

    /**
     * Dessine les éléments du jeu
     */
    private void doGameCycle() {
        update();
        repaint();
    }


    /**
     * Classe interne pour le timer
     */
    private class GameCycle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }

    public int getMovePlayer() {
        return movePlayer;
    }

    /**
     * Classe interne pour les touches du clavier
     */
    private class TAdapter extends KeyAdapter {

        /**
         * Appelé quand une touche est relachée
         *
         * @param e the event to be processed
         */
        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

        /**
         * Appelé quand une touche est pressée
         *
         * @param e the event to be processed
         */
        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
            int x = player.getX();
            int y = player.getY();
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE) {
                if (inGame) {
                    if (!shot.isVisible()) {
                        shot = new Shot(x, y);
                    }
                }
            }
        }
    }
    public List<Alien> getAliens() {
        return aliens;
    }

    /**
     * Enregistre le score dans la BD
     **/
    private void registerScore() {
        ScoreManager sm = ScoreManager.getInstance();
        if (Session.getInstance().isConnected()) {
            sm.createScore(rightPanel.getScore(), Session.getInstance().getLogin(), "GALAC");
        } else {
            sm.createScore(rightPanel.getScore(), "", "GALAC");
        }

    }
}
