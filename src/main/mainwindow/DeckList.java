package main.mainwindow;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

// TODO: Change to JTable to show more info about/for each deck
public class DeckList extends JList {
    ListPopup popup;
    public DeckList(MainWindow mw) {
        super(mw.getDeckNames());
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setFixedCellHeight(20);
        // Sets height to number of items in the list * height of each row
        setPreferredSize(new java.awt.Dimension(200, getModel().getSize() * getFixedCellHeight()));
        popup = new ListPopup(mw);

        // two mouse listeners because of different OSes and their different ways of handling mouse events
        // https://docs.oracle.com/javase/tutorial/uiswing/events/mouselistener.html
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    // get the selected index
                    int index = locationToIndex(e.getPoint());
                    // select the item that was clicked
                    setSelectedIndex(index);
                    // show popup menu
                    popup.show(DeckList.this, e.getX(), e.getY());
                }
            }
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    // get the selected index
                    int index = locationToIndex(e.getPoint());
                    // select the item that was clicked
                    setSelectedIndex(index);
                    // show popup menu
                    popup.show(DeckList.this, e.getX(), e.getY());
                }
            }
        });
        // If item in deckList is double-clicked, open study window
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    mw.study();
                }
            }
        });
        setToolTipText("Right click to edit deck");
    }
}
