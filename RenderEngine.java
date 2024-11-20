import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class RenderEngine extends JPanel implements Engine  {

    private ArrayList<Displayable> renderList;

    /**
     * Initialise le renderEngine
     */
    public RenderEngine() {
        renderList = new ArrayList<>();
    }

    /**
     * Dessines tous les Sprites sur le panel
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Displayable displayable : renderList) {
            displayable.draw(g);
        }
    }

    /**
     * Redessine les Sprites à chaque tick d'affichage
     */
    @Override
    public void update() {
        this.repaint();
    }

    /**
     * Permet de définir la liste des Sprites à afficher
     * @param renderList
     */
    public void setRenderList(ArrayList<Displayable> renderList) {
        this.renderList = renderList;
    }

    /**
     * Permet d'obtenir la liste des Sprites à afficher
     * @return
     */
    public ArrayList<Displayable> getRenderList() {
        return renderList;
    }

    /**
     * Permet d'ajouter un Sprite à afficher
     * @param displayable
     */
    public void addToRenderList(Displayable displayable) {
        renderList.add(displayable);
    }

    /**
     * Permet d'ajouter plusieurs Sprite à afficher
     * @param displayableList
     */
    public void addToRenderList(ArrayList<Displayable> displayableList) {
        for (Displayable displayable : displayableList) {
            renderList.add(displayable);
        }
    }

}