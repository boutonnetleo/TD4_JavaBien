import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;

public class EcranFin {

    private JPanel panel;
    private JFrame displayZoneFrame;

    /**
     * Affiche un panel permettant de rejouer sur la fenêtre
     * @param displayZoneFrame
     * @param partieEnCours
     */
    public EcranFin(JFrame displayZoneFrame, AtomicBoolean partieEnCours) {
        this.displayZoneFrame = displayZoneFrame;

        panel = new JPanel() {
            private Image backgroundImage = new ImageIcon("img/background.png").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);
        displayZoneFrame.getContentPane().add(panel);

        JLabel label = new JLabel("Vous êtes mort", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 48));
        label.setForeground(Color.RED);
        label.setBounds(0, 200, 704, 100);
        panel.add(label);

        JButton replayButton = new JButton("Rejouer");
        replayButton.setBounds(302, 350, 100, 40);
        replayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                partieEnCours.set(false);
            }
        });
        panel.add(replayButton);

        displayZoneFrame.setVisible(true);
    }

    /**
     * Permet de retirer le panel de la fenêtre
     */
    public void close() {
        panel.setVisible(false);
        displayZoneFrame.remove(panel);
    }
    
}