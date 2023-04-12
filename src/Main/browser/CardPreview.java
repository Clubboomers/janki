package Main.browser;

import Main.CardViewer;
import Main.data.Card;

import javax.swing.*;
import java.awt.*;

public class CardPreview extends JFrame {
    public CardPreview(Card card) {
        super("Card Preview");
        JPanel pnlPreview = new JPanel();
        pnlPreview.setLayout(new BoxLayout(pnlPreview, BoxLayout.Y_AXIS));
        CardViewer cardViewer = new CardViewer(card, card.getCardType().getHtmlFront());
        pnlPreview.add(cardViewer);

        JPanel pnlButton = new JPanel();
        pnlButton.setLayout(new BoxLayout(pnlButton, BoxLayout.X_AXIS));
        JButton btnBack = new JButton("<");
        JButton btnFront = new JButton(">");
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
        setPreferredSize(new Dimension(500, 500));
        pack();
        setVisible(true);
        this.setVisible(true);
    }
}
