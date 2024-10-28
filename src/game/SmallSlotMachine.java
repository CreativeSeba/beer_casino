// src/game/SmallSlotMachine.java
package game;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SmallSlotMachine extends SlotMachine {
    private int x;
    private int y;

    public SmallSlotMachine(int numberOfSlots) {
        this(numberOfSlots, 0, 0); // Default to spawn location
    }

    public SmallSlotMachine(int numberOfSlots, int x, int y) {
        super(numberOfSlots, x, y);
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
}