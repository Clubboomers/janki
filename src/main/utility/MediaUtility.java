package main.utility;

import java.awt.image.BufferedImage;
import java.io.File;

public class MediaUtility {
    public static final String[] imageExtensions = {"jpg", "jpeg", "png", "gif"};
    public static final String[] audioExtensions = {"mp3", "wav", "ogg"};
    public static final String IMAGE_FILE_DESCRIPTION = ".jpg, .jpeg, .png, .gif";
    public static final String AUDIO_FILE_DESCRIPTION = ".mp3, .wav, .ogg";
    private static int imageCounter = 0;
    private static int audioCounter = 0;
    public static final int IMAGE = 0;
    public static final int AUDIO = 1;
    public static final int TEXT = 2;
    private int preferredImageWidth = 400;
    private int preferredImageHeight = 400;
    private boolean preserveAspectRatio = true;
    private String preferredImageFormat = "jpg";


    public MediaUtility() {
    }

    public static boolean isImage(File f) {
        String fileName = f.getName();
        for (String extension : imageExtensions) {
            if (fileName.endsWith(extension)) {
                return true;
            }
        }
        return false;
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

    /**
     * Saves an image from the clipboard to a file
     * @return the path to the saved image
     */
    public String saveImageFromClipboard() {
        String fileName;
        fileName = imageCounter + "";
        if (ClipboardUtility.isImageInClipboard()) {
            BufferedImage image = ClipboardUtility.getImageFromClipboard();
            image = resizeImage(image);
            String filePath = ImageUtility.saveImage(image, fileName, preferredImageFormat);
            imageCounter++;
            return filePath;
        }
        throw new RuntimeException("No image in clipboard");
    }

    public String saveImageFromFile(File f) {
        String fileName;
        fileName = imageCounter + "";
        if (isImage(f)) {
            BufferedImage image = ImageUtility.loadImage(f);
            image = resizeImage(image);
            String filePath = ImageUtility.saveImage(image, fileName, preferredImageFormat);
            imageCounter++;
            return filePath;
        }
        throw new RuntimeException("File is not an image");
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
