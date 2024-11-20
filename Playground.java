import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Playground {
    
    private ArrayList<Sprite> environment = new ArrayList<>();
    private ArrayList<Sprite> breakableList = new ArrayList<>();
    private int fenetreWidth;
    private int fenetreHeight;

    /**
     * Génère la map à partir du fichier texte pathName
     * Dans le fichier texte :
     *  - "T" pour arbre
     *  - "G" ou " " pour herbe
     *  - "R" pour rocher (les rocher peuvent être cassés)
     * @param pathName
     */
    public Playground(String pathName) {
        try {
            final BufferedImage imageTree = ImageIO.read(new File("img/tree.png"));
            final BufferedImage imageGrass = ImageIO.read(new File("img/grass.png"));
            final BufferedImage imageRock = ImageIO.read(new File("img/rock.png"));

            final int imageWidth = imageTree.getWidth();
            final int imageHeight = imageTree.getHeight();

            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
            String line = bufferedReader.readLine();
            int lineNumber = 0;
            int columnNumber = 0;
            while (line != null) {
                for (byte element : line.getBytes(StandardCharsets.UTF_8)) {
                    switch (element) {
                        case 'T':
                            environment.add(new SolidSprite(imageTree, columnNumber*imageWidth, lineNumber*imageHeight, imageWidth, imageHeight, true));
                            break;
                        case 'G':
                        case ' ':
                            environment.add(new Sprite(imageGrass, columnNumber*imageWidth, lineNumber*imageHeight, imageWidth, imageHeight, true));
                            break;
                        case 'R':
                            SolidSprite solidSprite = new SolidSprite(imageRock, columnNumber*imageWidth, lineNumber*imageHeight, imageWidth, imageHeight, true);
                            environment.add(solidSprite);
                            breakableList.add(solidSprite);
                            break;
                    }
                    columnNumber++;
                }
                fenetreWidth = columnNumber*imageWidth;
                columnNumber = 0;
                lineNumber++;
                line = bufferedReader.readLine();
            }
            fenetreHeight = lineNumber*imageHeight;
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement du fond");
        }
    }

    /**
     * Renvoie la liste des sprites de type SolidSprites
     * @return
     */
    public ArrayList<SolidSprite> getSolidSpriteList() {
        ArrayList<SolidSprite> solidSpriteArrayList = new ArrayList<>();
        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite) {
                solidSpriteArrayList.add((SolidSprite) sprite);
            }
        }
        return solidSpriteArrayList;
    }

    /**
     * Renvoie la liste de tous les Sprites pour le renderEngine
     * @return
     */
    public ArrayList<Displayable> getSpriteList() {
        ArrayList<Displayable> displayableArrayList = new ArrayList<>();
        for (Sprite sprite : environment) {
            displayableArrayList.add((Displayable) sprite);
        }
        return displayableArrayList;
    }

    /**
     * Renvoie la liste des Sprite qui peuvent être cassés
     * @return
     */
    public ArrayList<Sprite> getBreakable() {
        return breakableList;
    }

    /**
     * Renvoie la largeur de la fenêtre
     * @return
     */
    public int getWidth() {
        return fenetreWidth;
    }

    /**
     * Renvoie la hauteur de la fenêtre
     * @return
     */
    public int getHeight() {
        return fenetreHeight;
    }

}