package main.utility;

/*import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;*/

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class AudioPlayer extends JPanel {
    /*private Icon icon;
    private MediaPlayer mediaPlayer;

    public AudioPlayer() {
        ImageIcon imageIcon = new ImageIcon("src/main/resources/play_button.png");
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        JLabel playButton = new JLabel(icon);
        // make cursor change to hand when hovering over play button
        playButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(playButton);
        playButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (isPlaying()) {
                    stop();
                    play("src/main/resources/userfiles/Heart_Warm.mp3");
                } else {
                    play("src/main/resources/userfiles/Heart_Warm.mp3");
                }
            }
        });
    }

    public void play(String filePath) {
        try {
            File musicPath = new File(filePath);
            if (musicPath.exists()) {
                Media hit = new Media(musicPath.toURI().toString());
                mediaPlayer = new MediaPlayer(hit);
                mediaPlayer.play();
            } else {
                System.out.println("Can't find file");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    private boolean isPlaying() {
        if (mediaPlayer != null) {
            return mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
        }
        return false;
    }*/
}
