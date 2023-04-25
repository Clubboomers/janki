package main.cardtypemanager;

import javax.swing.BorderFactory;
import javax.swing.JDialog;

import main.mainwindow.MainWindow;

public class CardTypeManagerWindow extends JDialog {
    public CardTypeManagerWindow(MainWindow mw) {
        super(mw, "Manage card types", true);
        this.setSize(300, 200);
        this.setLocationRelativeTo(mw);
        setResizable(false);

        CardTypeManagerView view = new CardTypeManagerView(mw, this);
        // Add 10 pixel inner padding to the view
        view.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setContentPane(view);
        this.pack();
        this.setVisible(true);
    }
}
