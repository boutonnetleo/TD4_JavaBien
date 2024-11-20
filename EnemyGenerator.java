import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class EnemyGenerator {
    
    private ArrayList<EnemySprite> enemyList = new ArrayList<>();
    private final int imageWidth = 48;
    private final int imageHeight = 50;

    /**
     * Génère les ennemis à partir du fichier texte pathName
     * Dans le fichier texte :
     *  - "O" correspond à une case sans ennemis
     *  - "N" correspond à un ennemis se déplaçant vers le nord
     *  - "S" correspond à un ennemis se déplaçant vers le sud
     *  - "E" correspond à un ennemis se déplaçant vers l'est
     *  - "W" correspond à un ennemis se déplaçant vers l'ouest
     * @param pathName
     */
    public EnemyGenerator(String pathName) {
        try {
            final BufferedImage imageEnemy = ImageIO.read(new File("img/enemyTileSheetLowRes.png"));

            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
            String line = bufferedReader.readLine();
            int columnNumber = 0;
            int lineNumber = 0;
            while (line != null) {
                for (byte element : line.getBytes(StandardCharsets.UTF_8)) {
                    switch (element) {
                        case 'N':
                            enemyList.add(new EnemySprite(imageEnemy, columnNumber*64+8, lineNumber*64+7, imageWidth, imageHeight, Direction.NORTH, 5));
                            break;
                        case 'S':
                            enemyList.add(new EnemySprite(imageEnemy, columnNumber*64+8, lineNumber*64+7, imageWidth, imageHeight, Direction.SOUTH, 5));
                            break;
                        case 'W':
                            enemyList.add(new EnemySprite(imageEnemy, columnNumber*64+8, lineNumber*64+7, imageWidth, imageHeight, Direction.WEST, 5));
                            break;
                        case 'E':
                            enemyList.add(new EnemySprite(imageEnemy, columnNumber*64+8, lineNumber*64+7, imageWidth, imageHeight, Direction.EAST, 5));
                            break;
                    }
                    columnNumber++;
                }
                columnNumber = 0;
                lineNumber++;
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement des ennemis");
        }
    }

    /**
     * Renvoie la liste des Sprites pour le renderEngine
     * @return
     */
    public ArrayList<Displayable> getRenderList() {
        ArrayList<Displayable> displayableArrayList = new ArrayList<>();
        for (Sprite sprite : enemyList) {
            displayableArrayList.add((Displayable) sprite);
        }
        return displayableArrayList;
    }

    /**
     * Renvoie la liste des ennemis pour le physicEngine
     * @return
     */
    public ArrayList<EnemySprite> getEnemyList() {
        return enemyList;
    }

    /**
     * Renvoie la liste des ennemis pour les déplacement dans le PhysicEngine
     * @return
     */
    public ArrayList<DynamicSprite> getDynamicList() {
        ArrayList<DynamicSprite> dynamicList = new ArrayList<>();
        for (Sprite sprite : enemyList) {
            dynamicList.add((DynamicSprite) sprite);
        }
        return dynamicList;
    }

    /**
     * Renvoie la liste des ennemis qui peuvent être détruit par les bombes
     * @return
     */
    public ArrayList<Sprite> getBreakable() {
        ArrayList<Sprite> list = new ArrayList<>();
        for (Sprite sprite : enemyList) {
            list.add((Sprite) sprite);
        }
        return list;
    }

}