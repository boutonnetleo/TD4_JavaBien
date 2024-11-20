import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.imageio.ImageIO;

public class PhysicEngine implements Engine {

    private ArrayList<DynamicSprite> movingSpriteList = new ArrayList<>();
    private ArrayList<SolidSprite> environment = new ArrayList<>();
    private ArrayList<BombSprite> listBomb = new ArrayList<>();
    private ArrayList<ExplosionSprite> listExplosion = new ArrayList<>();
    private ArrayList<Sprite> listBreakeable = new ArrayList<>();
    private ArrayList<EnemySprite> listEnemy = new ArrayList<>();
    private DynamicSprite hero;
    private RenderEngine renderEngine;
    private Rectangle2D.Double screenRectangle;
    private BufferedImage imageGrass;
    private AtomicBoolean partieEnCours;

    public PhysicEngine(RenderEngine renderEngine, DynamicSprite hero, AtomicBoolean partieEnCours) {
        this.renderEngine = renderEngine;
        this.hero = hero;
        this.partieEnCours = partieEnCours;
        try {
            imageGrass = ImageIO.read(new File("img/grass.png"));
        } catch (Exception e) {
            System.out.println("Image non trouvée");
        }
    }

    /**
     * Ajoute un Sprite dans movingSprite
     * @param sprite
     */
    public void addMovingSprite(DynamicSprite sprite) {
        movingSpriteList.add(sprite);
    }

    /**
     * Ajoute plusieurs Sprite dans movingSprite
     * @param spriteArray
     */
    public void addMovingSprite(ArrayList<DynamicSprite> spriteArray) {
        for (DynamicSprite sprite : spriteArray) {
            movingSpriteList.add(sprite);
        }
    }

    /**
     * Ajoute un Sprite en tant que Sprite cassable par les bombes
     * @param sprite
     */
    public void addBreakable(Sprite sprite) {
        listBreakeable.add(sprite);
    }

    /**
     * Ajoute plusieurs Sprite en tant que Sprite cassable par les bombes
     * @param arrayList
     */
    public void addBreakable(ArrayList<Sprite> arrayList) {
        for (Sprite sprite : arrayList) {
            addBreakable(sprite);
        }
    }

    /**
     * Ajoute plusieurs ennemis
     * @param arrayList
     */
    public void addEnemy(ArrayList<EnemySprite> arrayList) {
        for (EnemySprite sprite : arrayList) {
            listEnemy.add(sprite);
        }
    }

    /**
     * Permet de définir les éléments solides de l'environnement
     * @param environment
     */
    public void setEnvironment(ArrayList<SolidSprite> environment) {
        this.environment = environment;
    }

    /**
     * Permet de définir le rectangle correspondant à la zone de jeu
     * @param width
     * @param height
     */
    public void setScreenRectangle(int width, int height) {
        screenRectangle = new Rectangle2D.Double(0, 0, width, height);
    }

    /**
     * Permet d'obtenir la listes des bombes
     * @return
     */
    public ArrayList<BombSprite> getListBomb() {
        return listBomb;
    }

    /**
     * Efectue un tick de physique
     */
    @Override
    public void update() {
        for (DynamicSprite sprite : movingSpriteList) { //Déplace les DynamicSprite
            sprite.moveIfPossible(environment, screenRectangle);
        }
        for (EnemySprite sprite : listEnemy) { //Tue le héro en cas de contact avec un ennemi
            if (hero.getRectangle2D().intersects(sprite.getRectangle2D())) {
                renderEngine.getRenderList().remove(hero);
                movingSpriteList.remove(hero);
                partieEnCours.set(false);
            }
        }
        
        ArrayList<BombSprite> toRemove = new ArrayList<>();
        for (BombSprite bombSprite : listBomb) { //Gère l'explosion des bombes
            if (bombSprite.hasExploded() && !bombSprite.getExploded()) {
                bombSprite.setExploded(true);
                for (Direction direction : new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST}) {
                    ExplosionSprite explosionSprite = new ExplosionSprite(bombSprite, direction);
                    listExplosion.add(explosionSprite);
                    renderEngine.getRenderList().add(explosionSprite);
                }

                Rectangle2D.Double rectangle1 = new Rectangle2D.Double(bombSprite.getX()-bombSprite.getWidth(), bombSprite.getY(), bombSprite.getWidth()*3, bombSprite.getHeight());
                Rectangle2D.Double rectangle2 = new Rectangle2D.Double(bombSprite.getX(), bombSprite.getY()-bombSprite.getHeight(), bombSprite.getWidth(), bombSprite.getHeight()*3);
                ArrayList<Sprite> toRemove2 = new ArrayList<>();
                for (Sprite sprite : listBreakeable) {
                    if (sprite.getRectangle2D().intersects(rectangle2) || sprite.getRectangle2D().intersects(rectangle1)) {
                        if (sprite.equals(hero)) {
                            partieEnCours.set(false);
                        }
                        toRemove2.add(sprite);
                        if (sprite.isPartOfMap()) {//Destruction de terrain
                            environment.remove(sprite);
                            renderEngine.getRenderList().set(renderEngine.getRenderList().indexOf(sprite), new Sprite(imageGrass, sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(), true));
                        } else {
                            environment.remove(sprite);
                            renderEngine.getRenderList().remove(sprite);
                            listEnemy.remove(sprite);
                            movingSpriteList.remove(sprite);
                        }
                    }
                }
                for (Sprite sprite : toRemove2) {
                    listBreakeable.remove(sprite);
                }
            }
            if (bombSprite.animationFinie()) {
                renderEngine.getRenderList().remove(bombSprite);
                toRemove.add(bombSprite);
            }
        }
        for (BombSprite bombSprite : toRemove) {
            listBomb.remove(bombSprite);
        }
        ArrayList<ExplosionSprite> toRemove3 = new ArrayList<>();
        for (ExplosionSprite explosionSprite : listExplosion) {
            if (explosionSprite.animationFinie()) {
                renderEngine.getRenderList().remove(explosionSprite);
                toRemove3.add(explosionSprite);
            }
        }
        for (ExplosionSprite explosionSprite : toRemove3) {
            listExplosion.remove(explosionSprite);
        }
    }
    
}