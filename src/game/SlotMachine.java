package game;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.util.List;


public abstract class SlotMachine extends GamePanel implements Money {
    protected int[] numbers;
    public static List<Pair<Integer, Integer>> combinations;
    private Random random = new Random();
    private String labelText;
    private int numberOfSlots;
    private PlayerMoney playerMoney = GamePanel.playerMoney;
    Slots type;

    public SlotMachine(int numberOfSlots, Slots type, int loose) {
        this.numberOfSlots = numberOfSlots;
        this.type = type;
        numbers = new int[numberOfSlots];
        combinations = new ArrayList<>();

        switch (type) {
            case SMALL:
                this.labelText = "Small Slot Machine";
                break;
            case BIG:
                this.labelText = "Big Slot Machine";
                break;
            default:
                this.labelText = "Slot Machine";
        }
        setPreferredSize(new Dimension(200, 200));  // Set the size of the slot machine

        // Handle mouse click to trigger the slot machine's spin
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (playerMoney.money > 0) {
                    spin();
                    removeMoney(playerMoney, loose);
//                    combinations.clear();
//                    combinations.add(0, new Pair<>(1, 3));
//                    combinations.add(1, new Pair<>(2, 1));
//                    combinations.add(2, new Pair<>(4, 1));
//                    numbers = new int[]{1, 1, 1, 2, 1};

                    for (Pair<Integer, Integer> pair : combinations) {
                        System.out.println(pair.first + " " + pair.second);
                    }
                    setCombinations();
                    System.out.println("\n");
                    repaint();
                } else {
                    System.out.println("Przegrales ");
                }
            }
        });
    }

    private void spin() {
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
        repaint();
    }
    abstract void setCombinations();

    public void resetNumbers() {
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = 0;
        }
        repaint();
    }

    @Override
    public void addMoney(PlayerMoney playerMoney, int amount) {
        Money.super.addMoney(playerMoney, amount);
    }

    @Override
    public void removeMoney(PlayerMoney playerMoney, int amount) {
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