package game;

import javax.swing.*;

public class Main extends JFrame {
    public Main() {
        setTitle("Beer Casino");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        GamePanel gamePanel = new GamePanel();
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