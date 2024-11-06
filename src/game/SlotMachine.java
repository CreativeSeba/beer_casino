package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public abstract class SlotMachine extends JPanel  {
    private static final int SPAWN_X = 100; // Define SPAWN_X
    private static final int SPAWN_Y = 100; // Define SPAWN_Y
    private int[] numbers;
    private Random random = new Random();

    public SlotMachine(int numberOfSlots) {
        this(numberOfSlots, 0, 0); // Default to spawn location
    }

    public SlotMachine(int numberOfSlots, int x, int y) {
        numbers = new int[numberOfSlots];
        setPreferredSize(new Dimension(200, 200));
        setBackground(Color.BLUE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                spin();
                repaint();
            }
        });

        // Set location based on provided x and y
        EventQueue.invokeLater(() -> setLocation(SPAWN_X + x, SPAWN_Y + y));
    }

    protected void spin() {
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(10); // Random numbers between 0-9
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - (fm.stringWidth("0") * numbers.length + 20 * (numbers.length - 1))) / 2;
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        for (int i = 0; i < numbers.length; i++) {
            g.drawString(String.valueOf(numbers[i]), x + i * (fm.stringWidth("0") + 20), y);
        }
    }
}