package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SmallSlotMachine extends SlotMachine implements Money {
    static int x;
    static int y;
    public SmallSlotMachine(int numberOfSlots, int x, int y) {
        super(numberOfSlots);
        this.x = x;
        this.y = y;
         // Pass the adjusted x and y to the SlotMachine constructor
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


    @Override
    public void addMoney(int amount) {
        // Implement adding money logic here (if needed)
    }

    @Override
    public void removeMoney(int amount) {
        // Implement removing money logic here (if needed)
    }
}
