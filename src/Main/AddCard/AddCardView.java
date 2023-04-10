package Main.AddCard;

import Main.GridbagWizard;
import Main.Data.Card;
import Main.Data.CardType;
import Main.Data.Deck;
import Main.Data.Field;
import Main.Helper.OkCancelButtonsPanel;
import Main.MW.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AddCardView extends JPanel {

    private JPanel fieldsPanel;
    private GridBagLayout gridBagLayout;
    private GridbagWizard wizard;
    private GridBagConstraints gbc;
    private JComboBox<String> cbDecks;
    private JComboBox<String> cbCardTypes;
    private ArrayList<JTextField> fields;
    private String[] cardTypeNames;
    private String[] deckNames;
    private MainWindow mw;
    public AddCardView(MainWindow mw, AddCardWindow acw) {
        super();
        setPreferredSize(new Dimension(400, 400));

        this.mw = mw;
        cardTypeNames = mw.getCardTypeNames();
        deckNames = mw.getDeckNames();

        wizard = new GridbagWizard();
        gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);

        cbDecks = new JComboBox<>();
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        wizard.addComponent(cbDecks, this, gridBagLayout, gbc, 0, 0, 1, 1);

        cbCardTypes = new JComboBox<>();
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 5, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        wizard.addComponent(cbCardTypes, this, gridBagLayout, gbc, 1, 0, 1, 1);

        for(String cardTypeName : cardTypeNames) {
            cbCardTypes.addItem(cardTypeName);
        }
        for(String deckName : deckNames) {
            cbDecks.addItem(deckName);
        }

        cbCardTypes.addActionListener(e -> {
            System.out.println("Card type changed to: " + cbCardTypes.getSelectedItem());
            updateView();
        });
        updateView();

        gbc = new GridBagConstraints();
        OkCancelButtonsPanel okCancelButtonsPanel = new OkCancelButtonsPanel("Add Card", "Cancel") {
            @Override
            public void btnOk() {
                addCard();
            }

            @Override
            public void btnCancel() {
                acw.dispose();
            }
        };
        gbc.insets = new Insets(0, 0, 5, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        wizard.addComponent(okCancelButtonsPanel, this, gridBagLayout, gbc, 0, 2, 2, 1);
    }

    public void addFieldsPanel() {
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 5, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        wizard.addComponent(fieldsPanel, this, gridBagLayout, gbc, 0, 1, 2, 1);
    }

    /**
     * Update the view when the selected card type changes.
     * This will readd the fields panel to the view for the currently selected card type.
     */
    public void updateView() {
        if(fieldsPanel != null) {
            this.remove(fieldsPanel);
        }
        fields = new ArrayList<>();
        fieldsPanel = new JPanel(); // create new panel to hold rows of fields
        fieldsPanel.setLayout(gridBagLayout);
        String cardType = (String) cbCardTypes.getSelectedItem();
        CardType ct = mw.getCardTypeWithName(cardType);
        int i = 0;
        for(Field field : ct.getFields()) {
            JPanel fieldPanel = new JPanel(); // create new panel to hold field and label
            fieldPanel.setLayout(gridBagLayout);
            JLabel lblField = new JLabel(field.getName()); // name of field
            gbc = new GridBagConstraints();
            gbc.insets = new Insets(0, 0, 5, 0);
            wizard.addComponent(lblField, fieldPanel, gridBagLayout, gbc, 0, 0, 1, 1);
            JTextField txtField = new JTextField();
            fields.add(txtField);
            gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;
            // add field to panel in second column
            wizard.addComponent(txtField, fieldPanel, gridBagLayout, gbc, 0, 1, 1, 1);

            // add field panel to fields panel in row i
            gbc = new GridBagConstraints();
            gbc.insets = new Insets(0, 0, 5, 0);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;
            wizard.addComponent(fieldPanel, fieldsPanel, gridBagLayout, gbc, 0, i, 1, 1);
            i++;
        }
        addFieldsPanel();
        fieldsPanel.revalidate();
        fieldsPanel.repaint();
    }

    // TODO: add functionality to add card to selected deck
    public void addCard() {
        // Check if all the fields are empty, if so exit the method
        boolean allEmpty = true;
        for(JTextField field : fields) {
            if(!field.getText().trim().equals("")) {
                allEmpty = false;
            }
        }
        if(allEmpty) {
            JOptionPane.showMessageDialog(this, "All fields are empty, try adding a some data to one of the fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else {
            Deck selectedDeck = mw.getDeckWithName((String) cbDecks.getSelectedItem());
            String selectedCardType = (String) cbCardTypes.getSelectedItem();
            CardType ct = mw.getCardTypeWithName(selectedCardType);
            Field[] fields = new Field[ct.getFields().length];
            // populate the fields with the values from the text fields
            for(int i = 0; i < fields.length; i++) {
                fields[i] = new Field(ct.getFields()[i].getName(), this.fields.get(i).getText());
            }
            selectedDeck.addCard(new Card(ct, fields));
            System.out.println("Added card to deck: " + selectedDeck.getName());
            updateView();
            mw.updateView();
        }
    }
}