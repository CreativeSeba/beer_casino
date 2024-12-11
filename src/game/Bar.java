package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bar extends Variables implements Money{
    private static final BufferedImage barImage;
    private static final int width = 200, height = 200;
    private static final int x = spawnX-width/2, y = spawnY-sHeight-height/2+wallThickness;

    static {
        try {
            barImage = ImageIO.read(new File("src/game/graphics/bar.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Bar() {
    }

    @Override
    public void placeBets(int amount) {

    }

    @Override
    public void interaction(Player player) {
        if (isActiveEntity) {
            GamePanel.getInstance().remove(this);
            isActiveEntity = false;
        } else {
            setBounds(new Rectangle(x, y, width, height));
            GamePanel.getInstance().add(this);
            isActiveEntity = true;
            player.setX(x);
            player.setY(y + player.getHeight() / 4);
        }
        GamePanel.getInstance().revalidate();
        GamePanel.getInstance().repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int drawX = x - camera.getX();
        int drawY = y - camera.getY();
        g.drawImage(barImage, drawX, drawY, width, height, null);
        boolean isPlayerInArea = player.getX() >= x && player.getX() <= x + width && player.getY() >= y && player.getY() <= y + height;
        if (!isActiveEntity && isPlayerInArea) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            String message = "Press E to enter";
            int textWidth = g.getFontMetrics().stringWidth(message);
            g.drawString(message, x + width / 2 - textWidth / 2 - camera.getX(), y + height - height - camera.getY());
        }
        else if(isActiveEntity && isPlayerInArea){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            String message = "Press E to leave";
            int textWidth = g.getFontMetrics().stringWidth(message);
            g.drawString(message, x + width / 2 - textWidth / 2 - camera.getX(), y + width - height - camera.getY());
        }
    }
}
