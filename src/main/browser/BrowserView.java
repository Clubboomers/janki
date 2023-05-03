package main.browser;

import main.cardeditor.CardEditWindow;
import main.data.Card;
import main.data.Deck;
import main.mainwindow.MainWindow;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.Normalizer;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

// TODO: Add a search bar
// TODO: Add card editor
public class BrowserView extends JPanel {
        private MainWindow mw;
        private BrowserWindow parent;
        public BrowserView(MainWindow mw, BrowserWindow parent) {
            super();
            this.mw = mw;
            init();
        }

        private void init() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            ArrayList<Deck> decks = mw.getDecks();
            ArrayList<Card> cards = new ArrayList<Card>();
            for(Deck deck : decks) {
                cards.addAll(deck.getCards());
            }

            String[][] data = new String[cards.size()][7];

            for(Deck deck : decks) {
                for(Card card : deck.getCards()) {
                    int i = cards.indexOf(card);
                    data[i][0] = card.getFieldValue(mw.getCardTypeWithName(card.getCardType()).getSortField().getName());
                    data[i][1] = deck.getName();
                    data[i][2] = card.getCardType();
                    data[i][3] = unixToHumanReadable(card.getCreated());
                    data[i][4] = unixToHumanReadable(card.getInterval());
                    data[i][5] = unixToHumanReadable(card.getDue());
                    data[i][6] = Integer.toString(card.getCardId());
                }
            }

            String[] columnNames = {"Sort Field", "Deck", "Card Type", "Created", "Interval", "Due", "Card ID"};


            JTable cardTable = new JTable(data, columnNames);
            // set tooltiptext for 1st column
            cardTable.getColumnModel().getColumn(0).setCellRenderer(new TooltipRenderer(cardTable.getDefaultRenderer(Object.class)));

            cardTable.setDefaultEditor(Object.class, null); // set to uneditable
            cardTable.getTableHeader().setReorderingAllowed(false);

            // TODO: when the user clicks on a cell, display the card in a CardViewer panel
            // later
            // TODO: add a JPopupMenu to the table that allows the user to delete and edit the cards when they right click on a cell
            JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem editItem = new JMenuItem("Edit");
            editItem.addActionListener(e -> {
                int row = cardTable.getSelectedRow();
                int cardID = Integer.parseInt((String) cardTable.getValueAt(row, 6));
                Card card = mw.getCard(cardID);
                new CardEditWindow(mw, parent, card);
            });
            popupMenu.add(editItem);
            JMenuItem previewItem = new JMenuItem("Preview");
            previewItem.addActionListener(e -> {
                int row = cardTable.getSelectedRow();
                int cardID = Integer.parseInt((String) cardTable.getValueAt(row, 6));
                Card card = mw.getCard(cardID);
                new CardPreview(mw, card);
            });
            popupMenu.add(previewItem);
            JMenuItem deleteItem = new JMenuItem("Delete");
            deleteItem.addActionListener(e -> {
                int row = cardTable.getSelectedRow();
                int cardID = Integer.parseInt((String) cardTable.getValueAt(row, 6));
                mw.deleteCard(cardID);
                update();
            });
            popupMenu.add(deleteItem);
            cardTable.setComponentPopupMenu(popupMenu);
            // on click, select the row where the user clicked
            cardTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    int row = cardTable.rowAtPoint(e.getPoint());
                    cardTable.setRowSelectionInterval(row, row);
                    System.out.println("cardID: " + cardTable.getValueAt(row, 6));

                    // if the user double-clicks a row, open the card in a new JFrame containing a CardViewer
                    if (e.getClickCount() == 2) {
                        int cardID = Integer.parseInt((String) cardTable.getValueAt(row, 6));
                        Card card = mw.getCard(cardID);
                        new CardEditWindow(mw, parent, card);
                    }
                }
            });
            JScrollPane scrollPane = new JScrollPane(cardTable);
            add(scrollPane);
        }

        private String unixToHumanReadable(long unixTime) {
            if (unixTime == 0) {
                return "New";
            }
            Instant instant = Instant.ofEpochSecond(unixTime);
            LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
            int year = dateTime.getYear();
            if (year < 2000) {
                // if the year is less than 1900, it's probably an interval instead of a date
                // so we'll just return the interval
                Duration duration = Duration.ofMillis(unixTime);
                if (duration.toDays() >= 365) {
                    long years = duration.toDays() / 365;
                    return String.format("%d year%s", years, years > 1 ? "s" : "");
                } else if (duration.toDays() >= 60) {
                    long months = duration.toDays() / 30;
                    return String.format("%d month%s", months, months > 1 ? "s" : "");
                } else {
                    long days = duration.toDays();
                    return String.format("%d day%s", days, days > 1 ? "s" : "");
                }
            }
            return new Date(unixTime).toString();
        }

        private void update() {
            this.removeAll();
            init();
            this.repaint();
            this.revalidate();
        }
}
