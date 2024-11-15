package game;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BigSlotMachine extends SlotMachine implements Money{
    static int x;
    static int y;
    public BigSlotMachine(int numberOfSlots, int x, int y, PlayerMoney playerMoney) {
        super(numberOfSlots, "Big Slot Machine");
        this.x = x;
        this.y = y;
        // Pass the adjusted x and y to the SlotMachine constructor
        setPreferredSize(new Dimension(200, 200));
        setBackground(Color.RED);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                removeMoney(playerMoney, 100);
                spin();
                repaint();
            }
        });
    }
    @Override
    public void addMoney(PlayerMoney playerMoney, int amount){
        Money.super.addMoney(playerMoney, amount);
    }
    @Override
    public void removeMoney(PlayerMoney playerMoney, int amount){
        Money.super.removeMoney(playerMoney, amount);
    }
}
