package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements ActionListener {
    private Timer timer;
    private Player player;
    private Set<Integer> pressedKeys;
    private Camera camera;
    private BufferedImage backgroundImage;
    private List<SmallSlotMachine> slotMachines;
    private List<Rectangle> slotMachineAreas;
    private boolean isSlotMachineActive = false;

    private static final int WALL_THICKNESS = 50;
    private static final int WALL_RADIUS = 500;
    private static final int SPAWN_X = 400;
    private static final int SPAWN_Y = 400;


    public GamePanel() {
        setPreferredSize(new Dimension(SPAWN_X*2, SPAWN_Y*2));
        setFocusable(true);

        player = new Player(0, 0, 0, 300); // Increase speed to 300
        player = new Player(SPAWN_X - player.getWidth() / 2, SPAWN_Y - player.getHeight() / 2, 100, 300); // Set correct position with increased speed

        pressedKeys = new HashSet<>();
        camera = new Camera(SPAWN_X*2, SPAWN_Y*2, SPAWN_X, SPAWN_Y, WALL_THICKNESS, WALL_RADIUS);

        try {
            backgroundImage = ImageIO.read(new File("src/game/graphics/floor.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        slotMachines = new ArrayList<>();
        slotMachineAreas = new ArrayList<>();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                pressedKeys.add(e.getKeyCode());
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    for (int i = 0; i < slotMachineAreas.size(); i++) {
                        Rectangle slotMachineArea = slotMachineAreas.get(i);
                        if (slotMachineArea.contains(player.getX(), player.getY())) {
                            SmallSlotMachine slotMachine = slotMachines.get(i);

                            if (slotMachine != null) {
                                if (isSlotMachineActive) {
                                    // Deactivate the slot machine
                                    remove(slotMachine);
                                    isSlotMachineActive = false;
                                } else {
                                    // Activate the slot machine
                                    add(slotMachine);
                                    isSlotMachineActive = true;

                                    // Teleport the player to the center of the slot machine area
                                    player.setX(slotMachineArea.x + slotMachineArea.width / 2);
                                    player.setY(slotMachineArea.y + slotMachineArea.height / 2);
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
            public void keyReleased(KeyEvent e) {
                pressedKeys.remove(e.getKeyCode());
            }
        });

        timer = new Timer(16, this); // roughly 60 FPS
        timer.start();
    }

    public void addSmallSlotMachine(int x, int y) {
        int tx = SPAWN_X + x - player.getWidth() / 2;
        int ty = SPAWN_Y - y - player.getHeight() / 2;
        SmallSlotMachine slotMachine = new SmallSlotMachine(3, tx, ty);
        slotMachines.add(slotMachine);
        slotMachineAreas.add(new Rectangle(tx, ty, 100, 100));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            int bgWidth = backgroundImage.getWidth();
            int bgHeight = backgroundImage.getHeight();
            int offsetX = (camera.getX() % bgWidth + bgWidth) % bgWidth;
            int offsetY = (camera.getY() % bgHeight + bgHeight) % bgHeight;

            for (int x = -offsetX; x < getWidth(); x += bgWidth) {
                for (int y = -offsetY; y < getHeight(); y += bgHeight) {
                    g.drawImage(backgroundImage, x, y, this);
                }
            }
        }

        // Draw the walls
        g.setColor(Color.BLACK);
        g.fillRect(SPAWN_X - WALL_RADIUS - WALL_THICKNESS - camera.getX(), SPAWN_Y - WALL_RADIUS - WALL_THICKNESS - camera.getY(), WALL_THICKNESS, 2 * WALL_RADIUS + 2 * WALL_THICKNESS);
        g.fillRect(SPAWN_X + WALL_RADIUS - camera.getX(), SPAWN_Y - WALL_RADIUS - WALL_THICKNESS - camera.getY(), WALL_THICKNESS, 2 * WALL_RADIUS + 2 * WALL_THICKNESS);
        g.fillRect(SPAWN_X - WALL_RADIUS - WALL_THICKNESS - camera.getX(), SPAWN_Y - WALL_RADIUS - WALL_THICKNESS - camera.getY(), 2 * WALL_RADIUS + 2 * WALL_THICKNESS, WALL_THICKNESS);
        g.fillRect(SPAWN_X - WALL_RADIUS - WALL_THICKNESS - camera.getX(), SPAWN_Y + WALL_RADIUS - camera.getY(), 2 * WALL_RADIUS + 2 * WALL_THICKNESS, WALL_THICKNESS);

        // Draw the slot machine areas
        g.setColor(Color.BLUE);
        for (Rectangle area : slotMachineAreas) {
            g.fillRect(area.x - camera.getX(), area.y - camera.getY(), area.width, area.height);
        }

        camera.update(player);
        player.draw(g, camera.getX(), camera.getY()); // Draw the player entity relative to the camera
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