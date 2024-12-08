package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SmallSlotMachine extends SlotMachine {
    private static final int numberOfSlots = 3, loose = 100;
    private static final Color color = Color.GREEN;
    private static final Slots type = Slots.SMALL;
    private static final String labelText = "Small Slot Machine";
    private static final BufferedImage smallSlotMachineImage;

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
    public ArrayList<Pair<String, Integer>> combinations() {
        ArrayList<Pair<String, Integer>>  combinations = new ArrayList<>();
        combinations.add(new Pair<>("XX0", loose/2));
        combinations.add(new Pair<>("X0X", loose*5));
        combinations.add(new Pair<>("XXX", loose*50));
        combinations.add(new Pair<>("777", loose*500));
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
                activeBet = 1;
                PlayerMoney.money += vCombs.get(activeBet).second;
            }
        }
        for (Pair<Integer, Integer> pair : cCombs) {
            if (pair.second == numberOfSlots) {
                if (pair.first == 7) {
                    activeBet=3;
                    PlayerMoney.money += vCombs.get(activeBet).second;
                } else {
                    activeBet=2;
                    PlayerMoney.money += vCombs.get(activeBet).second;
                }
            } else if (pair.second == 2) {
                activeBet=0;
                PlayerMoney.money += vCombs.get(activeBet).second;
            }
        }
        updateGamePanelMessages(moneyBefore, loose);
        System.out.println("Win: " + (PlayerMoney.money - moneyBefore));
    }
}