package main.mainwindow;

import main.data.Deck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import static sun.swing.SwingUtilities2.convertColumnIndexToModel;

// TODO: Change to JTable to show more info about/for each deck
public class DeckList1 extends JScrollPane {
    private static final String[] columnNames = {"Deck", "Due", "Learning", "New"};
    private JTable deckTable;
    public static final Color NEW_COLOR = new Color(80, 168, 255);
    public static final Color LEARNING_COLOR = new Color(227, 155, 0);
    public static final Color DUE_COLOR = new Color(0, 210, 0);
    public static final int NAME_COLUMN = 0;
    public static final int DUE_COLUMN = 1;
    public static final int LEARNING_COLUMN = 2;
    public static final int NEW_COLUMN = 3;
    private ListPopup popup;

    public DeckList1(MainWindow mw) {
        super();

        String[][] data = DeckList1.getDataFromDecks(mw.getDecks());

        deckTable = new JTable(data, columnNames);
        deckTable.setDefaultRenderer(Object.class, new CellRenderer(new Color[] {deckTable.getForeground(), DUE_COLOR, LEARNING_COLOR, NEW_COLOR}));

        deckTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ArrayList<Deck> decks = mw.getDecks();
        updateDecks(decks);

        deckTable.setDefaultEditor(Object.class, null); // set to uneditable
        deckTable.getTableHeader().setReorderingAllowed(false);
        deckTable.setComponentPopupMenu(new ListPopup(mw));

        deckTable.setRowHeight(20);

        setPreferredSize(new Dimension(225, deckTable.getRowCount() * deckTable.getRowHeight()
                + deckTable.getTableHeader().getPreferredSize().height + 2)); // TODO: fix this hack

        // TODO: set a max height to the scroll pane

        deckTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // check if deck has due cards
                    if (mw.getDeckWithName(getSelectedDeck()).getDueCards().size() > 0) {
                        mw.study();
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int row = deckTable.rowAtPoint(e.getPoint());
                deckTable.setRowSelectionInterval(row, row);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int row = deckTable.rowAtPoint(e.getPoint());
                deckTable.setRowSelectionInterval(row, row);
            }
        });
        deckTable.setToolTipText("Right click to edit deck");

        setViewportView(deckTable);
    }

    public void updateDecks(ArrayList<Deck> decks) {
        String[][] data = getDataFromDecks(decks);

        /*deckTable.setModel(new javax.swing.table.DefaultTableModel(
                data,
                columnNames
        ) {
            Class[] types = new Class [] {
                    java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                    false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });*/
    }

    public JTable getDeckTable() {
        return deckTable;
    }

    public String getSelectedDeck() {
        return (String) deckTable.getValueAt(deckTable.getSelectedRow(), NAME_COLUMN);
    }

    public static String[][] getDataFromDecks(ArrayList<Deck> decks) {
        String[][] data = new String[decks.size()][columnNames.length];

        for (int i = 0; i < decks.size(); i++) {
            data[i][0] = decks.get(i).getName();
            data[i][1] = Integer.toString(decks.get(i).getGraduatedCardCount());
            data[i][2] = Integer.toString(decks.get(i).getLearningCardCount());
            data[i][3] = Integer.toString(decks.get(i).getNewCardsCount());
        }
        return data;
    }
}
