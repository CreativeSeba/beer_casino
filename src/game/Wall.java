package game;

import java.awt.*;
import javax.swing.JComponent;

public class Wall extends Variables {
    public Wall() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(SPAWN_X - WALL_RADIUS - WALL_THICKNESS - camera.getX(), SPAWN_Y - WALL_RADIUS - WALL_THICKNESS - camera.getY(), WALL_THICKNESS, 2 * WALL_RADIUS + 2 * WALL_THICKNESS);
        g.fillRect(SPAWN_X + WALL_RADIUS - camera.getX(), SPAWN_Y - WALL_RADIUS - WALL_THICKNESS - camera.getY(), WALL_THICKNESS, 2 * WALL_RADIUS + 2 * WALL_THICKNESS);
        g.fillRect(SPAWN_X - WALL_RADIUS - WALL_THICKNESS - camera.getX(), SPAWN_Y - WALL_RADIUS - WALL_THICKNESS - camera.getY(), 2 * WALL_RADIUS + 2 * WALL_THICKNESS, WALL_THICKNESS);
        g.fillRect(SPAWN_X - WALL_RADIUS - WALL_THICKNESS - camera.getX(), SPAWN_Y + WALL_RADIUS - camera.getY(), 2 * WALL_RADIUS + 2 * WALL_THICKNESS, WALL_THICKNESS);
    }
}