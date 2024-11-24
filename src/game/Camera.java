package game;

public class Camera extends Variables {
    private static int x, y;

    public Camera() {

    }

    public void update(Player player) {
        x = player.getX() - WIDTH / 2;
        y = player.getY() - HEIGHT / 2;

        // Clamp the camera position to the wall boundaries
        int minX = SPAWN_X - WALL_RADIUS - WALL_THICKNESS;
        int maxX = SPAWN_X + WALL_RADIUS + WALL_THICKNESS - WIDTH;
        int minY = SPAWN_Y - WALL_RADIUS - WALL_THICKNESS;
        int maxY = SPAWN_Y + WALL_RADIUS + WALL_THICKNESS - HEIGHT;

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