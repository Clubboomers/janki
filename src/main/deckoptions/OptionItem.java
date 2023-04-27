package main.deckoptions;

import main.GridbagWizard;
import main.data.Option;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

import static main.data.Option.*;

public class OptionItem extends JPanel {
    private Option option;
    private final String name;
    private Object value;
    private JComponent secondComponent;
    private int dataType;
    private int optionType;
    private GridBagLayout gridBagLayout;
    private GridBagConstraints gbc;
    private GridbagWizard wizard;
    private JPanel itemPanel;

    public OptionItem(Option option) {
        super();
        this.option = option;
        this.name = option.getName();
        this.value = option.getValue();
        this.dataType = option.getDataType();
        this.optionType = option.getOptionType();
        itemPanel = new JPanel();
        gridBagLayout = new GridBagLayout();
        gbc = new GridBagConstraints();
        wizard = new GridbagWizard();

        itemPanel.setLayout(gridBagLayout);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        init();
        add(itemPanel);

        secondComponent.setMaximumSize(new Dimension(Short.MAX_VALUE, secondComponent.getPreferredSize().height));
        // add 5px padding to this
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        // set titled border
        itemPanel.setBorder(BorderFactory.createTitledBorder(name));
    }

    private void init() {
        switch (optionType) {
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
        String[] dropdownOptions = option.getDropdownOptions();
        secondComponent = new JComboBox(dropdownOptions);
        addSecondComponent(secondComponent);
    }

    private void initText() {
        switch (dataType) {
            case STRING:
                secondComponent = new JTextField();
                break;
            case INTEGER:
                secondComponent = new ImprovedFormattedTextField(NumberFormat.getIntegerInstance(), value);
                break;
            case DOUBLE:
                secondComponent = new ImprovedFormattedTextField(NumberFormat.getNumberInstance(), value);
                break;
            case LIST:
                secondComponent = new ImprovedFormattedTextField(new IntegerListFormat(), value);
                break;
        }
        addSecondComponent(secondComponent);
    }

    private void initCheckbox() {
        secondComponent = new JCheckBox();
        addSecondComponent(secondComponent);
    }

    private void initButton() {
        secondComponent = new JButton();
        addSecondComponent(secondComponent);
    }

    private void addLabel(JLabel label) {
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0.3;
        wizard.addComponent(label, itemPanel, gridBagLayout, gbc, 0, 0, 1, 1);
    }

    private void addSecondComponent(JComponent component) {
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 1;
        wizard.addComponent(component, itemPanel, gridBagLayout, gbc, 1, 0, 1, 1);
    }
}
