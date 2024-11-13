package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public abstract class SlotMachine extends JPanel  {
    private int[] numbers;
    private Random random = new Random();

    public SlotMachine(int numberOfSlots) {
        numbers = new int[numberOfSlots];
        setPreferredSize(new Dimension(200, 200));  // Set the size of the slot machine
   // Background color for the slot machine
        // Handle mouse click to trigger the slot machine's spin
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                spin();
                repaint();
            }
        });
    }

    protected void spin() {
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(10);  // Random numbers between 0-9
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Render the slot machine's numbers on the screen
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        int xPos = (getWidth() - (fm.stringWidth("0") * numbers.length + 20 * (numbers.length - 1))) / 2;
        int yPos = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

        // Draw each slot machine number on the screen
        for (int i = 0; i < numbers.length; i++) {
            g.drawString(String.valueOf(numbers[i]), xPos + i * (fm.stringWidth("0") + 20), yPos);
        }
    }
}
