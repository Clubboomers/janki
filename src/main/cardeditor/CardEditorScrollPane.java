package main.cardeditor;

import main.data.Card;
import main.data.Field;
import main.mainwindow.MainWindow;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class CardEditorScrollPane extends JScrollPane {
    private JPanel scrollPanePanel;
    private int width;
    private ArrayList<FieldTextPane> fields;
    private MainWindow mw;
    public CardEditorScrollPane(MainWindow mw, Card card) {
        super();
        this.mw = mw;
        scrollPanePanel = new JPanel();
        scrollPanePanel.setLayout(new BoxLayout(scrollPanePanel, BoxLayout.Y_AXIS));
        width = this.getWidth();
        scrollPanePanel.setPreferredSize(new Dimension(width, this.getHeight()));
        //scrollPanePanel.setMaximumSize(new Dimension(this.getWidth(), Integer.MAX_VALUE));

        this.setViewportView(scrollPanePanel);

        setCard(card);
    }

    public void setCard(Card card) {
        scrollPanePanel.removeAll();
        fields = new ArrayList<>();
        for (String fieldName :  mw.getCardTypeWithName(card.getCardType()).getFieldNames()) {
            JPanel fieldPanel = new JPanel();
            fieldPanel.setBorder(BorderFactory.createTitledBorder(fieldName));
            fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
            FieldTextPane textPane = new FieldTextPane(fieldName);

            textPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, textPane.getPreferredSize().height));

            String fieldContent = card.getFieldContentByName(fieldName);
            textPane.setText(fieldContent);

            System.out.println(textPane.getText());

            fields.add(textPane);
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
                        textPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, textPane.getPreferredSize().height));
                        textPane.revalidate();
                        textPane.repaint();
                        scrollPanePanel.setPreferredSize(new Dimension(width, getPreferredHeight()));
                    });
                }
            });
            scrollPanePanel.add(fieldPanel);
        }
        scrollPanePanel.revalidate();
        scrollPanePanel.repaint();
    }

    public ArrayList<FieldTextPane> getFields() {
        return fields;
    }

    /**
     * Botch solution to get vertical overflow and character wrapping to work
     * @return height of all the components in the scroll pane
     */
    private int getPreferredHeight() {
        int height = 0;
        for (Component c : scrollPanePanel.getComponents()) {
            height += c.getPreferredSize().height;
        }
        return height;
    }
}
