package game;

import javax.swing.*;

public class Main extends JFrame {
    public Main() {
        setTitle("Beer Casino");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        ImageIcon icon = new ImageIcon("src/game/graphics/icon.png");
        setIconImage(icon.getImage());

        GamePanel gamePanel = new GamePanel(1000, 700, 750, 50, 10000, 100, 200);
        add(gamePanel);  // Here, add() is called on the JFrame (not GamePanel itself)
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main game = new Main();
            game.setVisible(true);
        });
    }
}