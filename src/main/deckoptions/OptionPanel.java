package main.deckoptions;

import main.GridbagWizard;
import main.data.Option;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

import static main.data.Option.*;

public class OptionPanel extends JPanel {
    private Option option;
    private final String name;
    private final String description;

    @Override
    public String getName() {
        return name;
    }

    private Object value;
    private JComponent secondComponent;
    private int dataType;
    private int optionType;
    private GridBagLayout gridBagLayout;
    private GridBagConstraints gbc;
    private GridbagWizard wizard;
    private JPanel itemPanel;

    public OptionPanel(Option option) {
        super();
        this.option = option;
        this.name = option.getName();
        this.description = option.getDescription();
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

    /**
     * Add the second component to the itemPanel
     */
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

    /**
     * Initialize the second component as a dropdown menu
     * and populate it with the option's dropdown options
     */
    private void initDropdown() {
        String[] dropdownOptions = option.getDropdownOptions();
        secondComponent = new JComboBox(dropdownOptions);
        addSecondComponent(secondComponent);
        // set selected index to value
        ((JComboBox) secondComponent).setSelectedIndex((int) value);
    }

    /**
     * Initialize the second component as a JTextField or JFormattedTextField
     * set the formatting to the correct data type
     * set the text to the value of the current option
     */
    private void initText() {
        switch (dataType) {
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

    /**
     * Initialize the second component as a JCheckBox
     */
    private void initCheckbox() {
        secondComponent = new JCheckBox();
        addSecondComponent(secondComponent);
    }

    /**
     * Initialize the second component as a JButton
     */
    private void initButton() {
        secondComponent = new JButton();
        addSecondComponent(secondComponent);
    }

    /**
     * Add the second component to the panel
     * @param component The component you want to add (a JTextField, JComboBox, JCheckBox, JButton, etc.)
     */
    private void addSecondComponent(JComponent component) {
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 1;
        component.setToolTipText(description);
        wizard.addComponent(component, itemPanel, gridBagLayout, gbc, 1, 0, 1, 1);
    }

    /**
     * Get the value from the second component (can be a JTextField, JComboBox, JCheckBox, JButton, etc.)
     * @return the value from the second component (as an Object)
     */
    public Object getValue() {
        switch (optionType) {
            case DROPDOWN:
                return getDropdownIndex();
            case TEXT:
                switch (dataType) {
                    case STRING:
                        return getText();
                    case INTEGER:
                        return getInt();
                    case DOUBLE:
                        return getDouble();
                    case LIST:
                        return getIntList();
                }
            case CHECKBOX:
                return ((JCheckBox) secondComponent).isSelected();
            case BUTTON:
                return ((JButton) secondComponent).getText();
        }
        return null;
    }

    /**
     * Treat the second component as a JTextField and get the text from it
     * @return the text in the second component
     */
    private String getText() {
        return ((ImprovedFormattedTextField) secondComponent).getText();
    }

    /**
     * Get the text from the second component, check if it is a valid integer, and return it
     * @return
     */
    private Integer getInt() {
        ImprovedFormattedTextField txtField = secondComponent instanceof ImprovedFormattedTextField ? (ImprovedFormattedTextField) secondComponent : null;
        if (txtField.validContent()) {
            String text = getText();
            // remove all spaces
            text = text.replaceAll("\\D", "");
            return Integer.parseInt(text);
        } else {
            return null;
        }
    }

    /**
     * Get the text from the second component, check if it is a valid double, and return it
     * @return
     */
    private Double getDouble() {
        ImprovedFormattedTextField txtField = secondComponent instanceof ImprovedFormattedTextField ? (ImprovedFormattedTextField) secondComponent : null;
        if (txtField.validContent()) {
            String text = getText();
            text = text.replaceAll(",", ".");
            return Double.valueOf(text);
        } else {
            return null;
        }
    }

    /**
     * Get the text from the second component, split it into a String array, convert each String to an int, and return it
     * @return
     */
    private int[] getIntList() {
        ImprovedFormattedTextField txtField = secondComponent instanceof ImprovedFormattedTextField ? (ImprovedFormattedTextField) secondComponent : null;
        if (!txtField.validContent()) {
            return null;
        }
        String[] stringList = getText().split(" ");
        int[] intList = new int[stringList.length];
        for (int i = 0; i < stringList.length; i++) {
            intList[i] = Integer.parseInt(stringList[i]);
        }
        return intList;
    }

    /**
     * Get the index of the selected item in the second component (a JComboBox)
     * @return the index of the selected item in the second component
     */
    private int getDropdownIndex() {
        return ((JComboBox) secondComponent).getSelectedIndex();
    }
}
