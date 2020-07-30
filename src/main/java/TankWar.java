import javax.swing.*;

public class TankWar {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        GameClient gameClient = new GameClient(1024,768);
        frame.add(gameClient);
        frame.setTitle("TankWar");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        gameClient.repaint();

    }
}
