package game;

public class Camera extends Variables {
    private static int x, y;

    public Camera() {
    }

    public void update() {
        x = player.getX() - spawnX;
        y = player.getY() - spawnY;

        int minX = spawnX - wallRadius - wallThickness;
        int maxX = spawnX + wallRadius + wallThickness - sWidth;
        int minY = spawnY - wallRadius - wallThickness;
        int maxY = spawnY + wallRadius + wallThickness - sHeight;

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