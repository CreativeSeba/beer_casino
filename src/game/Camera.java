package game;

public class Camera extends Variables {
    private static int x, y;

    public Camera() {}

    public void update(Player player) {
        x = player.getX() - width / 2;
        y = player.getY() - height / 2;

        // Clamp the camera position to the wall boundaries
        int minX = spawnX - wallRadius - wallThickness;
        int maxX = spawnX + wallRadius + wallThickness - width;
        int minY = spawnY - wallRadius - wallThickness;
        int maxY = spawnY + wallRadius + wallThickness - height;

        if (x <= minX) {
            x = minX;
        }
        if (x >= maxX) {
            x = maxX;
        }
        if (y <= minY) {
            y = minY;
        }
        if (y >= maxY) {
            y = maxY;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}