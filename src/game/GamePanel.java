package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.HashSet;


public class GamePanel extends Variables implements ActionListener {

    public GamePanel(int WIDTH, int HEIGHT, int WALL_RADIUS, int WALL_THICKNESS, int money) {
        Variables.width = WIDTH;
        Variables.height = HEIGHT;
        Variables.wallRadius = WALL_RADIUS;
        Variables.wallThickness = WALL_THICKNESS;
        spawnX = WIDTH / 2;
        spawnY = HEIGHT / 2;
        camera = new Camera();
        player = new Player(spawnX, spawnY, 100, 300); // Set correct position with increased speed
        PlayerMoney.money = money;
        floor = new Floor();
        wall = new Wall();
        slotMachines = new ArrayList<>();
        slotMachineAreas = new ArrayList<>();
        pressedKeys = new HashSet<>();

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);

        try {
            backgroundImage = ImageIO.read(new File("src/game/graphics/floor.jpg"));
            smallSlotMachineImage = ImageIO.read(new File("src/game/graphics/small-slot-machine.png"));
            bigSlotMachineImage = ImageIO.read(new File("src/game/graphics/big-slot-machine.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent key) {
                pressedKeys.add(key.getKeyCode());
                if (key.getKeyCode() == KeyEvent.VK_E) {
                    // SMALL SLOT MACHINE
                    for (int i = 0; i < slotMachineAreas.size(); i++) {
                        Rectangle slotMachineArea = slotMachineAreas.get(i);
                        if (slotMachineArea.contains(player.getX(), player.getY())) {
                            SlotMachine slotMachine = slotMachines.get(i);

                            if (slotMachine != null) {
                                if (isSlotMachineActive) {
                                    // Deactivate the slot machine
                                    remove(slotMachine);
                                    slotMachine.resetNumbers();
                                    isSlotMachineActive = false;
                                    activeSlotMachine = null;
                                } else {
                                    // Activate the slot machine
                                    add(slotMachine);
                                    isSlotMachineActive = true;
                                    activeSlotMachine = slotMachine;

                                    // Teleport the player to the center of the slot machine area
                                    player.setX(slotMachineArea.x + slotMachineArea.width / 2);
                                    player.setY(slotMachineArea.y + slotMachineArea.height / 2 + player.getHeight() / 4);
                                }
                                revalidate();
                                repaint();
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

        timer = new Timer(16, this); // roughly 60 FPS
        timer.start();
    }

    // In GamePanel.java
    public void addSlotMachine(int x, int y, Slots slotType) {
        int slotMachineWidth = player.getWidth() + player.getWidth() / 4;
        int slotMachineHeight = player.getHeight() + player.getHeight() / 4;
        x = spawnX + x - slotMachineWidth / 2;
        y = spawnY - y - slotMachineHeight / 2;
        SlotMachine slotMachine = switch (slotType) {
            case SMALL -> new SmallSlotMachine(x, y);
            case BIG -> new BigSlotMachine(x, y);
            default -> null;
        };
        slotMachines.add(slotMachine);
        slotMachineAreas.add(new Rectangle(x, y, slotMachineWidth, slotMachineHeight));

        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        floor.paintComponent(g);
        wall.paintComponent(g);

        int i = 0;
        for (Rectangle area : slotMachineAreas) {
            if(slotMachines.get(i).type == Slots.SMALL){
                if (smallSlotMachineImage != null) {
                    int imageWidth = (int) (area.width * 1.5);
                    int imageHeight = (int) (area.height * 1.5);
                    int centerX = area.x + area.width / 2 - camera.getX();
                    int centerY = area.y + area.height / 2 - camera.getY();
                    g.drawImage(smallSlotMachineImage, centerX - imageWidth / 2, centerY - imageHeight / 2, imageWidth, imageHeight, this);
                } else {
                    g.setColor(Color.GREEN);
                    g.fillRect(area.x - camera.getX(), area.y - camera.getY(), area.width, area.height);
                }
            }
            else if(slotMachines.get(i).type == Slots.BIG){
                if (bigSlotMachineImage != null) {
                    int imageWidth = (int) (area.width * 1.5);
                    int imageHeight = (int) (area.height * 1.5);
                    int centerX = area.x + area.width / 2 - camera.getX();
                    int centerY = area.y + area.height / 2 - camera.getY();
                    g.drawImage(bigSlotMachineImage, centerX - imageWidth / 2, centerY - imageHeight / 2, imageWidth, imageHeight, this);
                } else {
                    g.setColor(Color.RED);
                    g.fillRect(area.x - camera.getX(), area.y - camera.getY(), area.width, area.height);
                }
            }
            i++;
        }

        // Draw the player's money in the top right corner
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        String moneyText = "Chips: " + PlayerMoney.money;
        int textWidth = g.getFontMetrics().stringWidth(moneyText);
        g.drawString(moneyText, getWidth() - textWidth - 10, 30);

        camera.update(player);
        player.paintComponent(g); // Draw the player entity relative to the camera
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isSlotMachineActive) {
            updatePlayerPosition();
        }
        repaint();
    }

    private void updatePlayerPosition() {
        double deltaTime = 0.016; // Assuming 60 FPS, so each frame is roughly 0.016 seconds
        if (pressedKeys.contains(KeyEvent.VK_W)) {
            player.moveUp(deltaTime);
        }
        if (pressedKeys.contains(KeyEvent.VK_S)) {
            player.moveDown(deltaTime);
        }
        if (pressedKeys.contains(KeyEvent.VK_A)) {
            player.moveLeft(deltaTime);
        }
        if (pressedKeys.contains(KeyEvent.VK_D)) {
            player.moveRight(deltaTime);
        }
    }
}