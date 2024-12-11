package game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Variables extends JPanel {
    protected static Set<Integer> pressedKeys;
    protected static int wallThickness, wallRadius, spawnX, spawnY, sWidth, sHeight, playerSize, playerSpeed;
    protected static Camera camera;
    protected static Player player;
    protected static List<Entity> entities;
    protected static List<Rectangle> entityAreas;
    protected static boolean isActiveEntity = false;
}
