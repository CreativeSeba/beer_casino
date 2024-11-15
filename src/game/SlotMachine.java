// SlotMachine.java
package game;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.util.List;


public abstract class SlotMachine extends JPanel implements Money {
    private int[] numbers;
    private List<Pair<Integer, Integer>> combinations;
    private Random random = new Random();
    private String labelText;
    private int numberOfSlots;
    private Pair<Integer, Integer> numCount;
    public static PlayerMoney playerMoney;

    public SlotMachine(int numberOfSlots, Slots type, int loose) {
        switch(type){
            case SMALL:
                this.labelText = "Small Slot Machine";
                break;
            case BIG:
                this.labelText = "Big Slot Machine";
                break;
            default:
                this.labelText = "Slot Machine";
        }
        this.numberOfSlots = numberOfSlots;
        numbers = new int[numberOfSlots];
        combinations = new ArrayList<>();
        playerMoney = new PlayerMoney(10000);
        setPreferredSize(new Dimension(200, 200));  // Set the size of the slot machine

        // Handle mouse click to trigger the slot machine's spin
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                spin();
                removeMoney(playerMoney, loose);

                for(Pair<Integer, Integer> pair : combinations) {
                    System.out.println(pair.first + " " + pair.second);
                }
                System.out.println("\n");
                repaint();
            }
        });

    }

    protected void spin() {
        combinations.clear();
        Pair<Integer, Integer> numCount = new Pair<>(0, 0);
        for (int i = 0; i < numberOfSlots; i++) {
            numbers[i] = 0;
        }
        for (int i = 0; i < numberOfSlots; i++) {
            numbers[i] = random.nextInt(10); // Random numbers between 0-9
            int curr = numbers[i];
            if (i == 0) {
                numCount.first = curr;
                numCount.second = 1;
            } else {
                int prev = numbers[i - 1];
                if (curr == prev) {
                    numCount.second++;
                } else {
                    combinations.add(new Pair<>(numCount.first, numCount.second));
                    numCount.first = curr;
                    numCount.second = 1;
                }
            }
        }
        combinations.add(new Pair<>(numCount.first, numCount.second));
        repaint(); // Trigger repaint to update the slot machine window
    }

    public void resetNumbers() {
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = 0;
        }
        repaint();
    }
    @Override
    public void addMoney(PlayerMoney playerMoney, int amount){
        Money.super.addMoney(playerMoney, amount);
    }
    @Override
    public void removeMoney(PlayerMoney playerMoney, int amount){
        Money.super.removeMoney(playerMoney, amount);
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