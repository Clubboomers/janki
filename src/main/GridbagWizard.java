package main;

import java.awt.*;

/**
 * Class to make adding components to a GridBagLayout easier
 * by reducing the amount of code needed to add a component
 */
public class GridbagWizard {
    public GridbagWizard() {

    }

    /**
     * Add a component to a container using a GridBagLayout
     * @param component component to add
     * @param yourcontainer container to add component to
     * @param layout layout to use
     * @param gbc constraints to use
     * @param gridx x position
     * @param gridy y position
     * @param gridwidth width
     * @param gridheight height
     */
    public void addComponent(Component component, Container yourcontainer, GridBagLayout layout, GridBagConstraints gbc, int gridx, int gridy, int gridwidth, int gridheight){

        gbc.gridx = gridx;
        gbc.gridy = gridy;

        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;

        layout.setConstraints(component, gbc);
        yourcontainer.add(component);
    }
}
