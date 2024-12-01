package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Floor extends Variables {
    private static final BufferedImage floorImage;
    static {
        try {
            floorImage = ImageIO.read(new File("src/game/graphics/floor.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Floor() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (floorImage != null) {
            int bgWidth = floorImage.getWidth();
            int bgHeight = floorImage.getHeight();
            int offsetX = (camera.getX() % bgWidth + bgWidth) % bgWidth;
            int offsetY = (camera.getY() % bgHeight + bgHeight) % bgHeight;

            for (int x = -offsetX; x < sWidth; x += bgWidth) {
                for (int y = -offsetY; y < sHeight; y += bgHeight) {
                    g.drawImage(floorImage, x, y, null);
                }
            }
        }
    }
}
