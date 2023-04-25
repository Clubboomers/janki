package main.cardreviewer;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.*;

import main.CardViewer;
import main.GridbagWizard;
import main.data.Card;
import main.data.CardList;
import main.data.Deck;
import main.mainwindow.MainWindow;
import main.mainwindow.Menu;

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
    private JPanel pnlLabels;
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

    private void failCard() {
        deckToReview.fail(currentCard);
        nextCard();
    }

    private void changeButton() {
        // TODO Auto-generated method stub
        JButton btnPass = new JButton("Pass");
        btnPass.addActionListener(e -> {
            passCard();
        });
        JButton btnFail = new JButton("Fail");
        btnFail.addActionListener(e -> {
            failCard();
        });
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
        JPanel pnlMerge = new JPanel();
        pnlMerge.setLayout(new BoxLayout(pnlMerge, BoxLayout.Y_AXIS));

        pnlLabels = new JPanel();
        initLabels();

        pnlLabels.setAlignmentX(CENTER_ALIGNMENT);
        pnlMerge.add(pnlLabels);

        JButton btnFlip = new JButton("Flip");
        btnFlip.addActionListener(e -> flipCard());

        btnFlip.setAlignmentX(CENTER_ALIGNMENT);
        pnlMerge.add(btnFlip);

        pnlBottom.add(pnlMerge);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 0;
        wizard.addComponent(pnlBottom, this, gridBagLayout, gbc, 0, 2, 1, 1);
    }

    private void initLabels() {
        // TODO Auto-generated method stub
        int whatDeck = deckToReview.whatDeckIsThisCardIn(currentCard);

        pnlLabels.setLayout(new BoxLayout(pnlLabels, BoxLayout.X_AXIS));
        JLabel lblNewCards = new JLabel("" + deckToReview.getNewCardCount()+"");
        if (whatDeck == CardList.NEW_CARDS) {
            Font font = lblNewCards.getFont();      // you have to add this spaghetti code to make the underline work
            Map attributes = font.getAttributes();  // otherwise the label will get too big
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            lblNewCards.setFont(font.deriveFont(attributes));
        }
        lblNewCards.setForeground(new Color(110,160,220));
        pnlLabels.add(lblNewCards);
        // add 5px space
        pnlLabels.add(Box.createHorizontalStrut(5));
        JLabel lblLearningCards = new JLabel(deckToReview.getLearningCardCount()+"");
        if (whatDeck == CardList.LEARNING_CARDS) {
            Font font = lblLearningCards.getFont();
            Map attributes = font.getAttributes();
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            lblLearningCards.setFont(font.deriveFont(attributes));
        }
        lblLearningCards.setForeground(new Color(255, 165, 0));
        pnlLabels.add(lblLearningCards);
        pnlLabels.add(Box.createHorizontalStrut(5));
        JLabel lblGraduatedCards = new JLabel(deckToReview.getGraduatedCardCount()+"");
        if (whatDeck == CardList.GRADUATED_CARDS) {
            Font font = lblGraduatedCards.getFont();
            Map attributes = font.getAttributes();
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            lblGraduatedCards.setFont(font.deriveFont(attributes));
        }
        lblGraduatedCards.setForeground(new Color(0, 128, 0));
        pnlLabels.add(lblGraduatedCards);
    }
}
