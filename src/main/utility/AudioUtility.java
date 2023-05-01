package main.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static main.utility.MediaUtility.filePrefix;
import static main.utility.MediaUtility.audioExtensions;
public class AudioUtility {
    public AudioUtility() {
    }

    public static String saveAudio(File f, String extension) {
        String audioName = MediaUtility.getUniqueAudioName();
        String targetPath = filePrefix + audioName + "." + extension;
        try {
            Files.copy(f.toPath(), new File(targetPath).toPath(), StandardCopyOption.REPLACE_EXISTING); // copy the file to the target path
            return "[" + audioName + "." + extension + "]";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*private static void convertFileToAudioStream(File f){

    }*/

    public static boolean textIsAudio(String text) {
        for (String extension : audioExtensions) {
            if (text.endsWith(extension)) {
                return true;
            }
        }
        return false;
    }
}
