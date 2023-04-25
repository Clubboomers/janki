package main.cardeditor;

import main.data.Card;
import main.helper.OkCancelButtonsPanel;
import main.mainwindow.MainWindow;
import main.utility.MediaUtility;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.InlineView;
import javax.swing.text.html.ParagraphView;
import java.awt.*;
import java.util.ArrayList;

public class CardEditView extends JPanel {
    private CardEditWindow parent;
    private MainWindow mw;
    private Card card;
    public CardEditView(CardEditWindow parent, MainWindow mw, Card card) {
        super();
        this.parent = parent;
        this.mw = mw;
        this.card = card;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel thisPanel = this;

        JPanel scrollPanePanel = new JPanel();
        scrollPanePanel.setLayout(new BoxLayout(scrollPanePanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(scrollPanePanel);

        for (String fieldName : card.getCardType().getFieldNames()) {
            JPanel fieldPanel = new JPanel();
            fieldPanel.setBorder(BorderFactory.createTitledBorder(fieldName));
            fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
            JTextPane textPane = new JTextPane();

            textPane.setMaximumSize(new Dimension(Short.MAX_VALUE, textPane.getPreferredSize().height));

            textPane.setText(card.getFieldContentByName(fieldName));
            //textPane.setEditorKit(new TextPaneEditorKit());

            fieldPanel.add(textPane);


            textPane.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    update();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    update();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    update();
                }
                private void update() {
                    // Make it execute on the EDT thread (otherwise the change will be delayed until the next time the user types something)
                    SwingUtilities.invokeLater(() -> {
                        textPane.setMaximumSize(new Dimension(Short.MAX_VALUE, textPane.getPreferredSize().height));
                        textPane.revalidate();
                        textPane.repaint();
                    });
                }
            });
            scrollPanePanel.add(fieldPanel);
        }

        // add vertical strut to take up the rest of the space in the scroll pane
        //scrollPanePanel.add(Box.createVerticalGlue());

        add(scrollPane);

        add(new OkCancelButtonsPanel() {
            @Override
            public void btnOk() {

            }

            @Override
            public void btnCancel() {
                // TODO: detect if changes are made and ask the user is they want to continue
                parent.dispose();
            }
        });
    }
}
