package main.browser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.*;

import main.cardeditor.CardEditWindow;
import main.data.Card;
import main.data.Deck;
import main.mainwindow.MainWindow;

// TODO: Add a search bar
// TODO: Add card editor
public class BrowserView extends JPanel {
        private MainWindow mw;
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

            for (int i = 0; i < cards.size(); i++) {
                System.out.println(cards.get(i));
            }

            for(Deck deck : decks) {
                for (int i = 0; i < deck.getCardCount(); i++) {
                    data[i][0] = (String) deck.getCard(i).getFieldValue(deck.getCard(i).getSortField());
                    data[i][1] = deck.getName();
                    data[i][2] = deck.getCard(i).getCardType().getName();
                    data[i][3] = unixToHumanReadable(deck.getCard(i).getCreated()).toString();
                    data[i][4] = unixToHumanReadable(deck.getCard(i).getInterval());
                    data[i][5] = unixToHumanReadable(deck.getCard(i).getDue());
                    data[i][6] = String.valueOf(deck.getCard(i).getCardId());
                }
            }

            String[] columnNames = {"Sort Field", "Deck", "Card Type", "Created", "Interval", "Due", "Card ID"};


            JTable cardTable = new JTable(data, columnNames);
            cardTable.setDefaultEditor(Object.class, null); // set to uneditable
            // TODO: when the user clicks on a cell, display the card in a CardViewer panel
            // later
            // TODO: add a JPopupMenu to the table that allows the user to delete and edit the cards when they right click on a cell
            JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem editItem = new JMenuItem("Edit");
            editItem.addActionListener(e -> {
                int row = cardTable.getSelectedRow();
                int cardID = Integer.parseInt((String) cardTable.getValueAt(row, 6));
                Card card = mw.getCard(cardID);
                new CardEditWindow(mw, card);
            });
            popupMenu.add(editItem);
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

                    // if the user double-clicks a row, open the card in a new JFrame containing a CardViewer
                    if (e.getClickCount() == 2) {
                        int cardID = Integer.parseInt((String) cardTable.getValueAt(row, 6));
                        Card card = mw.getCard(cardID);
                        new CardPreview(card);
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