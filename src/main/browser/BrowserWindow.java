package main.browser;

import javax.swing.JFrame;

import main.mainwindow.MainWindow;

public class BrowserWindow extends JFrame {
    
    /**
    * Card browser window
    */
    public BrowserWindow(MainWindow mw) {
        super("Browser");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        BrowserView browserView = new BrowserView(mw, this);
        this.setContentPane(browserView);
        setVisible(true);
    }
}