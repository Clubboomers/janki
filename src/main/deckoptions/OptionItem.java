package main.deckoptions;

import main.GridbagWizard;

import javax.swing.*;
import java.awt.*;

public class OptionItem extends JPanel {
    public static final int DROPDOWN = 0;
    public static final int TEXT = 1;
    public static final int CHECKBOX = 2;
    public static final int BUTTON = 3;
    private String name;
    private JLabel label;
    private int type;
    private GridBagLayout gridBagLayout;
    private GridBagConstraints gbc;
    private GridbagWizard wizard;

    public OptionItem(String name, int type) {
        super();
        this.name = name;
        this.type = type;
        label = new JLabel(name);
        gridBagLayout = new GridBagLayout();
        gbc = new GridBagConstraints();
        wizard = new GridbagWizard();

        this.setLayout(gridBagLayout);

        init();
    }

    private void init() {
        switch (type) {
            case DROPDOWN:
                initDropdown();
                break;
            case TEXT:
                initText();
                break;
            case CHECKBOX:
                initCheckbox();
                break;
            case BUTTON:
                initButton();
                break;
        }
    }

    private void initDropdown() {
        addLabel(label);
        JComboBox<String> dropdown = new JComboBox<>();
        addSecondComponent(dropdown);
    }

    private void initText() {
        addLabel(label);
        JTextField textField = new JTextField();
        addSecondComponent(textField);
    }

    private void initCheckbox() {
        addLabel(label);
        JCheckBox checkBox = new JCheckBox();
        addSecondComponent(checkBox);
    }

    private void initButton() {
        addLabel(label);
        JButton button = new JButton();
        addSecondComponent(button);
    }

    private void addLabel(JLabel label) {
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0.3;
        wizard.addComponent(label, this, gridBagLayout, gbc, 0, 0, 1, 1);
    }

    private void addSecondComponent(JComponent component) {
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.3;
        wizard.addComponent(component, this, gridBagLayout, gbc, 1, 0, 1, 1);
    }
}
