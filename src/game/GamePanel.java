package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;

public class GamePanel extends Variables implements ActionListener {
    private static GamePanel instance;
    private String resultMessage;
    private long messageEndTime;
    private Color messageColor;
    private String paidMessage;
    private long paidMessageEndTime;

    public GamePanel(int sWidth, int sHeight, int wallRadius, int wallThickness, int money, int playerSize, int playerSpeed) {
        instance = this;
        Variables.sWidth = sWidth;
        Variables.sHeight = sHeight;
        Variables.wallRadius = wallRadius;
        Variables.wallThickness = wallThickness;
        Variables.playerSize = playerSize;
        Variables.playerSpeed = playerSpeed;
        spawnX = sWidth / 2;
        spawnY = sHeight / 2;
        camera = new Camera();
        player = new Player(spawnX, spawnY, playerSize, playerSpeed);
        floor = new Floor();
        wall = new Wall();
        bar = new Bar();
        slotMachines = new ArrayList<>();
        entityAreas = new ArrayList<>();
        pressedKeys = new HashSet<>();
        PlayerMoney.money = money;
        //SMALL
        new SmallSlotMachine(-200, 0);
        //BIG
        new BigSlotMachine(200, 0);

        setPreferredSize(new Dimension(sWidth, sHeight));
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent key) {
                pressedKeys.add(key.getKeyCode());
                if (key.getKeyCode() == KeyEvent.VK_E) {
                    for (int i = 0; i < entityAreas.size(); i++) {
                        Rectangle slotMachineArea = entityAreas.get(i);
                        if (slotMachineArea.contains(player.getX(), player.getY())) {
                            SlotMachine slotMachine = slotMachines.get(i);
                            if (slotMachine != null) {
                                slotMachine.interaction(player);
                                break;
                            }
                        }
                    }

                }
            }

            @Override
            public void keyReleased(KeyEvent key) {
                pressedKeys.remove(key.getKeyCode());
            }
        });
        timer = new Timer(16, this);
        timer.start();
    }
    public static GamePanel getInstance() {
        return instance;
    }

    // Update the paintComponent method in the GamePanel class
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        floor.paintComponent(g);
        wall.paintComponent(g);
        bar.paintComponent(g);
        for (SlotMachine slotMachine : slotMachines) {
            slotMachine.draw(g);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        String moneyText = "Chips: " + PlayerMoney.money;
        int textWidth = g.getFontMetrics().stringWidth(moneyText);
        g.drawString(moneyText, getWidth() - textWidth - 10, 30);

        int messageY = 50; // Initial position below the chips text

        // Draw the result message if it is still within the display duration
        if (System.currentTimeMillis() < messageEndTime) {
            g.setFont(new Font("Arial", Font.BOLD, 16));
            int messageWidth = g.getFontMetrics().stringWidth(resultMessage);
            int messageX = getWidth() - messageWidth - 10;
            g.setColor(messageColor);
            g.drawString(resultMessage, messageX, messageY);
            messageY += 20; // Move the Y position down for the next message
        }

        // Draw the paid message if it is still within the display duration
        if (System.currentTimeMillis() < paidMessageEndTime) {
            g.setFont(new Font("Arial", Font.BOLD, 16));
            int messageWidth = g.getFontMetrics().stringWidth(paidMessage);
            int messageX = getWidth() - messageWidth - 10;
            g.setColor(Color.RED);
            g.drawString(paidMessage, messageX, messageY);
        }

        camera.update();
        player.paintComponent(g);
    }
    public void setResultMessage(String message, Color color) {
        this.resultMessage = message;
        this.messageEndTime = System.currentTimeMillis() + 1000;
        this.messageColor = color;
        repaint();
    }
    public void setPaidMessage(String message) {
        this.paidMessage = message;
        this.paidMessageEndTime = System.currentTimeMillis() + 1000;
        repaint();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isActiveEntity) {
            player.updatePlayerPosition();
        }
        repaint();
    }
}