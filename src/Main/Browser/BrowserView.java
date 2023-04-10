package Main.Browser;

import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Main.Data.Card;
import Main.Data.Deck;
import Main.MW.MainWindow;

public class BrowserView extends JPanel {
        public BrowserView(MainWindow mw, BrowserWindow parent) {
            super();
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            ArrayList<Deck> decks = mw.getDecks();
            ArrayList<Card> cards = new ArrayList<Card>();
            for(Deck deck : decks) {
                cards.addAll(deck.getCards());
            }

            String[][] data = new String[cards.size()][6];

            for(Deck deck : decks) {
                for (int i = 0; i < deck.getCardCount(); i++) {
                    data[i][0] = (String) deck.getCard(i).getFieldValue(deck.getCard(i).getSortField());
                    data[i][1] = deck.getName();
                    data[i][2] = deck.getCard(i).getCardType().getName();
                    data[i][3] = unixToHumanReadable(deck.getCard(i).getCreated()).toString();
                    data[i][4] = unixToHumanReadable(deck.getCard(i).getInterval());
                    data[i][5] = unixToHumanReadable(deck.getCard(i).getDue());
                }
            }
            
            String[] columnNames = {"Sort Field", "Deck", "Card Type", "Created", "Interval", "Due"};
            JTable cardTable = new JTable(data, columnNames);
            // set to uneditable
            cardTable.setDefaultEditor(Object.class, null);
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
}
