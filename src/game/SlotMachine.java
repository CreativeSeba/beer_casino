// SlotMachine.java
package game;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.util.List;


public abstract class SlotMachine extends JPanel {
    private int[] numbers;
    private int[] numCount;
    private List<Pair<Integer, Integer>> combinations = new ArrayList<>();
    private Random random = new Random();
    private String labelText;

    public SlotMachine(int numberOfSlots, String labelText) {
        this.labelText = labelText;
        numbers = new int[numberOfSlots];
        for (int i = 0; i < numberOfSlots; i++) {
            numbers[i] = 0;
        }
        setPreferredSize(new Dimension(200, 200));  // Set the size of the slot machine

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
        combinations.clear(); // Clear previous combinations
        numCount = new int[10]; // Reset numCount array

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(10); // Random numbers between 0-9
            int curr = numbers[i];
            if (i == 0) {
                numCount[curr]++;
            } else {
                int prev = numbers[i - 1];
                if (curr == prev) {
                    numCount[curr]++;
                } else {
                    combinations.add(new Pair<>(prev, numCount[prev]));
                    numCount[prev] = 0;
                    numCount[curr]++;
                }
            }
        }

        for (Pair<Integer, Integer> pair : combinations) {
            System.out.println(pair.getKey() + " " + pair.getValue());
        }
        System.out.println("\n\n\n");
        combinations.clear();
    }

    public void resetNumbers() {
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = 0;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the label text above the slot machine
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g.getFontMetrics();
        int labelX = (getWidth() - fm.stringWidth(labelText)) / 2;
        int labelY = fm.getHeight();
        g.drawString(labelText, labelX, labelY);

        // Render the slot machine's numbers on the screen
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        fm = g.getFontMetrics();
        int xPos = (getWidth() - (fm.stringWidth("0") * numbers.length + 20 * (numbers.length - 1))) / 2;
        int yPos = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

        // Draw each slot machine number on the screen
        for (int i = 0; i < numbers.length; i++) {
            g.drawString(String.valueOf(numbers[i]), xPos + i * (fm.stringWidth("0") + 20), yPos);
        }
    }
}