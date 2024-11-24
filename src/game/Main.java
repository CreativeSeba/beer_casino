package game;

import javax.swing.*;

public class Main extends JFrame {
    public Main() {
        setTitle("Beer Casino");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        ImageIcon icon = new ImageIcon("src/game/graphics/icon.png");
        setIconImage(icon.getImage());

        GamePanel gamePanel = new GamePanel(1000, 700, 750, 50, 10000);
        add(gamePanel);
        pack(); // Automatically size the window based on its content
        setLocationRelativeTo(null); // Center the window on the screen;

        //SMALL
        gamePanel.addSlotMachine(-200,0, Slots.SMALL);

        //BIG
        gamePanel.addSlotMachine(200,0, Slots.BIG);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main game = new Main();
            game.setVisible(true);
        });
    }
}