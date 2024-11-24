package game;

import java.awt.*;

public class Wall extends Variables {
    public Wall() {}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(spawnX - wallRadius - wallThickness - camera.getX(), spawnY - wallRadius - wallThickness - camera.getY(), wallThickness, 2 * wallRadius + 2 * wallThickness);
        g.fillRect(spawnX + wallRadius - camera.getX(), spawnY - wallRadius - wallThickness - camera.getY(), wallThickness, 2 * wallRadius + 2 * wallThickness);
        g.fillRect(spawnX - wallRadius - wallThickness - camera.getX(), spawnY - wallRadius - wallThickness - camera.getY(), 2 * wallRadius + 2 * wallThickness, wallThickness);
        g.fillRect(spawnX - wallRadius - wallThickness - camera.getX(), spawnY + wallRadius - camera.getY(), 2 * wallRadius + 2 * wallThickness, wallThickness);
    }
}