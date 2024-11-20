import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;

class Main {

    private static JFrame displayZoneFrame;
    private static AtomicBoolean partieEnCours;

    public static void main(String[] args) { //Gère la création de la fenêtre
        displayZoneFrame = new JFrame("Java Labs");
        displayZoneFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        partieEnCours = new AtomicBoolean();

        while (true) { //Alterne entre le jeu et le menu pour rejouer
            partieEnCours.set(true);
            Panel mainPanel = new Panel(displayZoneFrame, partieEnCours);
            while (partieEnCours.get()) {
                try {
                    Thread.sleep(50);
                } catch (Exception ignored) {}
            }
            mainPanel.close();

            partieEnCours.set(true);
            EcranFin ecranFin = new EcranFin(displayZoneFrame, partieEnCours);
            while (partieEnCours.get()) {
                try {
                    Thread.sleep(50);
                } catch (Exception ignored) {}
            }
            ecranFin.close();
        }
    }

    //Il faut une javadoc sur tout ce qui est public
    //Mettre private ou protected quand c'est possible

    //Quand un ennemi meurt, il réapparait au pif ?
    //Compteur de score quand on tue des mobs ?

}