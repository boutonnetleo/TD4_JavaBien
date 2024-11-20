import java.awt.image.BufferedImage;

public class SolidSprite extends Sprite {
    
    /**
     * Crée le SolidSprite
     * @param image
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public SolidSprite(BufferedImage image, double x, double y, double width, double height) {
        super(image, x, y, width, height);
    }

    /**
     * Crée le SolidSprite
     * @param image
     * @param x
     * @param y
     * @param width
     * @param height
     * @param partOfMap indique si le Sprite fait partie du décors
     */
    public SolidSprite(BufferedImage image, double x, double y, double width, double height, boolean partOfMap) {
        super(image, x, y, width, height, partOfMap);
    }

}