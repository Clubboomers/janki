package main.cardtypemanager.cardtypestyle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import main.CardViewer;
import main.GridbagWizard;
import main.data.CardType;
import main.helper.HtmlHelper;
import main.helper.OkCancelButtonsPanel;
import main.mainwindow.MainWindow;

// TODO: break this class up into smaller classes
public class CardTypeStyleView extends JPanel {
    private String tempHtmlFront;       /* Temporary variables  */
    private String tempHtmlBack;        /* the user can play    */
    private String tempCss;             /* around with before   */
    private String tempHtmlBodyFront;   /* finalizing the       */
    private String tempHtmlBodyBack;    /* changes              */
    private CardViewer frontPreview;
    private CardViewer backPreview;
    private CardType cardType;
    private GridBagLayout gridBagLayout;
    private GridBagConstraints gbc;
    private GridbagWizard wizard;
    private JPanel pnlFrontPreview;
    private JPanel pnlBackPreview;
    private MainWindow mw;
    public CardTypeStyleView(MainWindow mw, CardTypeStyleWindow parent, CardType cardType) {
        super();
        this.mw = mw;
        this.cardType = cardType;
        tempHtmlFront = cardType.getHtmlFront();
        tempHtmlBack = cardType.getHtmlBack();
        tempCss = cardType.getCss();
        tempHtmlBodyFront = cardType.getHtmlBodyFront();
        tempHtmlBodyBack = cardType.getHtmlBodyBack();

        frontPreview = new CardViewer(cardType, tempHtmlFront);
        backPreview = new CardViewer(cardType, tempHtmlBack);

        this.setPreferredSize(new java.awt.Dimension(parent.getWidth(), parent.getHeight()));
        gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        gbc = new GridBagConstraints();
        wizard = new GridbagWizard();

        JPanel pnlEditorFront = new JPanel(new BorderLayout());
        JTextArea txaFront = new JTextArea();
        txaFront.setText(tempHtmlBodyFront);
        // Update the value of htmlBodyFront when the text area is changed
        txaFront.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tempHtmlBodyFront = txaFront.getText();
                updateFrontHtml();
            }
        });
        pnlEditorFront.add(new JScrollPane(txaFront), BorderLayout.CENTER);

        JPanel pnlEditorBack = new JPanel(new BorderLayout());
        JTextArea txaBack = new JTextArea();
        txaBack.setText(tempHtmlBodyBack);
        // Update the value of htmlBodyBack when the text area is changed
        txaBack.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tempHtmlBodyBack = txaBack.getText();
                updateBackHtml();
            }
        });
        pnlEditorBack.add(new JScrollPane(txaBack), BorderLayout.CENTER);

        JPanel pnlEditorStyle = new JPanel(new BorderLayout());
        JTextArea txaStyling = new JTextArea();
        txaStyling.setText(tempCss);
        // Update the value of css when the text area is changed
        txaStyling.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tempCss = txaStyling.getText();
                //cardType.setCss(txaStyling.getText());
                updateFrontHtml();
                updateBackHtml();
            }
        });
        pnlEditorStyle.add(new JScrollPane(txaStyling), BorderLayout.CENTER);

        JPanel pnlTabbedPane1 = new JPanel(new BorderLayout());
        pnlTabbedPane1.setBackground(Color.RED);
        JTabbedPane tpHtmlEditor = new JTabbedPane();
        tpHtmlEditor.add("Front html", pnlEditorFront);
        tpHtmlEditor.add("Back html", pnlEditorBack);
        tpHtmlEditor.add("Styling", pnlEditorStyle);
        pnlTabbedPane1.add(tpHtmlEditor, BorderLayout.CENTER);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.4;
        gbc.weighty = 1;
        gbc.insets = new Insets(0, 0, 10, 10);
        wizard.addComponent(pnlTabbedPane1, this, gridBagLayout, gbc, 0, 0, 1, 1);


        pnlFrontPreview = new JPanel(new BorderLayout());
        pnlFrontPreview.add(frontPreview, BorderLayout.CENTER);
        pnlBackPreview = new JPanel(new BorderLayout());
        pnlBackPreview.add(backPreview, BorderLayout.CENTER);

        JPanel pnlTabbedPane2 = new JPanel(new BorderLayout());
        pnlTabbedPane2.setBackground(Color.BLUE);
        JTabbedPane tpHtmlPreview = new JTabbedPane();
        tpHtmlPreview.add("Front preview", pnlFrontPreview);
        tpHtmlPreview.add("Back preview", pnlBackPreview);
        pnlTabbedPane2.add(tpHtmlPreview, BorderLayout.CENTER);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.6;
        gbc.weighty = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        wizard.addComponent(pnlTabbedPane2, this, gridBagLayout, gbc, 1, 0, 1, 1);

        OkCancelButtonsPanel okCancelButtonsPanel = new OkCancelButtonsPanel("Save", "Cancel") {
            @Override
            public void btnOk() {
                // TODO: Save the changes
                saveChanges();
                parent.dispose();
            }

            @Override
            public void btnCancel() {
                // TODO: Ask if the user wants to discard changes
                parent.dispose();
            }
        };
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;
        wizard.addComponent(okCancelButtonsPanel, this, gridBagLayout, gbc, 0, 1, 2, 1);
    }
    private void updateFrontHtml() {
        tempHtmlFront = HtmlHelper.getHtml(tempHtmlBodyFront, tempCss);
        frontPreview.updateHtml(tempHtmlFront);
    }

    private void updateBackHtml() {
        tempHtmlBack = HtmlHelper.getHtml(tempHtmlBodyBack, tempCss);
        backPreview.updateHtml(tempHtmlBack);
    }

    private void saveChanges() {
        cardType.setHtmlBodyFront(tempHtmlBodyFront);
        System.out.println(tempHtmlBodyFront);
        cardType.setHtmlBodyBack(tempHtmlBodyBack);
        System.out.println(tempHtmlBodyBack);
        cardType.setCss(tempCss);
        System.out.println(tempCss);
        cardType.updateHtmlFront();
        cardType.updateHtmlBack();
        mw.updateAllCards(cardType);
    }
}
