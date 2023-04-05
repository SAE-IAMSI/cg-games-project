package games.project.space_invader.sprite;

import java.awt.*;

public class Sprite {

    private boolean visible;
    private Image image;
    private boolean dying;

    int x;
    int y;
    int dx;

    /**
     * Constructeur de la classe Sprite
     */
    public Sprite() {
        visible = true;
    }

    /**
     * Quand l'objet est détruit
     */
    public void die() {
        visible = false;
    }

    /**
     * Défini la visibilité
     * @return visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Redéfinit la visibilité
     * @param visible boolean
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Définit l'image
     * @param image Image
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Récupère l'image
     * @return image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Définit la position en x
     * @param x int
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Définit la position en y
     * @param y int
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Retourne la position en x
     */
    public int getY() {
        return y;
    }

    /**
     * Retourne la position en y
     */
    public int getX() {
        return x;
    }

    /**
     * Définit la mort
     * @param dying boolean
     */
    public void setDying(boolean dying) {
        this.dying = dying;
    }

    /**
     * Retourne la mort
     * @return dying
     */
    public boolean isDying() {
        return this.dying;
    }

    public int getDx() {
        return dx;
    }


}
