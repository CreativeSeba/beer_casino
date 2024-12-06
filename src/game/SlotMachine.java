package game;

import javax.swing.*;
import java.awt.*;
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
    private final static int wWidth = 200, wHeight = 200;
    private JButton spinButton;

    public SlotMachine(int x, int y, int numberOfSlots, Slots type, int loose, BufferedImage image, Color color, String labelText) {
        this.numberOfSlots = numberOfSlots;
        this.type = type;
        this.image = image;
        this.color = color;
        this.loose = loose;
        this.labelText = labelText;
        this.x = spawnX + x - width/2;
        this.y = spawnY - y - height/2;

        slotMachines.add(this);
        slotMachineAreas.add(new Rectangle(this.x, this.y, width, height));

        numbers = new int[numberOfSlots];
        combinations = new ArrayList<>();

        spinButton = new JButton("Spin");
        spinButton.setFocusable(false);

        spinButton.addActionListener(e -> {
            if(PlayerMoney.money < loose) {
                placeBets(loose);
            }
            else if (PlayerMoney.money > 0) {
                spin();
                for (Pair<Integer, Integer> pair : combinations) {
                    System.out.println(pair.first + " " + pair.second);
                }
                placeBets(loose);
                System.out.println("\n");
                repaint();
            } else {
                System.out.println("Przegrales");
            }
        });
        ArrayList<String> c = combinations();
        for(int i = 0; i < c.size(); i++) {
            System.out.println(c.get(i));
        }
        this.add(spinButton);
    }
    protected abstract void placeBets(int amount);
    protected abstract ArrayList<String> combinations();

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
        boolean isPlayerInArea = player.getX() >= x && player.getX() <= x + width && player.getY() >= y && player.getY() <= y + height;
        if (!isSlotMachineActive && isPlayerInArea) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            String message = "Press E to play";
            int textWidth = g.getFontMetrics().stringWidth(message);
            g.drawString(message, x + width / 2 - textWidth / 2 - camera.getX(), y + wHeight - height - camera.getY());
        }
        else if(isSlotMachineActive && isPlayerInArea){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            String message = "Press E to leave";
            int textWidth = g.getFontMetrics().stringWidth(message);
            g.drawString(message, x + width / 2 - textWidth / 2 - camera.getX(), y + wHeight - height - camera.getY());
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setSize(wWidth * 2, wHeight);
        setLocation(spawnX - wWidth / 2, 0);
        // Draw the spin button
        int buttonWidth = 100;
        int buttonHeight = 30;
        int buttonX = (wWidth - buttonWidth) / 2;
        int buttonY = (wHeight - buttonHeight) / 2 + (int) (wHeight * 0.25);

        spinButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);

        // Draw the label text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g.getFontMetrics();
        int labelX = (wWidth - fm.stringWidth(labelText)) / 2;
        int labelY = fm.getHeight();
        g.drawString(labelText, labelX, labelY);

        // Draw the combinations label
        g.drawString("Combinations: ", labelX + wWidth, labelY);

        // Calculate the center position for the combined cost text
        String costText = "Spin cost: ";
        String looseText = String.valueOf(loose);
        int combinedTextWidth = g.getFontMetrics().stringWidth(costText + looseText);
        int combinedTextX = (wWidth - combinedTextWidth) / 2;
        int costTextY = buttonY + buttonHeight + fm.getHeight();

        // Draw the cost text in black
        g.setColor(Color.BLACK);
        g.drawString(costText, combinedTextX, costTextY);

        // Calculate the position and size for the loose text
        int looseTextX = combinedTextX + g.getFontMetrics().stringWidth(costText);
        int looseTextY = costTextY;

        // Draw the gray background and thicker border around the loose text
        int padding = 1;
        int borderThickness = 2; // Adjust this value to make the border thicker
        int rectX = looseTextX - padding;
        int rectY = looseTextY - fm.getAscent() - padding;
        int rectWidth = g.getFontMetrics().stringWidth(looseText) + 2 * padding;
        int rectHeight = fm.getHeight() + 2 * padding;

        // Draw the gray background and thicker border around the loose text
        g.setColor(Color.GRAY);
        g.fillRect(rectX, rectY, rectWidth, rectHeight);

        // Draw the border around the loose text
        g.setColor(Color.BLACK);
        for (int i = 0; i < borderThickness; i++) {
            g.drawRect(rectX - i, rectY - i, rectWidth + 2 * i, rectHeight + 2 * i);
        }

        // Draw the loose text
        g.setColor(Color.WHITE);
        g.drawString(looseText, looseTextX, looseTextY);

        // Draw the numbers
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        fm = g.getFontMetrics();
        int xPos = (wWidth - (fm.stringWidth("0") * numbers.length + 20 * (numbers.length - 1))) / 2;
        int yPos = (wHeight - fm.getHeight()) / 2 + fm.getAscent();

        for (int i = 0; i < numbers.length; i++) {
            g.drawString(String.valueOf(numbers[i]), xPos + i * (fm.stringWidth("0") + 20), yPos);
        }
    }
}