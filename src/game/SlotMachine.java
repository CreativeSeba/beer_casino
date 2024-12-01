package game;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.List;

public abstract class SlotMachine extends Variables{
    protected int[] numbers;
    protected int x, y;
    protected static List<Pair<Integer, Integer>> combinations;
    private final Random random = new Random();
    protected Slots type;
    protected String labelText;
    private final int numberOfSlots;
    protected BufferedImage image;
    protected Color color;
    protected int loose;
    private final static int width = 200, height = 200;

    public SlotMachine(int x, int y, int numberOfSlots, Slots type, int loose, BufferedImage image, Color color, String labelText) {
        this.numberOfSlots = numberOfSlots;
        this.type = type;
        this.image = image;
        this.color = color;
        this.loose = loose;
        this.labelText = labelText;
        this.x = spawnX + x - playerSize;
        this.y = spawnY - y - playerSize;

        slotMachines.add(this);
        slotMachineAreas.add(new Rectangle(this.x, this.y, width, height));

        numbers = new int[numberOfSlots];
        combinations = new ArrayList<>();

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
    protected abstract void placeBets(int amount);
    public void interact(Player player) {
        if (isSlotMachineActive) {
            GamePanel.getInstance().remove(this);
            resetNumbers();
            isSlotMachineActive = false;
            activeSlotMachine = null;
        } else {
            setBounds(new Rectangle(x, y, width, height));
            GamePanel.getInstance().add(this);
            isSlotMachineActive = true;
            activeSlotMachine = this;

            player.setX(x + width / 2);
            player.setY(y + height / 2 + player.getHeight() / 4);
        }
        GamePanel.getInstance().revalidate();
        GamePanel.getInstance().repaint();
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

    protected void resetNumbers() {
        Arrays.fill(numbers, 0);
        repaint();
    }
    protected void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, x - camera.getX(), y - camera.getY(), width, height, null);
        } else {
            g.setColor(color);
            g.fillRect(x - camera.getX(), y - camera.getY(), width, height);
        }
        if (!isSlotMachineActive && player.getX()>=x && player.getX()<=x+width && player.getY()>=y && player.getY()<=y+height) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            String message = "Press E to play";
            int textWidth = g.getFontMetrics().stringWidth(message);
            g.drawString(message, x + width / 2 - textWidth / 2 - camera.getX(), y - height / 8 - camera.getY());
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setSize(width, height);
        setLocation(spawnX-width/2, 0);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g.getFontMetrics();
        int labelX = (width - fm.stringWidth(labelText)) / 2;
        int labelY = fm.getHeight();
        g.drawString(labelText, labelX, labelY);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        fm = g.getFontMetrics();
        int xPos = (width - (fm.stringWidth("0") * numbers.length + 20 * (numbers.length - 1))) / 2;
        int yPos = (height - fm.getHeight()) / 2 + fm.getAscent();

        for (int i = 0; i < numbers.length; i++) {
            g.drawString(String.valueOf(numbers[i]), xPos + i * (fm.stringWidth("0") + 20), yPos);
        }
    }
}