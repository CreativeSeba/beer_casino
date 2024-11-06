package game;

public class Camera {
    private int width, height;
    private int x, y;

    private static final int WALL_THICKNESS = 50;
    private static final int WALL_RADIUS = 500;
    private static int spawnX;
    private static int spawnY;

    public Camera(int width, int height, int spawnX, int spawnY) {
        this.width = width;
        this.height = height;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
    }

    public void update(Player player) {
        x = player.getX() - width / 2;
        y = player.getY() - height / 2;

        // Clamp the camera position to the wall boundaries
        int minX = spawnX - WALL_RADIUS - WALL_THICKNESS;
        int maxX = spawnX + WALL_RADIUS + WALL_THICKNESS - width;
        int minY = spawnY - WALL_RADIUS - WALL_THICKNESS;
        int maxY = spawnY + WALL_RADIUS + WALL_THICKNESS - height;

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