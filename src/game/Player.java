package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Player extends Variables {
    private int x, y, size;
    private int speed;
    private Image playerImage;


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

    public void moveUp(double deltaTime) {
        y -= speed * deltaTime;
        if(y<= spawnY - wallRadius +size/2) {
            y = spawnY - wallRadius +size/2;
        }
    }

    public void moveDown(double deltaTime) {
        y += speed * deltaTime;
        if(y>= spawnY + wallRadius -size/2) {
            y = spawnY + wallRadius -size/2;
        }
    }

    public void moveLeft(double deltaTime) {
        x -= speed * deltaTime;
        if(x<= spawnX - wallRadius +size/2) {
            x = spawnX - wallRadius +size/2;
        }
    }

    public void moveRight(double deltaTime) {
        x += speed * deltaTime;
        if(x>= spawnX + wallRadius -size/2) {
            x = spawnX + wallRadius -size/2;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        int newSize = size; // Increase the size by a factor of 20
        int drawX = x - camera.getX() - newSize / 2; // Center the image horizontally
        int drawY = y - camera.getY() - newSize / 2; // Center the image vertically
        if (playerImage != null) {
            g.drawImage(playerImage, drawX, drawY, newSize, newSize, null);
        } else {
            g.setColor(Color.RED);
            g.fillRect(drawX, drawY, newSize, newSize);
        }
    }
}