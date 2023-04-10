package Main.AddDeck;

import Main.GridbagWizard;

import javax.swing.*;
import java.awt.*;

public class AddDeckView extends JPanel {
    private GridBagLayout decksViewLayout;
    public AddDeckView() {
        super();
        setPreferredSize(new Dimension(350, 150));

        GridbagWizard wizard = new GridbagWizard();
        decksViewLayout = new GridBagLayout();
        this.setLayout(decksViewLayout);
        GridBagConstraints gbc;

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        JLabel lblName = new JLabel("Deck name: ");
        wizard.addComponent(lblName, this, decksViewLayout, gbc, 0, 0, 1, 1);

        gbc = new GridBagConstraints();
        // make the text field fill the horizontal space
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(0, 0, 5, 0);
        JTextField txfName = new JTextField();
        wizard.addComponent(txfName, this, decksViewLayout, gbc, 0, 1, 3, 1);

        gbc = new GridBagConstraints();
        // make the button float to the right
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 1;
        gbc.insets = new Insets(0, 0, 5, 5);
        JButton btnAdd = new JButton("Add");
        wizard.addComponent(btnAdd, this, decksViewLayout, gbc, 1, 2, 1, 1);

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 5, 0);
        JButton btnCancel = new JButton("Cancel");
        wizard.addComponent(btnCancel, this, decksViewLayout, gbc, 2, 2, 1, 1);
    }
}
