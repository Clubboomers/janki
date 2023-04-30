package main.mainwindow;

import main.utility.AudioPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainWindowView extends JPanel {
    private BoxLayout mwLayout;
    private MainWindow mw;
    public MainWindowView(MainWindow mw) {
        super();
        this.mw = mw;
        init();
    }
    private void init() {
        this.setPreferredSize(new Dimension(700, 425));
        mwLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(mwLayout);
        Menu menu = new Menu(mw);
        menu.setMaximumSize(menu.getPreferredSize());
        // Create black border under menu
        menu.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 0, 0, 100)));
        this.add(menu);

        this.add(Box.createRigidArea(new Dimension(0, 5)));

        MainContentView studyDecksView = new MainContentView(mw);
        // Makes studyDecksView only as wide as its preferred size
        studyDecksView.setMaximumSize(studyDecksView.getPreferredSize());
        // Float studyDecksView to the top of the screen
        studyDecksView.setAlignmentY(TOP_ALIGNMENT);
        this.add(studyDecksView);
        /*AudioPlayer audioPlayer = new AudioPlayer();
        add(audioPlayer);*/
    }
    public void update() {
        this.removeAll();
        init();
        this.revalidate();
        this.repaint();
    }
}
