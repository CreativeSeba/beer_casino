package game;

public class Camera {
    private int width, height;
    private int x, y;

    private int wallThickness;
    private int wallRadius;
    private static int spawnX;
    private static int spawnY;

    public Camera(int width, int height, int spawnX, int spawnY, int wallThickness, int wallRadius) {
        this.width = width;
        this.height = height;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.wallThickness = wallThickness;
        this.wallRadius = wallRadius;
    }

    public void update(Player player) {
        x = player.getX() - width / 2;
        y = player.getY() - height / 2;

        // Clamp the camera position to the wall boundaries
        int minX = spawnX - wallRadius - wallThickness;
        int maxX = spawnX + wallRadius + wallThickness - width;
        int minY = spawnY - wallRadius - wallThickness;
        int maxY = spawnY + wallRadius + wallThickness - height;

        if (x < minX) {
            x = minX;
        }
        if (x > maxX) {
            x = maxX;
        }
        if (y < minY) {
            y = minY;
        }
        if (y > maxY) {
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