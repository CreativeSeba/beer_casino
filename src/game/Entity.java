package game;

import java.awt.*;

public class Entity {
    private int x, y;
    private int speed;

    private static final int WALL_THICKNESS = 50;
    private static final int WALL_RADIUS = 500;
    private static final int SPAWN_X = 400;
    private static final int SPAWN_Y = 300;

    public Entity(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void moveUp() {
        if (y - speed >= SPAWN_Y - WALL_RADIUS) {
            y -= speed;
        }
    }

    public void moveDown() {
        if (y + speed <= SPAWN_Y + WALL_RADIUS - WALL_THICKNESS) {
            y += speed;
        }
    }

    public void moveLeft() {
        if (x - speed >= SPAWN_X - WALL_RADIUS) {
            x -= speed;
        }
    }

    public void moveRight() {
        if (x + speed <= SPAWN_X + WALL_RADIUS - WALL_THICKNESS) {
            x += speed;
        }
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
        g.setColor(Color.BLACK);
        g.fillRect(x - offsetX, y - offsetY, 50, 50); // Draw the entity as a black square
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}