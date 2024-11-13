package game;

import jdk.dynalink.NamedOperation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LargeSlotMachine extends SlotMachine implements Money{
    static int x;
    static int y;
    public LargeSlotMachine(int numberOfSlots, int x, int y, PlayerMoney playerMoney) {
        super(numberOfSlots);
        this.x = x;
        this.y = y;
        // Pass the adjusted x and y to the SlotMachine constructor
        setPreferredSize(new Dimension(200, 200));
        setBackground(Color.RED);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                removeMoney(playerMoney, 100);
                System.out.println(playerMoney.money);
                spin();
                repaint();
            }
        });
    }
    @Override
    public void addMoney(PlayerMoney playerMoney){
        playerMoney.money+=100;
    }
    @Override
    public void removeMoney(PlayerMoney playerMoney, int amount){
        Money.super.removeMoney(playerMoney, amount);
    }
}
