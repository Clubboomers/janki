package main.mainwindow;

import javax.swing.*;
import java.awt.*;

public class MainWindowView extends JPanel {
    private BoxLayout mwLayout;
    private MainWindow mw;
    public MainWindowView(MainWindow mw) {
        super();
        this.mw = mw;
        init();
    }
    private void init() {
        this.setPreferredSize(new Dimension(400, 375));
        mwLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(mwLayout);
        Menu menu = new Menu(mw);
        menu.setMaximumSize(menu.getPreferredSize());
        // Create black border under menu
        menu.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        this.add(menu);

        this.add(Box.createRigidArea(new Dimension(0, 5)));

        MainContentView studyDecksView = new MainContentView(mw);
        // Makes studyDecksView only as wide as its preferred size
        studyDecksView.setMaximumSize(studyDecksView.getPreferredSize());
        // Float studyDecksView to the top of the screen
        studyDecksView.setAlignmentY(TOP_ALIGNMENT);
        this.add(studyDecksView);
    }
    public void update() {
        this.removeAll();
        init();
        this.revalidate();
        this.repaint();
    }
}