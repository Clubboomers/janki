package Main.CardReviewer;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;

import Main.CardViewer;
import Main.GridbagWizard;
import Main.Data.Deck;
import Main.MW.MainWindow;
import Main.MW.Menu;

public class CardReviewView extends JPanel {
    private MainWindow mw;
    private Deck deckToReview;
    private GridbagWizard wizard;
    private GridBagLayout gridBagLayout;
    private GridBagConstraints gbc;
    private CardViewer cardFront;
    private CardViewer cardBack;
    private JPanel pnlBottom;
    public CardReviewView(MainWindow mw, Deck deckToReview) {
        super();
        this.mw = mw;
        this.deckToReview = deckToReview;
        this.setPreferredSize(mw.getPreferredSize());
        gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        wizard = new GridbagWizard();
        gbc = new GridBagConstraints();

        // Add components
        Menu menu = new Menu(mw);
        menu.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 0;
        wizard.addComponent(menu, this, gridBagLayout, gbc, 0, 0, 1, 1);

        cardFront = new CardViewer(deckToReview.getCard(0), deckToReview.getCard(0).getCardType().getHtmlFront());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        wizard.addComponent(cardFront, this, gridBagLayout, gbc, 0, 1, 1, 1);

        pnlBottom = new JPanel();
        JButton btnFlip = new JButton("Flip");
        btnFlip.addActionListener(e -> flipCard());

        pnlBottom.add(btnFlip);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 0;
        wizard.addComponent(pnlBottom, this, gridBagLayout, gbc, 0, 2, 1, 1);

        cardBack = new CardViewer(deckToReview.getCard(0), deckToReview.getCard(0).getCardType().getHtmlBack());
    }

    private void flipCard() {
        this.remove(cardFront);
        this.remove(pnlBottom);
        changeButton();
        revalidate();
        repaint();
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        wizard.addComponent(cardBack, this, gridBagLayout, gbc, 0, 1, 1, 1);
    }

    private void changeButton() {
        // TODO Auto-generated method stub
        JButton btnPass = new JButton("Pass");
        JButton btnFail = new JButton("Fail");
        pnlBottom = new JPanel();
        pnlBottom.add(btnPass);
        pnlBottom.add(btnFail);
        wizard.addComponent(pnlBottom, this, gridBagLayout, gbc, 0, 2, 1, 1);
    }
}
