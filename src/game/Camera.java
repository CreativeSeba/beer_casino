package game;

public class Camera {
    private static int x, y;

    private static int wallThickness;
    private static int wallRadius;
    private static int spawnX;
    private static int spawnY;

    public Camera(int spawnX, int spawnY, int wallThickness, int wallRadius) {
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.wallThickness = wallThickness;
        this.wallRadius = wallRadius;
    }

    public void update(Player player) {

        x = player.getX() - spawnX;
        y = player.getY() - spawnY;

        // Clamp the camera position to the wall boundaries
        int minX = spawnX - wallRadius - wallThickness;
        int maxX = wallRadius + wallThickness - spawnX;
        int minY = spawnY - wallRadius - wallThickness;
        int maxY = wallRadius + wallThickness - spawnY;

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