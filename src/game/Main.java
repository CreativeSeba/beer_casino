package game;

import javax.swing.*;

public class Main extends JFrame {
    public Main() {
        setTitle("Beer Casino");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        GamePanel gamePanel = new GamePanel();
        add(gamePanel);
        pack();
        gamePanel.addSlotMachine(200, 150);
        gamePanel.addSlotMachine(500, 300);
        gamePanel.addSlotMachine(500, 100);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main game = new Main();
            game.setVisible(true);
        });
    }
}