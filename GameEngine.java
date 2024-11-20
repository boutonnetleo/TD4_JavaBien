import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine implements Engine, KeyListener {

    private DynamicSprite hero;
    private RenderEngine renderEngine;
    private PhysicEngine physicEngine;
    private int[] NextDir = new int[]{-1, -1, -1, -1}; //Stocke l'historique des dernières touches appuyées pour le déplacement

    public GameEngine(DynamicSprite hero, RenderEngine renderEngine, PhysicEngine physicEngine) {
        this.hero = hero;
        this.renderEngine = renderEngine;
        this.physicEngine = physicEngine;
    }

    @Override
    public void update() {
    }

    /**
     * Gère l'appui des touches pour les déplacements
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_Z:
                hero.direction = Direction.NORTH;
                addDir(Direction.NORTH);
                hero.speed = 5;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                hero.direction = Direction.EAST;
                addDir(Direction.EAST);
                hero.speed = 5;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                hero.direction = Direction.SOUTH;
                addDir(Direction.SOUTH);
                hero.speed = 5;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_Q:
                hero.direction = Direction.WEST;
                addDir(Direction.WEST);
                hero.speed = 5;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Gère le relachement des touches pour le déplacement et le dépôt de la bombe
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_Z:
                removeDir(Direction.NORTH);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                removeDir(Direction.EAST);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                removeDir(Direction.SOUTH);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_Q:
                removeDir(Direction.WEST);
                break;
            case KeyEvent.VK_SPACE:
                BombSprite bombSprite = new BombSprite(hero);
                renderEngine.addToRenderList(bombSprite);
                physicEngine.getListBomb().add(bombSprite);
                break;
        }
        if (NextDir[0] == -1) {
            hero.speed = 0;
        } else {
            hero.direction = getDir();
        }
    }

    private void addDir(Direction dir) {
        for (int i = 0; i < 4; i++) {
            if (NextDir[i] == dir.getFrameLineRender()) {
                return;
            }
            if (NextDir[i] == -1) {
                NextDir[i] = dir.getFrameLineRender();
                break;
            }
        }
    }

    private void removeDir(Direction dir) {
        boolean find = false;
        for (int i = 0; i < 4; i++) {
            if (find) {
                NextDir[i-1] = NextDir[i];
                NextDir[i] = -1;
            } else {
                if (NextDir[i] == dir.getFrameLineRender()) {
                    find = true;
                }
            }
        }
    }

    private Direction getDir() {
        int last = 0;
        for (int i = 0; i < 4; i++) {
            if (NextDir[i] == -1) {
                break;
            }
            last = NextDir[i];
        }
        switch (last) {
            case 2:
                return Direction.NORTH;
            case 0:
                return Direction.SOUTH;
            case 3:
                return Direction.EAST;
            case 1:
                return Direction.WEST;
        }
        return Direction.NORTH;
    }
    
}