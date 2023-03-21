package games.project.space_invader.sprite;

import java.awt.Image;

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
     * Désinit la visibilité
     * @return
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Redéfinit la visibilité
     * @param visible
     */
    protected void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Définit l'image
     * @param image
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Récupère l'image
     * @return
     */
    public Image getImage() {
        return image;
    }

    /**
     * Définit la position en x
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Définit la position en y
     * @param y
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
     * @param dying
     */
    public void setDying(boolean dying) {
        this.dying = dying;
    }

    /**
     * Retourne la mort
     * @return
     */
    public boolean isDying() {
        return this.dying;
    }
}
