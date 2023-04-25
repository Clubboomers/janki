package main.utility;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageUtility {
    public ImageUtility() {
    }

    public static BufferedImage resizeImage(BufferedImage image, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    public static BufferedImage resizeImage(BufferedImage image, int width, int height, int type) {
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    public static void saveImage(BufferedImage image, String path) {
        File outputFile = new File(path);
        try {
            javax.imageio.ImageIO.write(image, "jpg", outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
