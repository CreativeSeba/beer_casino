package game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bar extends Entity{
    private static final BufferedImage barImage;
    private static final Color color = Color.YELLOW;
    private static final String labelText = "Bar";

    static {
        try {
            barImage = ImageIO.read(new File("src/game/graphics/bar.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Bar() {
        super(spawnX - width /2, spawnY - height /2, labelText, barImage, color);

    }

    @Override
    public void placeBets(int amount) {

    }

    @Override
    public void interaction(Player player) {
        if (isActiveEntity) {
            GamePanel.getInstance().remove(this);
            isActiveEntity = false;
            pressedKeys.clear();
        } else {
            setBounds(new Rectangle(x, y, width, height));
            GamePanel.getInstance().add(this);
            isActiveEntity = true;
            player.setX(x + width / 2);
            player.setY(y + player.getHeight() / 4 + height / 2);
        }
        GamePanel.getInstance().revalidate();
        GamePanel.getInstance().repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setSize(wWidth, wHeight);
        setLocation( spawnX - wWidth / 2, 0);
    }
}
