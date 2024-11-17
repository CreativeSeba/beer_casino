package game;

import javax.swing.*;

public class Main extends JFrame {
    public Main() {
        setTitle("Beer Casino");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        int screenWidth = 1100;
        int screenHeight = 800;
        setSize(screenWidth, screenHeight);

        ImageIcon icon = new ImageIcon("src/game/graphics/icon.png");
        setIconImage(icon.getImage());

        GamePanel gamePanel = new GamePanel(screenWidth/2, screenHeight/2, 700, 50);
        add(gamePanel);
        setLocationRelativeTo(null); // Center the window on the screen


        gamePanel.addSlotMachine(-200, 0, Slots.SMALL);
        gamePanel.addSlotMachine(200, 0, Slots.BIG);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main game = new Main();
            game.setVisible(true);
        });
    }
}