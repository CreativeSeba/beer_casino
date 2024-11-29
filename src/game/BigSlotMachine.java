package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BigSlotMachine extends SlotMachine implements Money {
    private static final int numberOfSlots = 5, loose = 1000;
    private static Color color = Color.RED;
    private static BufferedImage bigSlotMachineImage;

    static {
        try {
            bigSlotMachineImage = ImageIO.read(new File("src/game/graphics/big-slot-machine.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BigSlotMachine(int x, int y) {
        super(x, y, numberOfSlots, Slots.BIG, loose, bigSlotMachineImage, color);
        setBackground(Color.RED);
    }

    @Override
    public void placeBets(int amount) {
        Money.super.placeBets(amount);
        boolean win = true;
        final int moneyBefore = PlayerMoney.money;
        for (Pair<Integer, Integer> pair : combinations) {
            if(pair.second==numberOfSlots) {
                if(pair.first==7){
                    PlayerMoney.money=1000000;
                }
                else {
                    PlayerMoney.money += 100000;
                }
            }
            else if(pair.second==2){
                PlayerMoney.money += amount;
            }
            else if(pair.second > 2){
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
            PlayerMoney.money += 50000;
        }
        System.out.println("Win: " + (PlayerMoney.money - moneyBefore));
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
