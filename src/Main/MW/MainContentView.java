package Main.MW;

import javax.swing.*;

import Main.GridbagWizard;
import Main.AddCard.AddCardWindow;
import Main.Data.Deck;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainContentView extends JPanel {
    private GridBagLayout decksViewLayout;
    static private DeckList deckList;
    private JButton btnStudy;
    MainWindow mw;
    public MainContentView(MainWindow mw) {
        super();
        this.mw = mw;
        GridbagWizard wizard = new GridbagWizard();
        decksViewLayout = new GridBagLayout();
        this.setLayout(decksViewLayout);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc = new GridBagConstraints();
        JButton btnAddDeck = new JButton("Add deck");
        // open new dialogue to create deck when button is clicked
        btnAddDeck.addActionListener(e -> {
            String deckName = null;
            try {
                deckName = JOptionPane.showInputDialog(null, "Deck name:", "Add deck", JOptionPane.PLAIN_MESSAGE).trim();
                mw.addDeck(new Deck(deckName));
                mw.updateView();
            } catch (Exception g) {
                // TODO: handle exception
                return;
            }
        });
        gbc.insets = new Insets(0, 0, 5, 5);
        // make button float to the right side of the cell
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.weightx = 1;
        wizard.addComponent(btnAddDeck, this, decksViewLayout, gbc, 0, 0, 1, 1);

        gbc =  new GridBagConstraints();
        JButton btnAddCard = new JButton("Add card");
        btnAddCard.addActionListener(e -> {
            AddCardWindow addCardWindow = new AddCardWindow(mw);
        });
        gbc.insets = new Insets(0, 5, 5, 0);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 1;
        wizard.addComponent(btnAddCard, this, decksViewLayout, gbc, 1, 0, 1, 1);

        gbc = new GridBagConstraints();
        deckList = new DeckList(mw);
        deckList.setCellRenderer(new MyListCellRenderer(mw));
        // If item in deckList is selected, enable study button
        deckList.addListSelectionListener(e -> {
            if (deckList.getSelectedIndex() != -1)
                btnStudy.setEnabled(true);
            else
                btnStudy.setEnabled(false);
        });
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.insets = new Insets(0, 0, 5, 0);
        wizard.addComponent(deckList, this, decksViewLayout, gbc, 0, 1, 2, 1);

        gbc =  new GridBagConstraints();
        btnStudy = new JButton("Study");
        btnStudy.setEnabled(false);
        btnStudy.addActionListener(e -> {
            mw.study();
        });
        wizard.addComponent(btnStudy, this, decksViewLayout, gbc, 0, 2, 2, 1);
    }

    static public String getSelectedDeck() {
        return (String) deckList.getSelectedValue();
    }

    static public void updateDeckList(MainWindow mw) {
        deckList.setListData(mw.getDeckNames());
    }
}
