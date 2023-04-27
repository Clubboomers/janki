package main.cardtypemanager;

import main.GridbagWizard;
import main.cardtypemanager.cardtypefields.CardTypeFieldsWindow;
import main.cardtypemanager.cardtypestyle.CardTypeStyleWindow;
import main.data.CardType;
import main.mainwindow.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CardTypeManagerView extends JPanel {
    private MainWindow mw;
    private JList<String> jlCardTypes;
    private ArrayList<JButton> buttons;
    private JLabel lblCardTypes;
    CardTypeManagerWindow parent;
    Dimension btnMaxSize;
    public CardTypeManagerView(MainWindow mw, CardTypeManagerWindow parent) {
        super();
        this.mw = mw;
        this.parent = parent;
        setPreferredSize(new Dimension(300, 200));
        btnMaxSize = new Dimension(100, 30);
        init();
    }

    private void init() {
        JPanel view = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        view.setLayout(gridBagLayout);
        GridbagWizard wizard = new GridbagWizard();
        GridBagConstraints gbc = new GridBagConstraints();


        JPanel pnlJList = new JPanel();
        pnlJList.setLayout(new BoxLayout(pnlJList, BoxLayout.Y_AXIS));

        jlCardTypes = new JList<>(mw.getCardTypeNames());
        JScrollPane jsp = new JScrollPane(jlCardTypes);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pnlJList.add(jsp);

        gbc = new GridBagConstraints();
        lblCardTypes = new JLabel("Card Types: ");
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        wizard.addComponent(lblCardTypes, view, gridBagLayout, gbc, 0, 0, 1, 1);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        wizard.addComponent(pnlJList, view, gridBagLayout, gbc, 0, 1, 1, 1);

        buttons = new ArrayList<>();
        buttons.add(new JButton("Add"));
        buttons.add(new JButton("Rename"));
        buttons.add(new JButton("Delete"));
        buttons.add(new JButton("Fields"));
        buttons.add(new JButton("Style"));
        buttons.add(new JButton("Cancel"));
        setButtonAction("Add", e -> {
            try {
                String name = JOptionPane.showInputDialog(parent, "Name:", "Add Card Type", JOptionPane.PLAIN_MESSAGE).trim();
                mw.addCardType(name);
                update();
            } catch (Exception g) {
                // TODO: handle exception
            }
        });
        setButtonAction("Rename", e -> {
            if (jlCardTypes.getSelectedValue() == null) {
                return;
            }
            try {
                String newName = JOptionPane.showInputDialog(null, "New name:", "Rename", JOptionPane.PLAIN_MESSAGE).trim();
                mw.renameCardType(jlCardTypes.getSelectedValue(), newName);
                update();
            } catch (Exception g) {
                // TODO: handle exception
                return;
            }
        });
        setButtonAction("Delete", e -> {
            if (jlCardTypes.getSelectedValue() == null) {
                return;
            }
            // Ask user if they are sure they want to delete the card type
            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this card type?", "Delete Card Type", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.NO_OPTION) {
                return;
            }
            if (mw.cardWithCardTypeExists(mw.getCardTypeWithName(jlCardTypes.getSelectedValue()))) {
                int cardCount = mw.getCardsWithCardType(mw.getCardTypeWithName(jlCardTypes.getSelectedValue()));
                int result2 = JOptionPane.showConfirmDialog(null, "Removing this card type will delete " + cardCount + " card(s), are you sure you want to continue?", "Delete Card Type", JOptionPane.YES_NO_OPTION);
            }
            try {
                mw.removeCardTypeWithName(jlCardTypes.getSelectedValue());
                update();
            } catch (Exception g) {
                return;
            }
        });
        setButtonAction("Fields", e -> {
            if (jlCardTypes.getSelectedValue() != null) {
                new CardTypeFieldsWindow(mw, parent, mw.getCardTypeWithName(jlCardTypes.getSelectedValue()));
            }
        });
        setButtonAction("Style", e -> {
            if (jlCardTypes.getSelectedValue() != null) {
                CardType cardType = mw.getCardTypeWithName(jlCardTypes.getSelectedValue());
                new CardTypeStyleWindow(mw, parent, cardType);
            }
        });
        setButtonAction("Cancel", e -> parent.dispose());

        JPanel pnlButtons = new JPanel();
        //pnlButtons.setLayout(new BoxLayout(pnlButtons, BoxLayout.Y_AXIS));
        pnlButtons.setLayout(gridBagLayout);
        for (JButton button : buttons) {
            gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1;
            gbc.weighty = 1;
            wizard.addComponent(button, pnlButtons, gridBagLayout, gbc, 0, buttons.indexOf(button), 1, 1);
        }

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        wizard.addComponent(pnlButtons, view, gridBagLayout, gbc, 1, 1, 1, 1);
        

        // Adds padding to all sides of panel before adding view
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(view, BorderLayout.CENTER);

        this.add(view);
    }

    private void setButtonAction(String btnName, ActionListener actionListener) {
        JButton btn = (JButton) this.getButtonByName(btnName);
        btn.addActionListener(actionListener);
    }

    private JButton getButtonByName(String btnName) {
        for (JButton btn : buttons) {
            if (btn.getText().equals(btnName)) {
                return btn;
            }
        }
        return null;
    }

    private void update() {
        this.removeAll();
        init();
        this.revalidate();
        this.repaint();
    }
}
