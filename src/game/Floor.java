package game;

import java.awt.*;

public class Floor extends GamePanel{
    public Floor() {
        super();
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            int bgWidth = backgroundImage.getWidth();
            int bgHeight = backgroundImage.getHeight();
            int offsetX = (camera.getX() % bgWidth + bgWidth) % bgWidth;
            int offsetY = (camera.getY() % bgHeight + bgHeight) % bgHeight;

            for (int x = -offsetX; x < getWidth(); x += bgWidth) {
                for (int y = -offsetY; y < getHeight(); y += bgHeight) {
                    g.drawImage(backgroundImage, x, y, this);
                }
            }
        }
    }
}
