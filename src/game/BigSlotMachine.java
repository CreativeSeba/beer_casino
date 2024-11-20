package game;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BigSlotMachine extends SlotMachine {
    private static int x;
    private static int y;
    private int numberOfSlots;
    private PlayerMoney playerMoney = GamePanel.playerMoney;

    public BigSlotMachine(int numberOfSlots, int x, int y) {
        super(5, Slots.BIG, 400);
        this.x = x;
        this.y = y;
        this.numberOfSlots = numberOfSlots;
        setBackground(Color.RED);
    }

    @Override
    protected void setCombinations() {
        boolean win = true;
        int i = -1;
        for (Pair<Integer, Integer> pair : combinations) {
            i += pair.second;
            if (i != 0 && i % 2 == 0 &&  numbers[i-2] != pair.first) {
                win = false;
            } else if (pair.second == numberOfSlots) {
                addMoney(playerMoney, 10000);
                System.out.println("Wygrana big");
            }
        }
        if (win) {
            addMoney(playerMoney, 5000);
            System.out.println("Wygrana z cando");
        }
    }
}
