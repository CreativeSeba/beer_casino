package game;

import javax.swing.*;

public class Main extends JFrame {
    public Main() {
        setTitle("Beer Casino");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        ImageIcon icon = new ImageIcon("src/game/graphics/icon.png");
        setIconImage(icon.getImage());

        GamePanel gamePanel = new GamePanel(1000, 700, 750, 50, 10000, 100, 400);
        add(gamePanel);
        pack(); // Automatically size the window based on its content
        setLocationRelativeTo(null); // Center the window on the screen;

        //SMALL
        SlotMachine smallSlotMachine = new SmallSlotMachine(-200, 0);

        //BIG
        SlotMachine bigSlotMachine = new BigSlotMachine(200, 0);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main game = new Main();
            game.setVisible(true);
        });
    }
}