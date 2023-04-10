package Main.CardTypeManager.CardTypeStyle;

import javax.swing.BorderFactory;
import javax.swing.JDialog;

import Main.CardTypeManager.CardTypeManagerWindow;
import Main.Data.CardType;
import Main.MW.MainWindow;

public class CardTypeStyleWindow extends JDialog {
    MainWindow mw;
    CardTypeManagerWindow parent;
    CardType cardType;
    public CardTypeStyleWindow(MainWindow mw, CardTypeManagerWindow parent, CardType cardType) {
        super(parent, "Card type style", true);
        this.mw = mw;
        this.parent = parent;
        this.cardType = cardType;
        this.setTitle("Card type style");
        this.setSize(700, 500);
        this.setLocationRelativeTo(null);
        setResizable(true);
        CardTypeStyleView view = new CardTypeStyleView(mw, this, cardType);
        // Add 10 pixels of padding around the view
        view.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setContentPane(view);
        this.pack();
        this.setVisible(true);
    }
}
