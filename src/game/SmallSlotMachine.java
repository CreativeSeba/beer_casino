package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SmallSlotMachine extends SlotMachine{
    static int x;
    static int y;
    public SmallSlotMachine(int numberOfSlots, int x, int y) {
        super(numberOfSlots, Slots.SMALL, 20);
        this.x = x;
        this.y = y;
         // Pass the adjusted x and y to the SlotMachine constructor
        setPreferredSize(new Dimension(200, 200));
        setBackground(Color.GREEN);

    }
}
