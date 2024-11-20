import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BombSprite extends Sprite {
    
    private int explosionTime = 3000;
    private long dateCreation;
    private int animationDelay = 40;
    private static BufferedImage imageBomb = null;
    private static BufferedImage imageBombRed = null;
    private static BufferedImage explosionTileSheet = null;
    private boolean exploded = false;

    /**
     * Créée la bombe
     * @param sprite
     */
    public BombSprite(Sprite sprite) {
        super(imageBomb, 0, 0, 64, 64);
        if (imageBomb == null) {
            try {
                imageBomb = ImageIO.read(new File("img/bomb.png"));
                imageBombRed = ImageIO.read(new File("img/bombRed.png"));
                explosionTileSheet = ImageIO.read(new File("img/explosionTileSheet.png"));
            } catch (IOException ignored) {
                System.out.println("Erreur : L'image existe pas");
            }
            this.image = imageBomb;
        }
        this.x = (int) ((sprite.x+32)/64)*64;
        this.y = (int) ((sprite.y+32)/64)*64;
        dateCreation = System.currentTimeMillis();
    }

    /**
     * Indique si la bombe a explosée
     * @return
     */
    public boolean hasExploded() {
        return System.currentTimeMillis()-dateCreation > explosionTime;
    }

    /**
     * Enregistre la prise en compte du caractère explosé
     * @param exploded
     */
    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    /**
     * Indique si le caractère explosé à déjà été pris en compte
     * @return
     */
    public boolean getExploded() {
        return exploded;
    }

    /**
     * Renvoie la date de création de la bombe
     * @return
     */
    public long getDateCreation() {
        return dateCreation;
    }

    /**
     * Renvoie la durée avant explosion de la bombe
     * @return
     */
    public int getExplosionTime() {
        return explosionTime;
    }

    /**
     * Renvoie le délai entre deux image lors de l'animation d'explosion
     * @return
     */
    public int getAnimationDelay() {
        return animationDelay;
    }

    /**
     * Renvoie true si l'animation d'explosion est finie
     * @return
     */
    public boolean animationFinie() {
        return System.currentTimeMillis()-dateCreation > explosionTime+4*animationDelay;
    }

    /**
     * Redessine la bombe
     */
    @Override
    public void draw(Graphics g) {
        long tempRestant = explosionTime-System.currentTimeMillis()+dateCreation;
        if (tempRestant > explosionTime/3) {
            g.drawImage(imageBomb, (int) x, (int) y, null);
        } else if (tempRestant > explosionTime/8) {
            if ((int) (tempRestant/100) % 2 == 0) {
                g.drawImage(imageBomb, (int) x, (int) y, null);
            } else {
                g.drawImage(imageBombRed, (int) x, (int) y, null);
            }
        } else if (tempRestant > 0) {
            if ((int) (tempRestant/50) % 2 == 0) {
                g.drawImage(imageBomb, (int) x, (int) y, null);
            } else {
                g.drawImage(imageBombRed, (int) x, (int) y, null);
            }
        } else if (tempRestant > -animationDelay) {
            g.drawImage(explosionTileSheet.getSubimage(64, 64, 64, 64), (int) x, (int) y, null);
        } else if (tempRestant > -2*animationDelay) {
            g.drawImage(explosionTileSheet.getSubimage(64+193, 64, 64, 64), (int) x, (int) y, null);
        } else if (tempRestant > -3*animationDelay) {
            g.drawImage(explosionTileSheet.getSubimage(64+2*193, 64, 64, 64), (int) x, (int) y, null);
        } else {
            g.drawImage(explosionTileSheet.getSubimage(64+3*193, 64, 64, 64), (int) x, (int) y, null);
        }
    }

}
