package game;

import java.awt.*;

public class BigSlotMachine extends SlotMachine implements Money {
    private static final int numberOfSlots = 5, loose = 400;

    public BigSlotMachine(int x, int y) {
        super(numberOfSlots, Slots.BIG, loose);
        this.x = x;
        this.y = y;
        setBackground(Color.RED);
    }

    @Override
    public void placeBets(int amount) {
        Money.super.placeBets(amount);
        boolean win = true;
        int i = -1;
        for (Pair<Integer, Integer> pair : combinations) {
            i += pair.second;
            if (i != 0 && i % 2 == 0 && numbers[i - 2] != pair.first) {
                win = false;
            } else if (pair.second == numberOfSlots) {
                PlayerMoney.money += 5000;
                System.out.println("Wygrana small");
            }
        }
        if (win) {
            PlayerMoney.money += 1000;
            System.out.println("Wygrana z cando");
        }
    }
//    @Override
//    public void interaction(KeyEvent key) {
//
//    }
//    @Override
//    public void addSlotMachine(int x, int y){
//
//    }
}
