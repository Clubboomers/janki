package Main.mainwindow;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu extends JPanel {
    public Menu(MainWindow mw) {
        super();
        // Make preferred size of panel as wide as possible
        this.setPreferredSize(new java.awt.Dimension(Integer.MAX_VALUE, 30));
        FlowLayout layout = new FlowLayout();
        this.setLayout(layout);
        
        JLabel lblHome = new JLabel("<html><u>" + "Home" + "</u></html>");

        // Change cursor to hand when mouse is over the label
        lblHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Add listener to label
        lblHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mw.showHome();
            }
        });
        // Add

        this.add(lblHome);

        JLabel lblBrowse = new JLabel("<html><u>" + "Browse" + "</u></html>");
        lblBrowse.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblBrowse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mw.showBrowse();
            }
        });
        this.add(lblBrowse);

        //setBackground(new java.awt.Color(255, 255, 255));
    }
}
