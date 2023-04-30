package main.utility;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Finds tags in a String of text e.g. [abc.jpg]
 * or [abc.mp3] and replaces them with
 * <img src="abc.jpg"> or <audio src="abc.mp3">
 */
public class MediaTextReplacer {
    private Pattern pattern = Pattern.compile("\\[([^}]*)\\]"); // checks for [...]
    public MediaTextReplacer() {

    }

    public String imageTagReplacer(String text) {
        String finalText = text;
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String tag = matcher.group(1);
            String media = getMediaByName(tag);
            if (media != null)
                finalText = finalText.replace("[" + tag + "]", media);
        }
        return finalText;
    }

    /**
     * Takes in a file name and returns the path to the file if it exists
     * @param fileName the name of the file, including the extension
     * @return
     */
    private String getMediaByName(String fileName) {
        String path = ImageUtility.filePrefix + fileName;
        String extension = fileName.substring(fileName.lastIndexOf(".") + fileName.length() - 1);

        for (String imageExtension : MediaUtility.imageExtensions) {
            if (extension.equals(imageExtension)) {
                File file = new File(path);
                if (file.exists()) {
                    String imgSrc = "<img src=\"" + path + "\">";
                    return imgSrc;
                }
            }
        }
        for (String audioExtension : MediaUtility.audioExtensions) {
            if (extension.equals(audioExtension)) {
                File file = new File(path);
                if (file.exists()) {
                    String audioSrc = "<audio src=\"" + path + "\">";
                    return audioSrc;
                }
            }
        }
        return null;
    }
}
