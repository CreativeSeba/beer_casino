package game;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

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
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                pressedKeys.add(e.getKeyCode());
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    // SMALL SLOT MACHINE
                    for (int i = 0; i < smallSlotMachineAreas.size(); i++) {
                        Rectangle slotMachineArea = smallSlotMachineAreas.get(i);
                        if (slotMachineArea.contains(player.getX(), player.getY())) {
                            SmallSlotMachine slotMachine = smallSlotMachines.get(i);

                            if (slotMachine != null) {
                                if (isSlotMachineActive) {
                                    // Deactivate the slot machine
                                    remove(slotMachine);
                                    slotMachine.resetNumbers();
                                    isSlotMachineActive = false;
                                    activeSlotMachine = null;
                                } else {
                                    // Activate the slot machine
                                    add(slotMachine);
                                    isSlotMachineActive = true;
                                    activeSlotMachine = slotMachine;

                                    // Teleport the player to the center of the slot machine area
                                    player.setX(slotMachineArea.x + slotMachineArea.width / 2);
                                    player.setY(slotMachineArea.y + slotMachineArea.height / 2 + player.getHeight() / 4);
                                }
                                revalidate();
                                repaint();
                                break;
                            }
                        }
                    }
                }
            }
        });
        repaint();
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
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Rectangle area : smallSlotMachineAreas) {
            if (smallSlotMachineImage != null) {
                int imageWidth = (int) (area.width * 1.5);
                int imageHeight = (int) (area.height * 1.5);
                int centerX = area.x + area.width / 2 - camera.getX();
                int centerY = area.y + area.height / 2 - camera.getY();
                g.drawImage(smallSlotMachineImage, centerX - imageWidth / 2, centerY - imageHeight / 2, imageWidth, imageHeight, this);
            } else {
                g.setColor(Color.GREEN);
                g.fillRect(area.x - camera.getX(), area.y - camera.getY(), area.width, area.height);
            }
        }
    }
}
