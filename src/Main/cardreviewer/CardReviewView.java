package Main.cardreviewer;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Stack;

import javax.swing.*;

import Main.CardViewer;
import Main.GridbagWizard;
import Main.data.Card;
import Main.data.Deck;
import Main.mainwindow.MainWindow;
import Main.mainwindow.Menu;

public class CardReviewView extends JPanel {
    private MainWindow mw;
    private Deck deckToReview;
    private GridbagWizard wizard;
    private GridBagLayout gridBagLayout;
    private GridBagConstraints gbc;
    private CardViewer cardFront;
    private CardViewer cardBack;
    private JPanel pnlBottom;
    private Card currentCard;
    public CardReviewView(MainWindow mw, Deck deckToReview) {
        super();
        this.mw = mw;
        this.deckToReview = deckToReview;
        this.setPreferredSize(mw.getPreferredSize());
        gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        wizard = new GridbagWizard();
        gbc = new GridBagConstraints();

        currentCard = deckToReview.getNextCardInReview();

        // Add components
        Menu menu = new Menu(mw);
        menu.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 0;
        wizard.addComponent(menu, this, gridBagLayout, gbc, 0, 0, 1, 1);

        cardFront = new CardViewer(currentCard, currentCard.getCardType().getHtmlFront());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        wizard.addComponent(cardFront, this, gridBagLayout, gbc, 0, 1, 1, 1);

        resetPnlBottom();

        //cardBack = new CardViewer(deckToReview.getCard(0), deckToReview.getCard(0).getCardType().getHtmlBack());
    }

    private void flipCard() {
        this.remove(cardFront);
        this.remove(pnlBottom);
        changeButton();
        cardBack = new CardViewer(currentCard, currentCard.getCardType().getHtmlBack());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        wizard.addComponent(cardBack, this, gridBagLayout, gbc, 0, 1, 1, 1);
        revalidate();
        repaint();
    }

    private void nextCard() {
        this.remove(cardBack);
        if (deckToReview.finishedReview()) {
            mw.showHome();
            return;
        }
        else
            currentCard = deckToReview.getNextCardInReview();

        cardFront = new CardViewer(currentCard, currentCard.getCardType().getHtmlFront());

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        wizard.addComponent(cardFront, this, gridBagLayout, gbc, 0, 1, 1, 1);
        resetPnlBottom();
        revalidate();
        repaint();
    }

    private void passCard() {
        deckToReview.pass(currentCard);
        nextCard();
    }

    private void changeButton() {
        // TODO Auto-generated method stub
        JButton btnPass = new JButton("Pass");
        btnPass.addActionListener(e -> {
            passCard();
        });
        JButton btnFail = new JButton("Fail");
        pnlBottom = new JPanel();
        pnlBottom.add(btnFail);
        pnlBottom.add(btnPass);
        wizard.addComponent(pnlBottom, this, gridBagLayout, gbc, 0, 2, 1, 1);
    }

    private void resetPnlBottom() {
        // TODO Auto-generated method stub
        if (pnlBottom != null) {
            this.remove(pnlBottom);
        }
        pnlBottom = new JPanel();
        JButton btnFlip = new JButton("Flip");
        btnFlip.addActionListener(e -> flipCard());
        pnlBottom.add(btnFlip);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 0;
        wizard.addComponent(pnlBottom, this, gridBagLayout, gbc, 0, 2, 1, 1);
    }
}
