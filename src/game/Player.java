package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Player extends Variables {
    private int x, y, size;
    private int speed;
    private static Image playerImage;

    public Player(int x, int y, int size, int speed) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speed = speed;
        try {
            playerImage = ImageIO.read(new File("src/game/graphics/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void move(double dx, double dy) {
        x += dx;
        y += dy;
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
    @Override
    protected void paintComponent(Graphics g) {
        int newSize = size;
        int drawX = x - camera.getX() - newSize / 2;
        int drawY = y - camera.getY() - newSize / 2;
        g.drawImage(playerImage, drawX, drawY, newSize, newSize, null);
    }
}