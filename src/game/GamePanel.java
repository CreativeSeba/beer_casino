package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class GamePanel extends JPanel implements ActionListener {
    private Timer timer;
    private Entity player;
    private Set<Integer> pressedKeys;
    private Camera camera;
    private BufferedImage backgroundImage;

    private static final int WALL_THICKNESS = 50;
    private static final int WALL_RADIUS = 500;
    private static final int SPAWN_X = 400;
    private static final int SPAWN_Y = 300;

    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        setFocusable(true);

        player = new Entity(SPAWN_X, SPAWN_Y, 5);
        pressedKeys = new HashSet<>();
        camera = new Camera(800, 600, 100); // 100-pixel margin

        try {
            backgroundImage = ImageIO.read(new File("src/game/graphics/floor.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                pressedKeys.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                pressedKeys.remove(e.getKeyCode());
            }
        });

        timer = new Timer(16, this); // roughly 60 FPS
        timer.start();
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

        camera.update(player);
        player.draw(g, camera.getX(), camera.getY()); // Draw the player entity relative to the camera
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        updatePlayerPosition();
        repaint();
    }

    private void updatePlayerPosition() {
        if (pressedKeys.contains(KeyEvent.VK_W)) {
            player.moveUp();
        }
        if (pressedKeys.contains(KeyEvent.VK_S)) {
            player.moveDown();
        }
        if (pressedKeys.contains(KeyEvent.VK_A)) {
            player.moveLeft();
        }
        if (pressedKeys.contains(KeyEvent.VK_D)) {
            player.moveRight();
        }
    }
}