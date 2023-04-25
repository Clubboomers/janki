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
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        for (String fieldName : card.getCardType().getFieldNames()) {
            int i = 0;
            JPanel fieldPanel = new JPanel();
            fieldPanel.setBorder(BorderFactory.createTitledBorder(fieldName));
            fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
            JTextPane textPane = new JTextPane();
            textPane.setText(card.getFieldContentByName(fieldName));
            textPane.setEditorKit(new HTMLEditorKit(){
                @Override
                public ViewFactory getViewFactory(){

                    return new HTMLFactory(){
                        public View create(Element e){
                            View v = super.create(e);
                            if(v instanceof InlineView){
                                return new InlineView(e){
                                    public int getBreakWeight(int axis, float pos, float len) {
                                        return GoodBreakWeight;
                                    }
                                    public View breakView(int axis, int p0, float pos, float len) {
                                        if(axis == View.X_AXIS) {
                                            checkPainter();
                                            int p1 = getGlyphPainter().getBoundedPosition(this, p0, pos, len);
                                            if(p0 == getStartOffset() && p1 == getEndOffset()) {
                                                return this;
                                            }
                                            return createFragment(p0, p1);
                                        }
                                        return this;
                                    }
                                };
                            }
                            else if (v instanceof ParagraphView) {
                                return new ParagraphView(e) {
                                    protected SizeRequirements calculateMinorAxisRequirements(int axis, SizeRequirements r) {
                                        if (r == null) {
                                            r = new SizeRequirements();
                                        }
                                        float pref = layoutPool.getPreferredSpan(axis);
                                        float min = layoutPool.getMinimumSpan(axis);
                                        // Don't include insets, Box.getXXXSpan will include them.
                                        r.minimum = (int)min;
                                        r.preferred = Math.max(r.minimum, (int) pref);
                                        r.maximum = Integer.MAX_VALUE;
                                        r.alignment = 0.5f;
                                        return r;
                                    }

                                };
                            }
                            return v;
                        }
                    };
                }
            });

            textPane.setMaximumSize(new Dimension(200, textPane.getPreferredSize().height));
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
                        textPane.setMaximumSize(new Dimension(200, textPane.getPreferredSize().height));
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
