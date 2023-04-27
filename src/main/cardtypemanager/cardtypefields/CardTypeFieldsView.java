package main.cardtypemanager.cardtypefields;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.GridbagWizard;
import main.data.CardType;
import main.mainwindow.MainWindow;

public class CardTypeFieldsView extends JPanel {
    ArrayList<JButton> buttons;
    MainWindow mw;
    CardType cardType;
    CardTypeFieldsWindow ctfw;
    public CardTypeFieldsView(MainWindow mw, CardTypeFieldsWindow ctfw, CardType cardType) {
        super();
        this.mw = mw;
        this.ctfw = ctfw;
        this.cardType = cardType;
        this.setPreferredSize(new Dimension(300, 200));
        init();
    }

    /**
     * Adds the components, listeners, and layout to the panel
     */
    private void init() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridbagWizard wizard = new GridbagWizard();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(gridBagLayout);

        gbc = new GridBagConstraints();
        JLabel lblFields = new JLabel("Fields: ");
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        wizard.addComponent(lblFields, this, gridBagLayout, gbc, 0, 0, 1, 1);

        gbc = new GridBagConstraints();
        JList<String> jlFields = new JList<>();
        jlFields.setListData(cardType.getFieldNames());
        // TODO: if selected field has the same name as cardType.getSortField().getName(), disable the "Primary" button
        jlFields.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                buttons.get(3).setToolTipText(null);
                return;
            }
            if (jlFields.getSelectedIndex() == -1) {
                for (JButton button : buttons) {
                    button.setEnabled(false);
                }
            } else {
                for (JButton button : buttons) {
                    button.setEnabled(true);
                }
                if (cardType.getSortField().getName().equals(jlFields.getSelectedValue())) {
                    buttons.get(3).setEnabled(false);
                    buttons.get(3).setToolTipText("Field " + "\"" + jlFields.getSelectedValue() + "\"" + " is already the primary field.");
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(jlFields);
        scrollPane.setPreferredSize(new Dimension(200, 200));
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        wizard.addComponent(scrollPane, this, gridBagLayout, gbc, 0, 1, 1, 1);

        buttons = new ArrayList<>();
        buttons.add(new JButton("Add"));
        buttons.add(new JButton("Rename"));
        buttons.add(new JButton("Delete"));
        buttons.add(new JButton("Primary"));
        buttons.get(3).setToolTipText("Select a field to make it the primary field."); // "Primary" button tooltip text
        buttons.add(new JButton("Cancel"));
        JPanel pnlButtons = new JPanel();
        pnlButtons.setLayout(gridBagLayout);
        for (JButton button : buttons) {
            gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1;
            gbc.weighty = 1;
            wizard.addComponent(button, pnlButtons, gridBagLayout, gbc, 0, buttons.indexOf(button), 1, 1);
        }
        setButtonAction("Add", new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // TODO Auto-generated method stub
                try {
                    String newName = JOptionPane.showInputDialog(null, "New name:", "Add", JOptionPane.PLAIN_MESSAGE).trim();
                    cardType.addField(newName);
                    update();
                } catch (Exception g) {
                    return;
                }
            }
        });
        setButtonAction("Rename", new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // TODO Auto-generated method stub
                if (jlFields.getSelectedValue() == null) {
                    return;
                }
                try {
                    String newName = JOptionPane.showInputDialog(null, "New name:", "Rename", JOptionPane.PLAIN_MESSAGE).trim();
                    cardType.renameField(jlFields.getSelectedValue(), newName);
                    update();
                } catch (Exception g) {
                    return;
                }
            }
        });
        setButtonAction("Delete", new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // TODO Auto-generated method stub
                if (jlFields.getSelectedValue() == null) {
                    return;
                }
                // Bring up a confirmation dialog
                int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the field " + "\"" + jlFields.getSelectedValue() + "\"?", "Warning", JOptionPane.YES_NO_OPTION);
                if (dialogResult != JOptionPane.YES_OPTION) {
                    return;
                }
                try {
                    cardType.removeField(jlFields.getSelectedValue());
                    update();
                } catch (Exception g) {
                    throw g;
                }
            }
        });
        setButtonAction("Primary", new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // TODO Auto-generated method stub
                if (jlFields.getSelectedValue() == null) {
                    return;
                }
                try {
                    cardType.setSortField(jlFields.getSelectedValue());
                    buttons.get(3).setEnabled(false);
                    update();
                } catch (Exception g) {
                    throw g;
                }
            }
        });
        setButtonAction("Cancel", e -> ctfw.dispose());

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weighty = 1;
        wizard.addComponent(pnlButtons, this, gridBagLayout, gbc, 1, 1, 1, 1);
    }

    private void update() {
        this.removeAll();
        init();
        this.revalidate();
        this.repaint();
    }

    private void setButtonAction(String buttonName, ActionListener action) {
        for (JButton button : buttons) {
            if (button.getText().equals(buttonName)) {
                button.addActionListener(action);
            }
        }
    }
}
