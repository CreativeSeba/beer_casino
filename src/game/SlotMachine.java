package game;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
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
    protected Color color;
    private final static int slotMachineWidth = player.getWidth() + player.getWidth() / 4;
    private final static int slotMachineHeight = player.getHeight() + player.getHeight() / 4;

    public SlotMachine(int x, int y, int numberOfSlots, Slots type, int loose, BufferedImage image, Color color) {
        this.numberOfSlots = numberOfSlots;
        this.type = type;
        this.image = image;
        this.color = color;
        this.x = spawnX + x - slotMachineWidth / 2;
        this.y = spawnY - y - slotMachineHeight / 2;
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
        slotMachines.add(this);
        slotMachineAreas.add(new Rectangle(x, y, slotMachineWidth, slotMachineHeight));

        revalidate();
        repaint();
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