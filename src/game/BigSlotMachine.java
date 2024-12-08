package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BigSlotMachine extends SlotMachine {
    private static final int numberOfSlots = 5, loose = 1000;
    private static final Color color = Color.RED;
    private static final Slots type = Slots.BIG;
    private static final String labelText = "Big Slot Machine";
    private static final BufferedImage bigSlotMachineImage;

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
    public ArrayList<Pair<String, Integer>> combinations() {
        ArrayList<Pair<String, Integer>>  combinations = new ArrayList<>();
        combinations.add(new Pair<>("XX000", loose/2));
        combinations.add(new Pair<>("XXX00", loose*2));
        combinations.add(new Pair<>("XXXX0", loose*5));
        combinations.add(new Pair<>("X0X0X", loose*20));
        combinations.add(new Pair<>("XXXXX", loose*100));
        combinations.add(new Pair<>("77777", loose*1000));
        return combinations;
    }
    @Override
    public void bet() {
        super.placeBets(loose);
        activeBet = -1;
        boolean win = true;
        final int moneyBefore = PlayerMoney.money;
        int first = numbers[0];

        if(vCombs.get(0).second != numberOfSlots) {
            for (int i = 1; i <= numberOfSlots; i++) {
                if (i % 2 == 0 && numbers[i] != first) {
                    win = false;
                    break;
                }
            }
            if (win) {
                activeBet = 3;
                PlayerMoney.money += vCombs.get(activeBet).second;
            }
        }
        for (Pair<Integer, Integer> pair : cCombs) {
            if (pair.second == numberOfSlots) {
                if (pair.first == 7) {
                    activeBet = 5;
                    PlayerMoney.money += vCombs.get(activeBet).second;
                } else {
                    activeBet = 4;
                    PlayerMoney.money += vCombs.get(activeBet).second;
                }
            } else if (pair.second == 2) {
                activeBet = 0;
                PlayerMoney.money += vCombs.get(activeBet).second;
            } else if (pair.second == 3) {
                activeBet = 1;
                PlayerMoney.money += vCombs.get(activeBet).second;
            } else if (pair.second == 4) {
                activeBet = 2;
                PlayerMoney.money += vCombs.get(activeBet).second;
            }
        }

        updateGamePanelMessages(moneyBefore, loose);
        System.out.println("Win: " + (PlayerMoney.money - moneyBefore));
    }
}
