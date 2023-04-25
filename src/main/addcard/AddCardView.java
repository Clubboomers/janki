package main.addcard;

import main.GridbagWizard;
import main.data.Card;
import main.data.CardType;
import main.data.Deck;
import main.data.Field;
import main.helper.OkCancelButtonsPanel;
import main.mainwindow.MainWindow;

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
    private JScrollPane scrollPane;
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

        fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));

        setupScrollPane();

        cbCardTypes.addActionListener(e -> {
            System.out.println("Card type changed to: " + cbCardTypes.getSelectedItem());
            updateView();
        });
        updateView();
        add(scrollPane);

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
        //wizard.addComponent(okCancelButtonsPanel, this, gridBagLayout, gbc, 0, 2, 2, 1);
        okCancelButtonsPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        add(okCancelButtonsPanel);
    }

    // TODO: fix scroll pane so that it doesn't break when adding too many fields
    private void setupScrollPane() {
        scrollPane = new JScrollPane(fieldsPanel);
        //scrollPane.setPreferredSize(new Dimension(this.getPreferredSize()));
        //scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // Add 5 px inner padding to the scrollPane
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        scrollPane.setAlignmentY(Component.TOP_ALIGNMENT);
        // always visible
    }

    /**
     * Update the view when the selected card type changes.
     * This will read the fields panel to the view for the currently selected card type.
     */
    public void updateView() {
        fields = new ArrayList<>();
        fieldsPanel.removeAll();
        String cardType = (String) cbCardTypes.getSelectedItem();
        CardType ct = mw.getCardTypeWithName(cardType);
        int i = 0;
        for(Field field : ct.getFields()) {
            JPanel fieldPanel = new JPanel(); // create new panel to hold field and label
            fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
            JLabel lblField = new JLabel(field.getName()); // name of field
            lblField.setAlignmentX(Component.LEFT_ALIGNMENT);
            fieldPanel.add(lblField);
            JTextField txtField = new JTextField();
            txtField.setPreferredSize(new Dimension(200, 50));
            fields.add(txtField);
            fieldPanel.setAlignmentY(Component.TOP_ALIGNMENT);
            // add field to panel in second column
            fieldPanel.add(txtField);
            fieldPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            // add panel to fields panel
            fieldsPanel.add(fieldPanel);
            i++;
        }
        fieldsPanel.revalidate();
        fieldsPanel.repaint();
    }

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