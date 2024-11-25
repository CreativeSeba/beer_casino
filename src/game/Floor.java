package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Floor extends Variables {
    private static BufferedImage floorImage;
    public Floor() {
        try {
            floorImage = ImageIO.read(new File("src/game/graphics/floor.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (floorImage != null) {
            int bgWidth = floorImage.getWidth();
            int bgHeight = floorImage.getHeight();
            int offsetX = (camera.getX() % bgWidth + bgWidth) % bgWidth;
            int offsetY = (camera.getY() % bgHeight + bgHeight) % bgHeight;

            for (int x = -offsetX; x < width; x += bgWidth) {
                for (int y = -offsetY; y < height; y += bgHeight) {
                    g.drawImage(floorImage, x, y, null);
                }
            }
        }
    }
}
