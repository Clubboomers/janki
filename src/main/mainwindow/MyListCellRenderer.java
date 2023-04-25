package main.mainwindow;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class MyListCellRenderer extends DefaultListCellRenderer {
    MainWindow mw;
    public MyListCellRenderer(MainWindow mw) {
        super();
        this.mw = mw;
    }
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {

        // Call the super method to get the JLabel for the value
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        // Create a new JLabel to display next to the value
        JLabel lblCards = new JLabel("Due cards: " + mw.getDeckWithName(value.toString()).getDueCardsCount());

        // Add the iconLabel to a JPanel to control its layout
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        panel.add(lblCards, BorderLayout.EAST);

        return panel;
    }
}
