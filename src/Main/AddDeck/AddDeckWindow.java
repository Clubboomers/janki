package Main.AddDeck;

import javax.swing.*;
import javax.swing.border.Border;

public class AddDeckWindow extends JFrame {
    public AddDeckWindow() {
        super("Add deck");
        AddDeckView addDeckView = new AddDeckView();
        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        addDeckView.setBorder(padding);
        this.setContentPane(addDeckView);
        pack();

        setVisible(true);
    }
}
