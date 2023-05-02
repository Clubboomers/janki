package main.browser;

import main.CardViewer;
import main.data.Card;
import main.mainwindow.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;

public class CardPreview extends JFrame {
    public CardPreview(MainWindow mw, Card card) {
        super("Card Preview");
        SwingUtilities.invokeLater(() -> {
            JPanel pnlPreview = new JPanel();
            pnlPreview.setLayout(new BoxLayout(pnlPreview, BoxLayout.Y_AXIS));
            CardViewer cardViewer = new CardViewer(card, mw.getCardTypeWithName(card.getCardType()), mw.getCardTypeWithName(card.getCardType()).getHtmlFront());
            pnlPreview.add(cardViewer);

            JPanel pnlButton = new JPanel();
            pnlButton.setLayout(new BoxLayout(pnlButton, BoxLayout.X_AXIS));
            JButton btnBack = new JButton("<");
            JButton btnFront = new JButton(">");
            btnBack.setEnabled(false);
            btnBack.addActionListener(e -> {
                cardViewer.setHtmlFront();
                btnBack.setEnabled(false);
                btnFront.setEnabled(true);
            });
            btnFront.addActionListener(e -> {
                cardViewer.setHtmlBack();
                btnFront.setEnabled(false);
                btnBack.setEnabled(true);
            });
            pnlButton.add(btnBack);
            pnlButton.add(btnFront);
            pnlPreview.add(pnlButton);
            add(pnlPreview);

            // add key listener on arrow keys to move between front and back of card
            KeyListener keyListener = new KeyAdapter() {
                @Override
                public void keyPressed(java.awt.event.KeyEvent e) {
                    if (e.getKeyCode() == java.awt.event.KeyEvent.VK_LEFT && btnBack.isEnabled()) {
                        cardViewer.setHtmlFront();
                        btnBack.setEnabled(false);
                        btnFront.setEnabled(true);
                    } else if (e.getKeyCode() == java.awt.event.KeyEvent.VK_RIGHT && btnFront.isEnabled()) {
                        cardViewer.setHtmlBack();
                        btnFront.setEnabled(false);
                        btnBack.setEnabled(true);
                    }
                }
            };
            addKeyListener(keyListener); // TODO: make it work because it loses focus even if you press inside the frame
            setFocusable(true);
            requestFocus();

            FocusListener focusListener = new FocusListener() {
                @Override
                public void focusGained(java.awt.event.FocusEvent e) {
                    System.out.println("focus gained");
                }

                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    System.out.println("focus lost");
                }
            };
            addFocusListener(focusListener);

        });
        setPreferredSize(new Dimension(500, 500));
        pack();
        setVisible(true);
    }
}
