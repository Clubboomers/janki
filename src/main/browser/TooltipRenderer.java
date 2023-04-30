package main.browser;

import javax.swing.table.TableCellRenderer;

public class TooltipRenderer implements TableCellRenderer {
    private TableCellRenderer renderer;

    public TooltipRenderer(TableCellRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        java.awt.Component c = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (c instanceof javax.swing.JComponent) {
            javax.swing.JComponent jc = (javax.swing.JComponent) c;
            jc.setToolTipText(value.toString());
        }
        return c;
    }
}
