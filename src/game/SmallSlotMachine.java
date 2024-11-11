// src/game/SmallSlotMachine.java
package game;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SmallSlotMachine extends SlotMachine implements Money {
    private int x;
    private int y;
    static int spawnX;
    static int spawnY;

    public SmallSlotMachine(int numberOfSlots, int spawnX, int spawnY) {
        this(numberOfSlots, 0, 0, spawnX, spawnY); // Default to spawn location
    }

    public SmallSlotMachine(int numberOfSlots, int x, int y, int spawnX, int spawnY) {
        super(numberOfSlots, x, y);
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.x = x;
        this.y = y;
        setPreferredSize(new Dimension(200, 200));
        setBackground(Color.GREEN);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                spin();
                repaint();
            }
        });
    }

    public int getCustomX() {
        return x;
    }

    public int getCustomY() {
        return y;
    }

    @Override
    public void addMoney(int amount) {

    }

    @Override
    public void removeMoney(int amount) {

    }
}