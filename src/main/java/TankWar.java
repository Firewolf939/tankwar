import javax.swing.*;

public class TankWar {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        frame.add(new GameClient(800,600));
        frame.setTitle("TankWar");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

    }
}
