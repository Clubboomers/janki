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

        CardEditorScrollPane scrollPane = new CardEditorScrollPane(mw, card);

        add(scrollPane);

        // add 5 px padding
        add(Box.createRigidArea(new Dimension(0, 5)));

        add(new OkCancelButtonsPanel() {
            @Override
            public void btnOk() {
                ArrayList<FieldTextPane> fields = scrollPane.getFields();
                for (FieldTextPane field : fields) {
                    String newContent = field.getText().trim();
                    mw.getCard(card.getCardId()).setFieldContentByName(field.getFieldName(), newContent);
                }
                parent.dispose();
            }

            @Override
            public void btnCancel() {
                // TODO: detect if changes are made and ask the user is they want to continue
                parent.dispose();
            }
        });
    }
}
