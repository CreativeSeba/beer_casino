package game;

public class Camera {
    private int width, height, margin;
    private int x, y;

    private static final int WALL_THICKNESS = 50;
    private static final int WALL_RADIUS = 500;
    private static final int SPAWN_X = 400;
    private static final int SPAWN_Y = 300;

    public Camera(int width, int height, int margin) {
        this.width = width;
        this.height = height;
        this.margin = margin;
    }

    public void update(Player player) {
        x = player.getX() - width / 2;
        y = player.getY() - height / 2;

        // Clamp the camera position to the wall boundaries
        int minX = SPAWN_X - WALL_RADIUS - WALL_THICKNESS;
        int maxX = SPAWN_X + WALL_RADIUS + WALL_THICKNESS - width;
        int minY = SPAWN_Y - WALL_RADIUS - WALL_THICKNESS;
        int maxY = SPAWN_Y + WALL_RADIUS + WALL_THICKNESS - height;

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