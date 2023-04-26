package main.addcard;

import javax.swing.*;
import javax.swing.border.Border;

import main.mainwindow.MainWindow;

public class AddCardWindow extends JDialog {
    public AddCardWindow(MainWindow mw) {
        super(mw, "Add Card", true);
        setTitle("Add Card");
        setLocationRelativeTo(mw);

        AddCardView addCardView = new AddCardView(mw, this);
        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        addCardView.setBorder(padding);
        setContentPane(addCardView);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
