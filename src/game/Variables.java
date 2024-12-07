package game;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

public abstract class Variables extends JPanel {
    protected static Timer timer;
    protected static Set<Integer> pressedKeys;
    protected static int wallThickness, wallRadius, spawnX, spawnY, sWidth, sHeight, playerSize, playerSpeed;
    protected static Camera camera;
    protected static Player player;
    protected static Wall wall;
    protected static Floor floor;
    protected static Bar bar;
    protected static List<SlotMachine> slotMachines;
    protected static List<Rectangle> slotMachineAreas;
    protected static boolean isSlotMachineActive = false;
    protected static SlotMachine activeSlotMachine;
}
