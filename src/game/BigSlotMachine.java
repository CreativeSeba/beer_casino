package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BigSlotMachine extends SlotMachine implements Money {
    private static final int numberOfSlots = 5, loose = 1000;
    private static final Color color = Color.RED;
    private static final Slots type = Slots.BIG;
    private static final String labelText = "Big Slot Machine";
    private static final BufferedImage bigSlotMachineImage;
    private static final String comb = "00000";

    static {
        try {
            bigSlotMachineImage = ImageIO.read(new File("src/game/graphics/big-slot-machine.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BigSlotMachine(int x, int y) {
        super(x, y, numberOfSlots, type, loose, bigSlotMachineImage, color, labelText);
        setBackground(color);
    }

    @Override
    public ArrayList<String> combinations(){
        StringBuilder def = new StringBuilder(comb);
        ArrayList<String> combinations = new ArrayList<>();
        for(int i = 0; i < numberOfSlots-1; i++){
            def.replace(i, i+2, "XX");
            combinations.add(def.toString());
            def.setLength(0);
            def.append(comb);
        }
        for(int i = 0; i < numberOfSlots-2; i++){
            def.replace(i, i+3, "XXX");
            combinations.add(def.toString());
            def.setLength(0);
            def.append(comb);
        };
        combinations.add("XXXXX");
        combinations.add("X0X0X");
        combinations.add("77777");
        return combinations;
    }

    @Override
    public void placeBets(int amount) {
        if(PlayerMoney.money < amount) {
            GamePanel.getInstance().setResultMessage("Not enough money", Color.RED);
            return;
        }
        Money.super.placeBets(amount);
        boolean win = true;
        final int moneyBefore = PlayerMoney.money;
        for (Pair<Integer, Integer> pair : combinations) {
            if(pair.second == numberOfSlots) {
                if(pair.first == 7) {
                    PlayerMoney.money = 1000000;
                } else {
                    PlayerMoney.money += 100000;
                }
            } else if(pair.second == 2) {
                PlayerMoney.money += amount/2;
            } else if(pair.second > 2) {
                PlayerMoney.money += pair.second * amount;
            }
        }
        int first = numbers[0];
        for (int i = 1; i <= numberOfSlots; i++) {
            if (i % 2 == 0 && numbers[i] != first) {
                win = false;
                break;
            }
        }
        if (win) {
            PlayerMoney.money += 50000;
        }
        int result = PlayerMoney.money - moneyBefore;
        if (result > 0) {
            GamePanel.getInstance().setResultMessage("+" + result, Color.GREEN);
        } else if (result < 0) {
            GamePanel.getInstance().setResultMessage(String.valueOf(result), Color.RED);
        }
        GamePanel.getInstance().setPaidMessage("-" + amount); // Set the paid message
        System.out.println("Win: " + result);
    }
}
