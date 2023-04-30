package main.cardeditor;

import java.awt.event.MouseEvent;

public class CardEditorMouseListener implements java.awt.event.MouseListener {
    private FieldTextPane fieldTextPane;
    public CardEditorMouseListener(FieldTextPane fieldTextPane) {
        this.fieldTextPane = fieldTextPane;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            CardEditorPopupMenu popupMenu = new CardEditorPopupMenu(fieldTextPane);
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            CardEditorPopupMenu popupMenu = new CardEditorPopupMenu(fieldTextPane);
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
