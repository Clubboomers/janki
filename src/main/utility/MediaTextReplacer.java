package main.utility;

import main.cardeditor.FieldTextPane;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.utility.MediaUtility.filePrefix;

/**
 * Finds tags in a String of text e.g. [abc.jpg]
 * or [abc.mp3] and replaces them with
 * <img src="abc.jpg"> or <audio src="abc.mp3">
 */
public class MediaTextReplacer {
    private Pattern pattern = Pattern.compile("\\[(.*?)\\]"); // checks for [...]
    public MediaTextReplacer() {
    }

    public String update(String text) {
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String tag = matcher.group(1);
            text = text.replace("["+tag+"]", insertMediaWithName(tag));
        }
        return text;
    }

    /**
     * Takes in a file name and returns the path to the file if it exists
     * @param fileName the name of the file, including the extension
     * @return the media html tag
     */
    private String insertMediaWithName(String fileName) {
        String path = filePrefix + fileName;
        File file = new File(path);
        URL absolutePath = null;
        try {
            absolutePath = file.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(path);
        System.out.println(file.exists());
        if (file.exists()) {
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
            System.out.println(fileExtension);
            if (Arrays.asList(MediaUtility.imageExtensions).contains(fileExtension)) { // if the file is an image
                //BufferedImage image = ImageUtility.loadImage(file);
                String imageTag = "<img src=\"" + absolutePath + "\">";
                System.out.println(imageTag);
                return imageTag;
            } else if (Arrays.asList(MediaUtility.audioExtensions).contains(fileExtension)) { // if the file is an audio file
                // TODO: implement audio
                String audioTag = "<audio controls src=\"" + absolutePath + "\" type=\"audio/" + fileExtension + "\"></audio>";
                System.out.println(audioTag);
                return audioTag;
            } else {
                JOptionPane.showMessageDialog(null, "File " + fileName + " is not an image or audio file");
                return null;
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "File " + fileName + " does not exist");
            return null;
        }
    }
}
