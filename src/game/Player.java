package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class Player extends Variables {
    private int x;
    private int y;
    private final int size;
    private final int speed;
    private static final Image playerImage;
    static{
        try {
            playerImage = ImageIO.read(new File("src/game/graphics/player.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Player(int x, int y, int size, int speed) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speed = speed;
    }

    public int getWidth() {
        return size;
    }

    public int getHeight() {
        return size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    private void move(double dx, double dy) {
        x += (int) dx;
        y += (int) dy;
        int minX = spawnX - wallRadius + size / 2;
        int maxX = spawnX + wallRadius - size / 2;
        int minY = spawnY - wallRadius + size / 2;
        int maxY = spawnY + wallRadius - size / 2;
        //LEFT
        if (x <= minX) {
            x = minX;
        }
        //RIGHT
        if (x >= maxX) {
            x = maxX;
        }
        //UP
        if (y <= minY) {
            y = minY;
        }
        //DOWN
        if (y >= maxY) {
            y = maxY;
        }
    }
    protected void updatePlayerPosition() {
        double deltaTime = 0.016;
        double speed = player.getSpeed();
        double dx = 0;
        double dy = 0;

        if (pressedKeys.contains(KeyEvent.VK_W)) {
            dy -= 1;
        }
        if (pressedKeys.contains(KeyEvent.VK_S)) {
            dy += 1;
        }
        if (pressedKeys.contains(KeyEvent.VK_A)) {
            dx -= 1;
        }
        if (pressedKeys.contains(KeyEvent.VK_D)) {
            dx += 1;
        }

        double length = Math.sqrt(dx * dx + dy * dy);
        if (length != 0) {
            dx = (dx / length) * speed * deltaTime;
            dy = (dy / length) * speed * deltaTime;
        }

        player.move(dx, dy);
     /*   double currentSpeed = Math.sqrt(dx * dx + dy * dy) / deltaTime;
        System.out.println("Player speed: " + currentSpeed + " pixels per second");*/
    }
    @Override
    protected void paintComponent(Graphics g) {
        int drawX = x - camera.getX() - size / 2;
        int drawY = y - camera.getY() - size / 2;
        g.drawImage(playerImage, drawX, drawY, size, size, null);
    }
}