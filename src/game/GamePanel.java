package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements ActionListener {
    protected Timer timer;
    protected Player player;
    protected Set<Integer> pressedKeys;
    protected Camera camera;
    protected BufferedImage backgroundImage, smallSlotMachineImage, bigSlotMachineImage;
    protected List<BigSlotMachine> bigSlotMachines;
    protected List<SmallSlotMachine> smallSlotMachines;
    protected List<Rectangle>  smallSlotMachineAreas, bigSlotMachineAreas;
    protected boolean isSlotMachineActive = false;
    protected SlotMachine activeSlotMachine;



    protected static int WALL_THICKNESS;
    protected static int WALL_RADIUS;
    protected static int SPAWN_X;
    protected static int SPAWN_Y;
    public static PlayerMoney playerMoney;
    private static Wall wall;
    private static Floor floor;

    public GamePanel() {

    }

    public GamePanel(int SPAWN_X, int SPAWN_Y, int WALL_RADIUS, int WALL_THICKNESS, int money) {
        this.SPAWN_X = SPAWN_X / 2;
        this.SPAWN_Y = SPAWN_Y / 2;
        this.WALL_RADIUS = WALL_RADIUS;
        this.WALL_THICKNESS = WALL_THICKNESS;
        this.playerMoney = new PlayerMoney(money);
        setPreferredSize(new Dimension(SPAWN_X, SPAWN_Y));
        setFocusable(true);

        player = new Player(0, 0, 0, 300, WALL_THICKNESS, WALL_RADIUS, SPAWN_X, SPAWN_Y); // Increase speed to 300
        player = new Player(this.SPAWN_X - player.getWidth() / 2, this.SPAWN_Y - player.getHeight() / 2, 100, 300, WALL_THICKNESS, WALL_RADIUS, this.SPAWN_X, this.SPAWN_Y); // Set correct position with increased speed
        wall = new Wall();
        floor = new Floor();

        pressedKeys = new HashSet<>();
        camera = new Camera(SPAWN_X, SPAWN_Y, this.SPAWN_X, this.SPAWN_Y, WALL_THICKNESS, WALL_RADIUS);

        try {
            backgroundImage = ImageIO.read(new File("src/game/graphics/floor.jpg"));
            smallSlotMachineImage = ImageIO.read(new File("src/game/graphics/small-slot-machine.png"));
            bigSlotMachineImage = ImageIO.read(new File("src/game/graphics/big-slot-machine.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }



        bigSlotMachines = new ArrayList<>();
        bigSlotMachineAreas = new ArrayList<>();
        smallSlotMachines = new ArrayList<>(); // Initialize the smallSlotMachines list
        smallSlotMachineAreas = new ArrayList<>();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                pressedKeys.add(e.getKeyCode());
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    // BIG SLOT MACHINE
                    for (int i = 0; i < bigSlotMachineAreas.size(); i++) {
                        Rectangle slotMachineArea = bigSlotMachineAreas.get(i);
                        if (slotMachineArea.contains(player.getX(), player.getY())) {
                            BigSlotMachine slotMachine = bigSlotMachines.get(i);

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

            @Override
            public void keyReleased(KeyEvent e) {
                pressedKeys.remove(e.getKeyCode());
            }
        });

        timer = new Timer(16, this); // roughly 60 FPS
        timer.start();
    }

    public void addSlotMachine(int x, int y, Slots type) {
        switch (type) {
            case SMALL:
                int smallSlotMachineWidth = player.getWidth() + player.getWidth() / 4;
                int smallSlotMachineHeight = player.getHeight() + player.getHeight() / 4;
                int stx = SPAWN_X + x - smallSlotMachineWidth / 2;
                int sty = SPAWN_Y - y - smallSlotMachineHeight / 2;
                SmallSlotMachine smallSlotMachine = new SmallSlotMachine(3, stx, sty);
                smallSlotMachines.add(smallSlotMachine);
                smallSlotMachineAreas.add(new Rectangle(stx, sty, smallSlotMachineWidth, smallSlotMachineHeight));

                break;
            case BIG:
                int bigSlotMachineWidth = player.getWidth() + player.getWidth() / 4;
                int bigSlotMachineHeight = player.getHeight() + player.getHeight() / 4;
                int btx = SPAWN_X + x - bigSlotMachineWidth / 2;
                int bty = SPAWN_Y - y - bigSlotMachineHeight / 2;
                BigSlotMachine bigSlotMachine = new BigSlotMachine(5, btx, bty);
                bigSlotMachines.add(bigSlotMachine);
                bigSlotMachineAreas.add(new Rectangle(btx, bty, bigSlotMachineWidth, bigSlotMachineHeight));
                break;
            default:
                break;
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the large slot machine areas
        for (Rectangle area : bigSlotMachineAreas) {
            if (bigSlotMachineImage != null) {
                int imageWidth = (int) (area.width * 1.5);
                int imageHeight = (int) (area.height * 1.5);
                int centerX = area.x + area.width / 2 - camera.getX();
                int centerY = area.y + area.height / 2 - camera.getY();
                g.drawImage(bigSlotMachineImage, centerX - imageWidth / 2, centerY - imageHeight / 2, imageWidth, imageHeight, this);
            } else {
                g.setColor(Color.RED);
                g.fillRect(area.x - camera.getX(), area.y - camera.getY(), area.width, area.height);
            }
        }

        // Draw the player's money in the top right corner
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        String moneyText = "Chips: " + playerMoney.money;
        int textWidth = g.getFontMetrics().stringWidth(moneyText);
        g.drawString(moneyText, getWidth() - textWidth - 10, 30);

        camera.update(player);
        player.draw(g, camera.getX(), camera.getY()); // Draw the player entity relative to the camera
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isSlotMachineActive) {
            updatePlayerPosition();
        }
        repaint();
    }

    private void updatePlayerPosition() {
        double deltaTime = 0.016; // Assuming 60 FPS, so each frame is roughly 0.016 seconds
        if (pressedKeys.contains(KeyEvent.VK_W)) {
            player.moveUp(deltaTime);
        }
        if (pressedKeys.contains(KeyEvent.VK_S)) {
            player.moveDown(deltaTime);
        }
        if (pressedKeys.contains(KeyEvent.VK_A)) {
            player.moveLeft(deltaTime);
        }
        if (pressedKeys.contains(KeyEvent.VK_D)) {
            player.moveRight(deltaTime);
        }
    }
}