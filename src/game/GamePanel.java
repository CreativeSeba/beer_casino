package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;

public class GamePanel extends Variables implements ActionListener {
    private static GamePanel instance;
    public GamePanel(int sWidth, int sHeight, int wallRadius, int wallThickness, int money, int playerSize, int playerSpeed) {
        instance = this;
        Variables.sWidth = sWidth;
        Variables.sHeight = sHeight;
        Variables.wallRadius = wallRadius;
        Variables.wallThickness = wallThickness;
        Variables.playerSize = playerSize;
        Variables.playerSpeed = playerSpeed;
        spawnX = sWidth / 2;
        spawnY = sHeight / 2;
        camera = new Camera();
        player = new Player(spawnX, spawnY, playerSize, playerSpeed);
        floor = new Floor();
        wall = new Wall();
        bar = new Bar();
        slotMachines = new ArrayList<>();
        slotMachineAreas = new ArrayList<>();
        pressedKeys = new HashSet<>();
        PlayerMoney.money = money;
        //SMALL
        new SmallSlotMachine(-200, 0);
        //BIG
        new BigSlotMachine(200, 0);

        setPreferredSize(new Dimension(sWidth, sHeight));
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent key) {
                pressedKeys.add(key.getKeyCode());
                if (key.getKeyCode() == KeyEvent.VK_E) {
                    for (int i = 0; i < slotMachineAreas.size(); i++) {
                        Rectangle slotMachineArea = slotMachineAreas.get(i);
                        if (slotMachineArea.contains(player.getX(), player.getY())) {
                            SlotMachine slotMachine = slotMachines.get(i);
                            if (slotMachine != null) {
                                slotMachine.interact(player);
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent key) {
                pressedKeys.remove(key.getKeyCode());
            }
        });
        timer = new Timer(16, this);
        timer.start();
    }
    public static GamePanel getInstance() {
        return instance;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        floor.paintComponent(g);
        wall.paintComponent(g);
        bar.paintComponent(g);
        for (SlotMachine slotMachine : slotMachines) {
            slotMachine.draw(g);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        String moneyText = "Chips: " + PlayerMoney.money;
        int textWidth = g.getFontMetrics().stringWidth(moneyText);
        g.drawString(moneyText, getWidth() - textWidth - 10, 30);

        camera.update();
        player.paintComponent(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isSlotMachineActive) {
            player.updatePlayerPosition();
        }
        repaint();
    }
}