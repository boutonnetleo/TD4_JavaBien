import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Sprite implements Displayable {

    protected BufferedImage image;
    protected double x;
    protected double y;
    protected double width;
    protected double height;
    private boolean partOfMap;

    /**
     * Crée un Sprite
     * @param image
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Sprite(BufferedImage image, double x, double y, double width, double height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.partOfMap = false;
    }

    /**
     * Crée un Sprite
     * @param image
     * @param x
     * @param y
     * @param width
     * @param height
     * @param partOfMap indique si c'est un élément du décors
     */
    public Sprite(BufferedImage image, double x, double y, double width, double height, boolean partOfMap) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.partOfMap = partOfMap;
    }
    
    /**
     * Redessine le Sprite sur le Graphics
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, null);
    }

    /**
     * Permet de créer et d'obtenir la boite de collision
     * @return
     */
    protected Rectangle2D.Double getRectangle2D() {
        return new Rectangle2D.Double(this.x, this.y, this.width, this.height);
    }

    /**
     * Renvoie true si le Sprite fait partie du décors
     * @return
     */
    public boolean isPartOfMap() {
        return partOfMap;
    }

    /**
     * Renvoie la coordonnée x du Sprite
     * @return
     */
    public double getX() {
        return x;
    }

    /**
     * Renvoie la coordonnée y du Sprite
     * @return
     */
    public double getY() {
        return y;
    }

    /**
     * Renvoie la largeur du Sprite
     * @return
     */
    public double getWidth() {
        return width;
    }

    /**
     * Renvoie la hauteur du Sprite
     * @return
     */
    public double getHeight() {
        return height;
    }

}