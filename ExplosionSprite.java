import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ExplosionSprite extends Sprite {

    private long dateCreation;
    private int animationDelay;
    private static BufferedImage explosionTileSheet = null;
    private int xImg;
    private int yImg;

    /**
     * Crée le Sprite d'explosion (uniquement visuel)
     * @param bombSprite
     * @param direction
     */
    public ExplosionSprite(BombSprite bombSprite, Direction direction) {
        super(explosionTileSheet, 0, 0, 64, 64);
        this.dateCreation = bombSprite.getDateCreation()+bombSprite.getExplosionTime();
        this.animationDelay = bombSprite.getAnimationDelay();
        switch (direction) {
            case NORTH:
                this.y = bombSprite.y-64;
                this.x = bombSprite.x;
                xImg = 64;
                yImg = 0;
                break;
            case SOUTH:
                this.y = bombSprite.y+64;
                this.x = bombSprite.x;
                xImg = 64;
                yImg = 128;
                break;
            case WEST:
                this.y = bombSprite.y;
                this.x = bombSprite.x-64;
                xImg = 0;
                yImg = 64;
                break;
            case EAST:
                this.y = bombSprite.y;
                this.x = bombSprite.x+64;
                xImg = 128;
                yImg = 64;
                break;
        }
        if (explosionTileSheet == null) {
            try {
                explosionTileSheet = ImageIO.read(new File("img/explosionTileSheet.png"));
            } catch (IOException ignored) {
                System.out.println("Erreur : L'image existe pas");
            }
            this.image = explosionTileSheet;
        }
    }

    /**
     * Renvoie true si l'animation est finie
     * @return
     */
    public boolean animationFinie() {
        return System.currentTimeMillis()-dateCreation > 4*animationDelay;
    }

    /**
     * Redessine l'explosion
     */
    @Override
    public void draw(Graphics g) {
        long tempRestant = System.currentTimeMillis()-dateCreation;
        if (tempRestant < animationDelay) {
            g.drawImage(explosionTileSheet.getSubimage(xImg, yImg, 64, 64), (int) x, (int) y, null);
        } else if (tempRestant < 2*animationDelay) {
            g.drawImage(explosionTileSheet.getSubimage(xImg+193, yImg, 64, 64), (int) x, (int) y, null);
        } else if (tempRestant < 3*animationDelay) {
            g.drawImage(explosionTileSheet.getSubimage(xImg+2*193, yImg, 64, 64), (int) x, (int) y, null);
        } else {
            g.drawImage(explosionTileSheet.getSubimage(xImg+3*193, yImg, 64, 64), (int) x, (int) y, null);
        }
    }
    
}