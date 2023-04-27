package main.utility;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class AudioPlayer extends JPanel {
    private Clip clip;
    private Icon icon;

    public AudioPlayer() {
        icon = new ImageIcon("src/main/resources/play_button.png");
        JLabel playButton = new JLabel("play");
        // make cursor change to hand when hovering over play button
        playButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(playButton);
        playButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (isPlaying()) {
                    stop();
                    play("src/main/resources/userfiles/Heart Warm2.wav");
                } else {
                    play("src/main/resources/userfiles/Heart Warm2.wav");
                }
            }
        });
    }

    public void play(String filePath) {
        try {
            File musicPath = new File(filePath);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            } else {
                System.out.println("Can't find file");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    private boolean isPlaying() {
        return clip != null && clip.isRunning();
    }

    public void close() {
        stop();
        clip.close();
    }
}
