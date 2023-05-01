package main.utility;

/*import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;*/

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class AudioPlayer extends JLabel {
    private Icon icon;
    private MediaPlayer mediaPlayer;

    public AudioPlayer(String filePath) {
        ImageIcon imageIcon = new ImageIcon("src/main/resources/play_button.png");
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        this.setIcon(icon);
        // make cursor change to hand when hovering over play button
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (isPlaying()) {
                    stop();
                    play(filePath);
                } else {
                    play(filePath);
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
    }
}
