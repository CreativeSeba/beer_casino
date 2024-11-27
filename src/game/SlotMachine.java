package game;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.List;


public abstract class SlotMachine extends Variables {
    protected int[] numbers;
    protected int x, y;
    protected static List<Pair<Integer, Integer>> combinations;
    private final Random random = new Random();
    protected Slots type;
    private final String labelText;
    private final int numberOfSlots;
    protected BufferedImage image;
    private int loose;
    protected Color color;

    public SlotMachine(int numberOfSlots, Slots type, int loose, BufferedImage image, Color color) {
        this.numberOfSlots = numberOfSlots;
        this.type = type;
        this.loose = loose;
        this.image = image;
        this.color = color;
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
        setPreferredSize(new Dimension(200, 200));
        //addSlotMachine(x, y);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (PlayerMoney.money > 0) {
                    spin();

                    for (Pair<Integer, Integer> pair : combinations) {
                        System.out.println(pair.first + " " + pair.second);
                    }
                    placeBets(loose);
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
            numbers[i] = random.nextInt(10);
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

    protected abstract void placeBets(int amount);

    //public abstract void addSlotMachine(int x, int y) ;

    public void resetNumbers() {
        Arrays.fill(numbers, 0);
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