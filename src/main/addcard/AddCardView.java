package main.addcard;

import main.GridbagWizard;
import main.cardeditor.CardEditorScrollPane;
import main.cardeditor.FieldTextPane;
import main.data.Card;
import main.data.CardType;
import main.data.Deck;
import main.data.Field;
import main.helper.OkCancelButtonsPanel;
import main.mainwindow.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

public class AddCardView extends JPanel {

    private JComboBox<String> cbDecks;
    private JComboBox<String> cbCardTypes;
    private ArrayList<FieldTextPane> fields;
    private String[] cardTypeNames;
    private String[] deckNames;
    private MainWindow mw;
    private CardEditorScrollPane scrollPane;
    public AddCardView(MainWindow mw, AddCardWindow acw) {
        super();
        setPreferredSize(new Dimension(400, 400));

        this.mw = mw;
        cardTypeNames = mw.getCardTypeNames();
        deckNames = mw.getDeckNames();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel comboBoxesPanel = new JPanel();
        comboBoxesPanel.setLayout(new BoxLayout(comboBoxesPanel, BoxLayout.X_AXIS));

        cbDecks = new JComboBox<>();

        cbCardTypes = new JComboBox<>();

        comboBoxesPanel.add(cbDecks);
        // Add a space 5px between the combo boxes
        comboBoxesPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        comboBoxesPanel.add(cbCardTypes);
        comboBoxesPanel.setMaximumSize(new Dimension(getPreferredSize()));
        add(comboBoxesPanel);

        // add 5px space between the combo boxes and the fields panel
        add(Box.createRigidArea(new Dimension(0, 5)));

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
        String cardTypeName = cbCardTypes.getSelectedItem().toString();
        scrollPane = new CardEditorScrollPane(mw, new Card(cardTypeName, mw.getCardTypeWithName(cardTypeName).getFields()));
        add(scrollPane);

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
        //wizard.addComponent(okCancelButtonsPanel, this, gridBagLayout, gbc, 0, 2, 2, 1);
        okCancelButtonsPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        add(okCancelButtonsPanel);
    }

    /**
     * Update the view when the selected card type changes.
     * This will read the fields panel to the view for the currently selected card type.
     */
    public void updateView() {
        fields = new ArrayList<>();
        String cardTypeName = cbCardTypes.getSelectedItem().toString();
        Card card = new Card(cardTypeName, mw.getCardTypeWithName(cardTypeName).getFields(), Optional.empty());
        scrollPane.setCard(card);
    }

    public void addCard() {
        // Check if all the fields are empty, if so exit the method
        fields = scrollPane.getFields();
        boolean allEmpty = true;
        for(FieldTextPane field : fields) {
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
            CardType cardType = mw.getCardTypeWithName(selectedCardType);
            Field[] fields = new Field[cardType.getFields().length];
            // populate the fields with the values from the text fields
            for(int i = 0; i < fields.length; i++) {
                fields[i] = new Field(cardType.getFields()[i].getName(), this.fields.get(i).getText());
            }
            selectedDeck.addCard(new Card(cardType.getName(), fields));
            System.out.println("Added card to deck: " + selectedDeck.getName());
            updateView();
            mw.updateView();
        }
    }
}