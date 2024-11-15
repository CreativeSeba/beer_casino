package game;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BigSlotMachine extends SlotMachine {
    static int x;
    static int y;
    public BigSlotMachine(int numberOfSlots, int x, int y) {
        super(numberOfSlots, Slots.BIG, 100);
        this.x = x;
        this.y = y;
        // Pass the adjusted x and y to the SlotMachine constructor
        setPreferredSize(new Dimension(200, 200));
        setBackground(Color.RED);
    }
}
