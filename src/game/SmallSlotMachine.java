package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SmallSlotMachine extends SlotMachine implements Money {
    private static final int numberOfSlots = 3, loose = 100;
    private static Color color = Color.GREEN;
    private static BufferedImage smallSlotMachineImage;

    static {
        try {
            smallSlotMachineImage = ImageIO.read(new File("src/game/graphics/small-slot-machine.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SmallSlotMachine(int x, int y) {
        super(x, y, numberOfSlots, Slots.SMALL, loose, smallSlotMachineImage, color);
        setBackground(Color.GREEN);
    }

    @Override
    public void placeBets(int amount) {
        Money.super.placeBets(amount);
        boolean win = true;
        final int moneyBefore = PlayerMoney.money;
        for (Pair<Integer, Integer> pair : combinations) {
            if(pair.second==numberOfSlots) {
                if(pair.first==7){
                    PlayerMoney.money=50000;
                }
                else {
                    PlayerMoney.money += 5000;
                }
            }
            else if(pair.second > 1){
                PlayerMoney.money += pair.second*amount;
            }
        }
        int first = numbers[0];
        for (int i = 1; i <= numberOfSlots; i++) {
            if(i%2==0 && numbers[i]!=first){
                win=false;
            }
        }
        if (win) {
            PlayerMoney.money += 500;
        }
        System.out.println("Win: " + (PlayerMoney.money - moneyBefore));
    }

    // src/game/SmallSlotMachine.java
    //    @Override
//    public void interaction(KeyEvent key) {
//
//    }
//    @Override
//    public void addSlotMachine(int x, int y){
//
//    }
}