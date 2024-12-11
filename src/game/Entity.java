package game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity extends Variables implements Money{
    protected int x, y;
    protected static final int wWidth = 200, wHeight = 200;
    protected static final int width = 200, height = 200;
    protected Color color;
    protected BufferedImage image;
    private static final  Rectangle gameArea = new Rectangle(spawnX - wallRadius, spawnY-wallRadius + wHeight+player.getHeight() / 16, 2 * wallRadius, 2 * wallRadius);
    protected String labelText;

    public Entity(int x, int y, String labelText, BufferedImage image, Color color) {
        this.x = x;
        this.y = y;
        this.labelText = labelText;
        this.image = image;
        this.color = color;
        if(!gameArea.contains(x, y)){
            if(x < gameArea.x){
                this.x = gameArea.x;
            }
            else if(x > gameArea.x + gameArea.width){
                this.x = spawnX+wallRadius - width;
            }
            if(y < gameArea.y){
                this.y = gameArea.y;
            }
            else if(y > gameArea.y + gameArea.height){
                this.y = spawnY+wallRadius - height;
            }
        }
        entities.add(this);
        entityAreas.add(new Rectangle(this.x, this.y, width, height));
    }

    public abstract void interaction(Player player);

    protected void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, x - camera.getX(), y - camera.getY(), width, height, null);
        } else {
            g.setColor(color);
            g.fillRect(x - camera.getX(), y - camera.getY(), width, height);
        }
        boolean isPlayerInArea = player.getX() >= x && player.getX() <= x + width && player.getY() >= y && player.getY() <= y + height;
        if (!isActiveEntity && isPlayerInArea) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            String message = "Press E to enter";
            int textWidth = g.getFontMetrics().stringWidth(message);
            g.drawString(message, x + width / 2 - textWidth / 2 - camera.getX(), y  - camera.getY()-player.getHeight() / 16);
        }
        else if(isActiveEntity && isPlayerInArea){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            String message = "Press E to leave";
            int textWidth = g.getFontMetrics().stringWidth(message);
            g.drawString(message, x + width / 2 - textWidth / 2 - camera.getX(), y - camera.getY()-player.getHeight() / 16);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(color);
        // Draw the label text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g.getFontMetrics();
        int labelX = (wWidth - fm.stringWidth(labelText)) / 2;
        int labelY = fm.getHeight();
        g.drawString(labelText, labelX, labelY);
    }
}
