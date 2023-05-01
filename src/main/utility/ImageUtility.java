package main.utility;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import static main.utility.MediaUtility.filePrefix;
import static main.utility.MediaUtility.imageExtensions;

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

    public static String saveImage(BufferedImage image, String fileName, String extension) {
        String filePath = filePrefix + fileName + "." + extension;
        File outputFile = new File(filePath);
        try {
            javax.imageio.ImageIO.write(image, extension, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "[" + fileName + "." + extension + "]";
    }

    public static boolean textIsImage(String text) {
        for (String extension : imageExtensions) {
            if (text.endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    public static BufferedImage loadImage(File f) {
        BufferedImage image = null;
        try {
            image = javax.imageio.ImageIO.read(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}
