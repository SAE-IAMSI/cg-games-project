package games.project.space_invader;

import games.project.space_invader.sprite.Alien;
import games.project.space_invader.sprite.Player;
import games.project.space_invader.sprite.Shot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends JPanel {
    private final RightPanel rightPanel;
    private final LeftPanel leftPanel;

    private Dimension d;
    private List<Alien> aliens;
    private Player player;
    private Shot shot;

    private int direction = -1;
    private int deaths = 0;

    private boolean inGame = true;
    private final URL explImg = SpaceInvadersClassic.class.getResource("images/explosion2.png");
    private String message = "Game Over";

    private Timer timer, timerLife;
    private Boolean getDommage = true;

    private final SpaceInvaders game;

    /**
     * constructeur de la classe Board
     *
     * @param rightPanel
     * @param leftPanel
     * @param game
     */
    public Board(RightPanel rightPanel, LeftPanel leftPanel, SpaceInvaders game) {
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
    private void gameInit() {
        aliens = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 10; j++) {

                var alien = new Alien(Commons.ALIEN_INIT_X + 18 * j,
                        Commons.ALIEN_INIT_Y + 18 * i);
                aliens.add(alien);
            }
        }

        player = new Player();
        shot = new Shot();
    }

    /**
     * Dessine les aliens
     *
     * @param g
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
     * @param g
     */
    private void drawPlayer(Graphics g) {

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
     * @param g
     */
    private void drawShot(Graphics g) {
        if (shot.isVisible()) {
            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }
    }

    /**
     * Dessine les bombes
     *
     * @param g
     */
    private void drawBombing(Graphics g) {
        for (Alien a : aliens) {
            Alien.Bomb b = a.getBomb();
            if (!b.isDestroyed()) {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    /**
     * Dessine le plateau de jeu
     *
     * @param g
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
     * @param g
     */
    private void gameOver(Graphics g) {
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
    private void update() {

        if (deaths == Commons.NUMBER_OF_ALIENS_TO_DESTROY) {
            if (game instanceof SpaceInvadersInfinite) {
                aliens.clear();
                player = null;
                shot = null;
                deaths = 0;
                removeKeyListener(this.getKeyListeners()[0]);
                initBoard();
            } else if (game instanceof SpaceInvadersClassic) {
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
            y -= 4;
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
                direction = -1;
                for (Alien a2 : aliens) {
                    a2.setY(a2.getY() + Commons.GO_DOWN);
                }
            }

            if (x <= Commons.BORDER_LEFT && direction != 1) {
                direction = 1;
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
                bomb.setY(bomb.getY() + 1);
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

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

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
}
