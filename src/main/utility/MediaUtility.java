package main.utility;

import main.data.Card;
import main.data.Deck;
import main.data.Field;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class MediaUtility {
    public static final String filePrefix = "src\\main\\resources\\userfiles\\";
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
    private static String preferredImageFormat = "jpg";
    private static String preferredAudioFormat = "mp3";


    public MediaUtility() {
    }

    /**
     * Gets image counter until a unique image name is found
     * @return unique image name
     */
    public static String getUniqueImageName() {
        String imageName = getImageCounter() + "";
        File[] files = new File(filePrefix).listFiles();
        Boolean nameExists = false;
        for (File f : files) {
            String fileName = f.getName();
            if (fileName.equals(imageName + "." + preferredImageFormat)) {
                nameExists = true;
                break;
            }
        }
        if (nameExists) {
            return getUniqueImageName();
        } else {
            return imageName;
        }
    }

    /**
     * Gets audio counter until a unique audio name is found
     * @return unique audio name
     */
    public static String getUniqueAudioName() {
        String audioName = getAudioCounter() + "";
        File[] files = new File(filePrefix).listFiles();
        Boolean nameExists = false;
        for (File f : files) {
            String fileName = f.getName();
            if (fileName.equals(audioName + "." + preferredAudioFormat)) {
                nameExists = true;
                break;
            }
        }
        if (nameExists) {
            return getUniqueAudioName();
        } else {
            return audioName;
        }
    }

    private static int getImageCounter() {
        imageCounter++;
        return imageCounter;
    }

    private static int getAudioCounter() {
        audioCounter++;
        return audioCounter;
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

    public static boolean isAudio(File f) {
        String fileName = f.getName();
        if (AudioUtility.textIsAudio(fileName)) {
            return true;
        }
        return false;
    }

    public static ArrayList<File> getUnusedMedia(ArrayList<Deck> decks) {
        ArrayList<String> allFieldContent = new ArrayList<>();
        for (Deck d : decks) {
            for (Card c : d.getCards()) {
                for (Field f : c.getFields()) {
                    allFieldContent.add(f.getContent());
                }
            }
        }
        ArrayList<File> unusedMedia = new ArrayList<>();
        String[] fileNames = new File(filePrefix).list();
        for (String fileName : fileNames) {
            String tag = "[" + fileName + "]";
            if (!allFieldContent.contains(tag)) {
                unusedMedia.add(new File(filePrefix + fileName));
            }
        }
        return unusedMedia;
    }

    public static void deleteFiles(ArrayList<File> unusedMedia) {
        for (File f : unusedMedia) {
            if(!f.delete())
                System.out.println("Failed to delete file: " + f.getName());
        }
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

    public static void setImageCounter(int imageCounter) {
        MediaUtility.imageCounter = imageCounter;
    }

    public static void setAudioCounter(int audioCounter) {
        MediaUtility.audioCounter = audioCounter;
    }

    public String saveImageFromFile(File f) {
        String fileName;
        fileName = getUniqueImageName() + "";
        if (isImage(f)) {
            BufferedImage image = ImageUtility.loadImage(f);
            image = resizeImage(image);
            return ImageUtility.saveImage(image, fileName, preferredImageFormat); // returns tag
        }
        throw new RuntimeException("File is not an image");
    }

    /**
     * Saves an image from a BufferedImage to a file
     * @param image the BufferedImage to save
     * @return returns a tag with the image name e.g. [image.jpg]
     */
    public String saveImageFromBufferedImage(BufferedImage image) {
        String fileName;
        fileName = getUniqueImageName() + "";
        image = resizeImage(image);
        return ImageUtility.saveImage(image, fileName, preferredImageFormat); // returns tag
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

    public String saveAudioFromFile(File selectedFile) {
        if (isAudio(selectedFile)) {
            return AudioUtility.saveAudio(selectedFile, preferredAudioFormat);
        }
        throw new RuntimeException("File is not an audio file");
    }
}
