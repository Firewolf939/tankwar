import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TankWar {

    public static GameClient gameClient;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        gameClient = new GameClient(1024,768);
        frame.add(gameClient);
        frame.setTitle("TankWar");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                gameClient.KeyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                gameClient.keyReleased(e);
            }
        });
    }
}
