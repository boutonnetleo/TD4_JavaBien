import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemySprite extends DynamicSprite {
    
    /**
     * Crée un ennemi
     * @param image
     * @param x
     * @param y
     * @param width
     * @param height
     * @param direction
     * @param speed
     */
    public EnemySprite(BufferedImage image, double x, double y, double width, double height, Direction direction, int speed) {
        super(image, x, y, width, height);
        this.direction = direction;
        this.speed = speed;
    }

    /**
     * Inverse la direction de l'ennemi s'il s'apprête à rencontrer un obstacle
     * Cette fonction renvoie toujours true car l'ennemi se déplace toujours, seule la direction change
     */
    @Override
    public boolean isMovingPossible(ArrayList<SolidSprite> environment, Double screenRectangle) {
        if (!super.isMovingPossible(environment, screenRectangle)) {
            switch (direction) {
                case NORTH:
                    direction = Direction.SOUTH;
                    break;
                case SOUTH:
                    direction = Direction.NORTH;
                    break;
                case EAST:
                    direction = Direction.WEST;
                    break;
                case WEST:
                    direction = Direction.EAST;
                    break;
            }
        }
        return true;
    }

}