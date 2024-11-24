package game;

import java.awt.*;

public class Floor extends Variables {
    public Floor() {}
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            int bgWidth = backgroundImage.getWidth();
            int bgHeight = backgroundImage.getHeight();
            int offsetX = (camera.getX() % bgWidth + bgWidth) % bgWidth;
            int offsetY = (camera.getY() % bgHeight + bgHeight) % bgHeight;

            for (int x = -offsetX; x < width; x += bgWidth) {
                for (int y = -offsetY; y < height; y += bgHeight) {
                    g.drawImage(backgroundImage, x, y, this);
                }
            }
        }
    }
}
