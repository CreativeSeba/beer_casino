package game;

import java.awt.*;

public class SmallSlotMachine extends SlotMachine{
    static int x;
    static int y;
    private int numberOfSlots;
    private PlayerMoney playerMoney = GamePanel.playerMoney;
    public SmallSlotMachine(int numberOfSlots, int x, int y) {
        super(numberOfSlots, Slots.SMALL, 100);
        this.x = x;
        this.y = y;
        this.numberOfSlots = numberOfSlots;
        setBackground(Color.GREEN);

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
                addMoney(playerMoney, 5000);
                System.out.println("Wygrana small");
            }
        }
        if (win) {
            addMoney(playerMoney, 1000);
            System.out.println("Wygrana z cando");
        }
    }
}
