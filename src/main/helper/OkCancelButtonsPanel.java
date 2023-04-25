package main.helper;

import javax.swing.*;

import main.GridbagWizard;

import java.awt.*;

public abstract class OkCancelButtonsPanel extends JPanel {
    GridBagLayout mwLayout;
    GridbagWizard wizard;
    JButton btnOk;
    public OkCancelButtonsPanel() {
        super();
        constructor("Ok", "Cancel");
    }

    public OkCancelButtonsPanel(String btnOkString, String btnCancelString) {
        super();
        constructor(btnOkString, btnCancelString);
    }

    public abstract void btnOk();
    public abstract void btnCancel();

    public void constructor(String btnOkString, String btnCancelString) {
        wizard = new GridbagWizard();
        mwLayout = new GridBagLayout();
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        GridBagConstraints gbc;

        gbc = new GridBagConstraints();
        btnOk = new JButton(btnOkString);
        btnOk.addActionListener(e -> {
            btnOk();
        });
        gbc.fill = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 5);
        add(btnOk);
        add(Box.createRigidArea(new Dimension(5, 0)));
        //wizard.addComponent(btnOk, this, mwLayout, gbc, 0, 0, 1, 1);
        
        gbc =  new GridBagConstraints();
        JButton btnCancel = new JButton(btnCancelString);
        btnCancel.addActionListener(e -> {
            btnCancel();
        });
        gbc.fill = GridBagConstraints.CENTER;
        add(btnCancel);
        //wizard.addComponent(btnCancel, this, mwLayout, gbc, 1, 0, 1, 1);
    }
}
