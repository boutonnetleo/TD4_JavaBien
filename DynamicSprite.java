import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite {

    protected double speed = 0;
    private final int spriteSheetNumberOfColumn = 10;
    private int timeBetweenFrame = 100;
    protected Direction direction;
    
    /**
     * Crée le DynamicSprite
     * @param image
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public DynamicSprite(BufferedImage image, double x, double y, double width, double height) {
        super(image, x, y, width, height);
    }

    /**
     * Redéfinit la direction du DynamicSprite
     * @param direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Anime le DynamicSprite
     */
    @Override
    public void draw(Graphics g) {
        long index;
        if (speed != 0) {
            index = (System.currentTimeMillis()/timeBetweenFrame) % spriteSheetNumberOfColumn;
        } else {
            index = 0;
        }
        int attitude = direction.getFrameLineRender();
        g.drawImage(image.getSubimage((int) (width*index), (int) (height*attitude), (int) width, (int) height), (int) x, (int) y, null);
    }

    /**
     * Déplace le DynamicSprite
     */
    public void move() {
        switch (direction) {
            case NORTH:
                this.y -= speed;
                break;
            case SOUTH:
                this.y += speed;
                break;
            case WEST:
                this.x -= speed;
                break;
            case EAST:
                this.x += speed;
                break;
        }
    }

    /**
     * Renvoie true si le mouvement est possible
     * @param environment
     * @param screenRectangle
     * @return
     */
    public boolean isMovingPossible(ArrayList<SolidSprite> environment, Rectangle2D.Double screenRectangle) {
        Rectangle2D.Double rectangle2d = this.getRectangle2D(direction, speed);
        for (SolidSprite sprite : environment) {
            if (sprite != this) {
                if (rectangle2d.intersects(sprite.getRectangle2D())) {
                    return false;
                }
                if (!screenRectangle.contains(rectangle2d)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Permet d'obtenir la prochaine boite de collision après déplacement
     * @param direction
     * @param speed
     * @return
     */
    protected Rectangle2D.Double getRectangle2D(Direction direction, double speed) {
        switch (direction) {
            case NORTH:
                return new Rectangle2D.Double(this.x, this.y-speed, this.width, this.height);
            case SOUTH:
                return new Rectangle2D.Double(this.x, this.y+speed, this.width, this.height);
            case WEST:
                return new Rectangle2D.Double(this.x-speed, this.y, this.width, this.height);
            case EAST:
                return new Rectangle2D.Double(this.x+speed, this.y, this.width, this.height);
            default:
                return new Rectangle2D.Double(this.x, this.y, this.width, this.height);
        }
    }

    /**
     * Déplace le DynamicSprite seulement si ce déplacement n'entraine pas de collision
     * @param environment
     * @param screenRectangle
     */
    public void moveIfPossible(ArrayList<SolidSprite> environment, Rectangle2D.Double screenRectangle) {
        if (isMovingPossible(environment, screenRectangle)) {
            this.move();
        }
    }

}