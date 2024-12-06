package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SmallSlotMachine extends SlotMachine implements Money {
    private static final int numberOfSlots = 3, loose = 100;
    private static final Color color = Color.GREEN;
    private static final Slots type = Slots.SMALL;
    private static final String labelText = "Small Slot Machine";
    private static final BufferedImage smallSlotMachineImage;
    private static final String comb = "000";

    static {
        try {
            smallSlotMachineImage = ImageIO.read(new File("src/game/graphics/small-slot-machine.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SmallSlotMachine(int x, int y) {
        super(x, y, numberOfSlots, type, loose, smallSlotMachineImage, color, labelText);
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
        combinations.add("XXX");
        combinations.add("X0X");
        combinations.add("777");
        return combinations;
    }

    @Override
    public void placeBets(int amount) {
        if (PlayerMoney.money < amount) {
            GamePanel.getInstance().setResultMessage("Not enough money", Color.RED);
            return;
        }
        Money.super.placeBets(amount);
        boolean win = true;
        final int moneyBefore = PlayerMoney.money;
        for (Pair<Integer, Integer> pair : combinations) {
            if (pair.second == numberOfSlots) {
                if (pair.first == 7) {
                    PlayerMoney.money = 50000;
                } else {
                    PlayerMoney.money += 5000;
                }
            } else if (pair.second > 1) {
                PlayerMoney.money += pair.second * amount / 2;
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
            PlayerMoney.money += 500;
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