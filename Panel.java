import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Panel {

    private JFrame displayZoneFrame;
    private RenderEngine renderEngine;
    private GameEngine gameEngine;
    private PhysicEngine physicEngine;
    private Timer renderTimer;
    private Timer physicTimer;
    private Timer gameTimer;

    /**
     * Permet de créer/gérer le panel contenant tous le gameplay du jeu
     * @param displayZoneFrame
     * @param partieEnCours
     */
    public Panel(JFrame displayZoneFrame, AtomicBoolean partieEnCours) {
        this.displayZoneFrame = displayZoneFrame;

        DynamicSprite hero = null;
        try {
            hero = new DynamicSprite(ImageIO.read(new File("img/heroTileSheetLowRes.png")), 320, 0, 48, 50);
            hero.setDirection(Direction.NORTH);
        } catch (IOException ignored) {
            System.out.println("Erreur : L'image existe pas");
        }

        Playground playground = new Playground("data/genshin.txt");
        EnemyGenerator enemyGenerator = new EnemyGenerator("data/genshin-enemy.txt");

        renderEngine = new RenderEngine();
        displayZoneFrame.getContentPane().add(renderEngine);
        renderTimer = new Timer(20, (time) -> {
            renderEngine.update();
        });
        renderTimer.start();

        physicEngine = new PhysicEngine(renderEngine, hero, partieEnCours);
        physicTimer = new Timer(50, (time) -> {
            physicEngine.update();
        });
        physicTimer.start();

        gameEngine = new GameEngine(hero, renderEngine, physicEngine);
        displayZoneFrame.addKeyListener(gameEngine);
        displayZoneFrame.setFocusable(true);
        displayZoneFrame.requestFocusInWindow();
        gameTimer = new Timer(50, (time) -> {
            gameEngine.update();
        });
        gameTimer.start();

        physicEngine.setEnvironment(playground.getSolidSpriteList());
        physicEngine.addMovingSprite(hero);
        physicEngine.addMovingSprite(enemyGenerator.getDynamicList());
        physicEngine.addEnemy(enemyGenerator.getEnemyList());

        renderEngine.setRenderList(playground.getSpriteList());
        renderEngine.addToRenderList(hero);
        renderEngine.addToRenderList(enemyGenerator.getRenderList());

        physicEngine.addBreakable(hero);
        physicEngine.addBreakable(playground.getBreakable());
        physicEngine.addBreakable(enemyGenerator.getBreakable());
        physicEngine.setScreenRectangle(playground.getWidth(), playground.getHeight());

        displayZoneFrame.setSize(playground.getWidth()+14, playground.getHeight()+37);
        displayZoneFrame.setVisible(true);
    }

    /**
     * Permet de retirer le panel de la fenêtre
     */
    public void close() {
        displayZoneFrame.removeKeyListener(gameEngine);
        displayZoneFrame.remove(renderEngine);
        renderTimer.stop();
        physicTimer.stop();
        gameTimer.stop();
        renderEngine.setVisible(false);
    }
    
}