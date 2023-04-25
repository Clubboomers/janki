package main.utility;

import java.awt.image.BufferedImage;

public class MediaUtility {
    private static int imageCounter = 0;
    private static int audioCounter = 0;
    public static final int IMAGE = 0;
    public static final int AUDIO = 1;
    public static final int TEXT = 2;
    private int preferredImageWidth = 400;
    private int preferredImageHeight = 400;
    private boolean preserveAspectRatio = true;
    private String preferredImageFormat = "png";

    public MediaUtility() {
    }

    private BufferedImage resizeImage(BufferedImage image) {
        if (preserveAspectRatio) {
            double aspectRatio = (double) image.getWidth() / (double) image.getHeight();
            if (image.getWidth() > image.getHeight()) {
                return ImageUtility.resizeImage(image, preferredImageWidth, (int) (preferredImageWidth / aspectRatio));
            } else {
                return ImageUtility.resizeImage(image, (int) (preferredImageHeight * aspectRatio), preferredImageHeight);
            }
        } else {
            return ImageUtility.resizeImage(image, preferredImageWidth, preferredImageHeight);
        }
    }

    public String saveImageFromClipboard() {
        String path;
        path = "src\\main\\resources" + imageCounter + "." + preferredImageFormat;
        if (ClipboardUtility.isImageInClipboard()) {
            BufferedImage image = ClipboardUtility.getImageFromClipboard();
            image = resizeImage(image);
            ImageUtility.saveImage(image, path);
            imageCounter++;
            return path;
        }
        throw new RuntimeException("No image in clipboard");
    }

    // detect what type of media is in an object
    public static int getMediaType(Object object) {
        if (object instanceof BufferedImage) {
            return IMAGE;
        } else if (object instanceof String) {
            return AUDIO;
        } else {
            throw new RuntimeException("Unknown media type");
        }
    }
}
