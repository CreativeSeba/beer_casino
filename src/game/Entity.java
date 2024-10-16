// Entity.java
package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Entity {
    private int x, y, size;
    private Image playerImage;

    public Entity(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
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

    public void moveUp() {
        y -= 5;
    }

    public void moveDown() {
        y += 5;
    }

    public void moveLeft() {
        x -= 5;
    }

    public void moveRight() {
        x += 5;
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
        int newSize = size * 20; // Increase the size by a factor of 20
        int drawX = x - offsetX - newSize / 2 + size / 2; // Center the image horizontally
        int drawY = y - offsetY - newSize / 2 + size / 2; // Center the image vertically
        if (playerImage != null) {
            g.drawImage(playerImage, drawX, drawY, newSize, newSize, null);
        } else {
            g.setColor(Color.RED);
            g.fillRect(drawX, drawY, newSize, newSize);
        }
    }
}